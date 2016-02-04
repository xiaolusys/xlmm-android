package com.jimei.xiaolumeimei.widget.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.widget.banner.Animations.BaseAnimationInterface;
import com.jimei.xiaolumeimei.widget.banner.Indicators.PagerIndicator;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.xiaolumeimei.widget.banner.Transformers.BaseTransformer;
import com.jimei.xiaolumeimei.widget.banner.Transformers.DefaultTransformer;
import com.jimei.xiaolumeimei.widget.banner.Transformers.ZoomInTransformer;
import com.jimei.xiaolumeimei.widget.banner.Transformers.ZoomOutSlideTransformer;
import com.jimei.xiaolumeimei.widget.banner.Transformers.ZoomOutTransformer;
import com.jimei.xiaolumeimei.widget.banner.Tricks.FixedSpeedScroller;
import com.jimei.xiaolumeimei.widget.banner.Tricks.InfinitePagerAdapter;
import com.jimei.xiaolumeimei.widget.banner.Tricks.InfiniteViewPager;
import com.jimei.xiaolumeimei.widget.banner.Tricks.ViewPagerEx;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class SliderLayout extends RelativeLayout {

  private Context mContext;
  /**
   * InfiniteViewPager is extended from ViewPagerEx. As the name says, it can scroll
   * without bounder.
   */
  private InfiniteViewPager mViewPager;

  /**
   * InfiniteViewPager adapter.
   */
  private SliderAdapter mSliderAdapter;

  private PagerIndicator mIndicator;

  private Timer mCycleTimer;
  private TimerTask mCycleTask;

  private Timer mResumingTimer;
  private TimerTask mResumingTask;

  private boolean mCycling;

  private boolean mAutoRecover = true;

  private int mTransformerId;

  private int mTransformerSpan = 1100;

  private boolean mAutoCycle;

  /**
   * the duration between animation.
   */
  private long mSliderDuration = 4000;

  private PagerIndicator.IndicatorVisibility mIndicatorVisibility =
      PagerIndicator.IndicatorVisibility.Visible;

  private BaseTransformer mViewPagerTransformer;

  private BaseAnimationInterface mCustomAnimation;
  private android.os.Handler mh = new android.os.Handler() {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
      moveNextPosition(true);
    }
  };

  public SliderLayout(Context context) {
    this(context, null);
  }

  public SliderLayout(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.SliderStyle);
  }

  public SliderLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mContext = context;
    LayoutInflater.from(context).inflate(R.layout.slider_layout, this, true);

    final TypedArray attributes = context.getTheme()
        .obtainStyledAttributes(attrs, R.styleable.SliderLayout, defStyle, 0);

    mTransformerSpan =
        attributes.getInteger(R.styleable.SliderLayout_pager_animation_span, 1100);
    mTransformerId = attributes.getInt(R.styleable.SliderLayout_pager_animation,
        Transformer.Default.ordinal());
    mAutoCycle = attributes.getBoolean(R.styleable.SliderLayout_auto_cycle, true);
    int visibility = attributes.getInt(R.styleable.SliderLayout_indicator_visibility, 0);
    for (PagerIndicator.IndicatorVisibility v : PagerIndicator.IndicatorVisibility.values()) {
      if (v.ordinal() == visibility) {
        mIndicatorVisibility = v;
        break;
      }
    }
    mSliderAdapter = new SliderAdapter(mContext);
    PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSliderAdapter);

    mViewPager = (InfiniteViewPager) findViewById(R.id.daimajia_slider_viewpager);
    mViewPager.setAdapter(wrappedAdapter);

    mViewPager.setOnTouchListener(new OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
          case MotionEvent.ACTION_UP:
            recoverCycle();
            break;
        }
        return false;
      }
    });

    attributes.recycle();
    setPresetIndicator(PresetIndicators.Center_Bottom);
    setPresetTransformer(mTransformerId);
    setSliderTransformDuration(mTransformerSpan, null);
    setIndicatorVisibility(mIndicatorVisibility);
    if (mAutoCycle) {
      startAutoCycle();
    }
  }

  public void addOnPageChangeListener(
      ViewPagerEx.OnPageChangeListener onPageChangeListener) {
    if (onPageChangeListener != null) {
      mViewPager.addOnPageChangeListener(onPageChangeListener);
    }
  }

  public void removeOnPageChangeListener(
      ViewPagerEx.OnPageChangeListener onPageChangeListener) {
    mViewPager.removeOnPageChangeListener(onPageChangeListener);
  }

  public void setCustomIndicator(PagerIndicator indicator) {
    if (mIndicator != null) {
      mIndicator.destroySelf();
    }
    mIndicator = indicator;
    mIndicator.setIndicatorVisibility(mIndicatorVisibility);
    mIndicator.setViewPager(mViewPager);
    mIndicator.redraw();
  }

  public <T extends BaseSliderView> void addSlider(T imageContent) {
    mSliderAdapter.addSlider(imageContent);
  }

  public void startAutoCycle() {
    startAutoCycle(mSliderDuration, mSliderDuration, mAutoRecover);
  }

  /**
   * start auto cycle.
   *
   * @param delay delay time
   * @param duration animation duration time.
   * @param autoRecover if recover after user touches the slider.
   */
  public void startAutoCycle(long delay, long duration, boolean autoRecover) {
    if (mCycleTimer != null) mCycleTimer.cancel();
    if (mCycleTask != null) mCycleTask.cancel();
    if (mResumingTask != null) mResumingTask.cancel();
    if (mResumingTimer != null) mResumingTimer.cancel();
    mSliderDuration = duration;
    mCycleTimer = new Timer();
    mAutoRecover = autoRecover;
    mCycleTask = new TimerTask() {
      @Override public void run() {
        mh.sendEmptyMessage(0);
      }
    };
    mCycleTimer.schedule(mCycleTask, delay, mSliderDuration);
    mCycling = true;
    mAutoCycle = true;
  }

  /**
   * pause auto cycle.
   */
  private void pauseAutoCycle() {
    if (mCycling) {
      mCycleTimer.cancel();
      mCycleTask.cancel();
      mCycling = false;
    } else {
      if (mResumingTimer != null && mResumingTask != null) {
        recoverCycle();
      }
    }
  }

  /**
   * set the duration between two slider changes. the duration value must >= 500
   */
  public void setDuration(long duration) {
    if (duration >= 500) {
      mSliderDuration = duration;
      if (mAutoCycle && mCycling) {
        startAutoCycle();
      }
    }
  }

  /**
   * stop the auto circle
   */
  public void stopAutoCycle() {
    if (mCycleTask != null) {
      mCycleTask.cancel();
    }
    if (mCycleTimer != null) {
      mCycleTimer.cancel();
    }
    if (mResumingTimer != null) {
      mResumingTimer.cancel();
    }
    if (mResumingTask != null) {
      mResumingTask.cancel();
    }
    mAutoCycle = false;
    mCycling = false;
  }

  /**
   * when paused cycle, this method can weak it up.
   */
  private void recoverCycle() {
    if (!mAutoRecover || !mAutoCycle) {
      return;
    }

    if (!mCycling) {
      if (mResumingTask != null && mResumingTimer != null) {
        mResumingTimer.cancel();
        mResumingTask.cancel();
      }
      mResumingTimer = new Timer();
      mResumingTask = new TimerTask() {
        @Override public void run() {
          startAutoCycle();
        }
      };
      mResumingTimer.schedule(mResumingTask, 6000);
    }
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    int action = ev.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        pauseAutoCycle();
        break;
    }
    return false;
  }

  /**
   * set ViewPager transformer.
   */
  public void setPagerTransformer(boolean reverseDrawingOrder,
      BaseTransformer transformer) {
    mViewPagerTransformer = transformer;
    mViewPagerTransformer.setCustomAnimationInterface(mCustomAnimation);
    mViewPager.setPageTransformer(reverseDrawingOrder, mViewPagerTransformer);
  }

  /**
   * set the duration between two slider changes.
   */
  public void setSliderTransformDuration(int period, Interpolator interpolator) {
    try {
      Field mScroller = ViewPagerEx.class.getDeclaredField("mScroller");
      mScroller.setAccessible(true);
      FixedSpeedScroller scroller =
          new FixedSpeedScroller(mViewPager.getContext(), interpolator, period);
      mScroller.set(mViewPager, scroller);
    } catch (Exception e) {

    }
  }

  /**
   * set a preset viewpager transformer by id.
   */
  public void setPresetTransformer(int transformerId) {
    for (Transformer t : Transformer.values()) {
      if (t.ordinal() == transformerId) {
        setPresetTransformer(t);
        break;
      }
    }
  }

  ;

  /**
   * set preset PagerTransformer via the name of transforemer.
   */
  public void setPresetTransformer(String transformerName) {
    for (Transformer t : Transformer.values()) {
      if (t.equals(transformerName)) {
        setPresetTransformer(t);
        return;
      }
    }
  }

  public void setCustomAnimation(BaseAnimationInterface animation) {
    mCustomAnimation = animation;
    if (mViewPagerTransformer != null) {
      mViewPagerTransformer.setCustomAnimationInterface(mCustomAnimation);
    }
  }

  /**
   * pretty much right? enjoy it. :-D
   */
  public void setPresetTransformer(Transformer ts) {
    //
    // special thanks to https://github.com/ToxicBakery/ViewPagerTransforms
    //
    BaseTransformer t = null;
    switch (ts) {
      case Default:
        t = new DefaultTransformer();
        break;

      case ZoomIn:
        t = new ZoomInTransformer();
        break;
      case ZoomOutSlide:
        t = new ZoomOutSlideTransformer();
        break;
      case ZoomOut:
        t = new ZoomOutTransformer();
        break;
    }
    setPagerTransformer(true, t);
  }

  public PagerIndicator.IndicatorVisibility getIndicatorVisibility() {
    if (mIndicator == null) {
      return mIndicator.getIndicatorVisibility();
    }
    return PagerIndicator.IndicatorVisibility.Invisible;
  }

  /**
   * Set the visibility of the indicators.
   */
  public void setIndicatorVisibility(PagerIndicator.IndicatorVisibility visibility) {
    if (mIndicator == null) {
      return;
    }

    mIndicator.setIndicatorVisibility(visibility);
  }

  public PagerIndicator getPagerIndicator() {
    return mIndicator;
  }

  public void setPresetIndicator(PresetIndicators presetIndicator) {
    PagerIndicator pagerIndicator =
        (PagerIndicator) findViewById(presetIndicator.getResourceId());
    setCustomIndicator(pagerIndicator);
  }

  private InfinitePagerAdapter getWrapperAdapter() {
    PagerAdapter adapter = mViewPager.getAdapter();
    if (adapter != null) {
      return (InfinitePagerAdapter) adapter;
    } else {
      return null;
    }
  }

  private SliderAdapter getRealAdapter() {
    PagerAdapter adapter = mViewPager.getAdapter();
    if (adapter != null) {
      return ((InfinitePagerAdapter) adapter).getRealAdapter();
    }
    return null;
  }

  /**
   * get the current item position
   */
  public int getCurrentPosition() {

    if (getRealAdapter() == null) {
      throw new IllegalStateException("You did not set a slider adapter");
    }

    return mViewPager.getCurrentItem() % getRealAdapter().getCount();
  }

  public void setCurrentPosition(int position) {
    setCurrentPosition(position, true);
  }

  /**
   * get current slider.
   */
  public BaseSliderView getCurrentSlider() {

    if (getRealAdapter() == null) {
      throw new IllegalStateException("You did not set a slider adapter");
    }

    int count = getRealAdapter().getCount();
    int realCount = mViewPager.getCurrentItem() % count;
    return getRealAdapter().getSliderView(realCount);
  }

  /**
   * remove  the slider at the position. Notice: It's a not perfect method, a very small
   * bug still exists.
   */
  public void removeSliderAt(int position) {
    if (getRealAdapter() != null) {
      getRealAdapter().removeSliderAt(position);
      mViewPager.setCurrentItem(mViewPager.getCurrentItem(), false);
    }
  }

  /**
   * remove all the sliders. Notice: It's a not perfect method, a very small bug still
   * exists.
   */
  public void removeAllSliders() {
    if (getRealAdapter() != null) {
      int count = getRealAdapter().getCount();
      getRealAdapter().removeAllSliders();
      //a small bug, but fixed by this trick.
      //bug: when remove adapter's all the sliders.some caching slider still alive.
      mViewPager.setCurrentItem(mViewPager.getCurrentItem() + count, false);
    }
  }

  /**
   * set current slider
   */
  public void setCurrentPosition(int position, boolean smooth) {
    if (getRealAdapter() == null) {
      throw new IllegalStateException("You did not set a slider adapter");
    }
    if (position >= getRealAdapter().getCount()) {
      throw new IllegalStateException("Item position is not exist");
    }
    int p = mViewPager.getCurrentItem() % getRealAdapter().getCount();
    int n = (position - p) + mViewPager.getCurrentItem();
    mViewPager.setCurrentItem(n, smooth);
  }

  /**
   * move to prev slide.
   */
  public void movePrevPosition(boolean smooth) {

    if (getRealAdapter() == null) {
      throw new IllegalStateException("You did not set a slider adapter");
    }

    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, smooth);
  }

  public void movePrevPosition() {
    movePrevPosition(true);
  }

  /**
   * move to next slide.
   */
  public void moveNextPosition(boolean smooth) {

    if (getRealAdapter() == null) {
      throw new IllegalStateException("You did not set a slider adapter");
    }

    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, smooth);
  }

  public void moveNextPosition() {
    moveNextPosition(true);
  }

  /**
   * preset transformers and their names
   */
  public enum Transformer {
    Default("Default"),
    Accordion("Accordion"),
    Background2Foreground("Background2Foreground"),
    CubeIn("CubeIn"),
    DepthPage("DepthPage"),
    Fade("Fade"),
    FlipHorizontal("FlipHorizontal"),
    FlipPage("FlipPage"),
    Foreground2Background("Foreground2Background"),
    RotateDown("RotateDown"),
    RotateUp("RotateUp"),
    Stack("Stack"),
    Tablet("Tablet"),
    ZoomIn("ZoomIn"),
    ZoomOutSlide("ZoomOutSlide"),
    ZoomOut("ZoomOut");

    private final String name;

    private Transformer(String s) {
      name = s;
    }

    public String toString() {
      return name;
    }

    public boolean equals(String other) {
      return (other == null) ? false : name.equals(other);
    }
  }

  public enum PresetIndicators {
    Center_Bottom("Center_Bottom", R.id.default_center_bottom_indicator),
    Right_Bottom("Right_Bottom", R.id.default_bottom_right_indicator),
    Left_Bottom("Left_Bottom", R.id.default_bottom_left_indicator),
    Center_Top("Center_Top", R.id.default_center_top_indicator),
    Right_Top("Right_Top", R.id.default_center_top_right_indicator),
    Left_Top("Left_Top", R.id.default_center_top_left_indicator);

    private final String name;
    private final int id;

    private PresetIndicators(String name, int id) {
      this.name = name;
      this.id = id;
    }

    public String toString() {
      return name;
    }

    public int getResourceId() {
      return id;
    }
  }
}
