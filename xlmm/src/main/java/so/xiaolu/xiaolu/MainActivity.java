package so.xiaolu.xiaolu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.LayoutInflater;


import android.graphics.BitmapFactory;
import android.graphics.Matrix;


import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

//import com.seu.bigocto.collection.CollectionFragment;
import so.xiaolu.xiaolu.mainframe.ChildFragment;
import so.xiaolu.xiaolu.mainframe.TodayFragment;
import so.xiaolu.xiaolu.mainframe.WomanFragment;
import so.xiaolu.xiaolu.mainframe.YesterdayFragment;
import so.xiaolu.xiaolu.trade.TradeCommodityActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager mPager;
    //private List<View> listViews;
    private List<Fragment> listViews;
    private TextView t1, t2, t3, t4;//set top tab labels

    private ImageView cursor;
    private int offset = 0;
    private int currIndex = 0;
    private int bmpW;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*fab按钮点击进入购物车界面*/
                startActivity(new Intent(MainActivity.this, TradeCommodityActivity.class));
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        this.setTitle("小鹿美美--外贸原单");
        InitImageView();
        InitTextView();
        InitViewPager();
    }

    /***
     * show the header content
     *******/

    private void InitTextView() {
        t1 = (TextView) findViewById(R.id.mainframe_text1);
        t2 = (TextView) findViewById(R.id.mainframe_text2);
        t3 = (TextView) findViewById(R.id.mainframe_text3);
        t4 = (TextView) findViewById(R.id.mainframe_text4);

        t1.setOnClickListener(new MainFrameMyOnClickListener(0));
        t2.setOnClickListener(new MainFrameMyOnClickListener(1));
        t3.setOnClickListener(new MainFrameMyOnClickListener(2));
        t4.setOnClickListener(new MainFrameMyOnClickListener(3));
    }

    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.mainframe_Pager);
        //listViews=new ArrayList<View>();
        listViews = new ArrayList<Fragment>();
        LayoutInflater mInfalter = getLayoutInflater();

        listViews.add(new TodayFragment());
        listViews.add(new YesterdayFragment());
        listViews.add(new WomanFragment());
//        listViews.add(new BrowseFragment());
//        listViews.add(new CollectionFragment());
//       listViews.add(new SettingFragment());
        listViews.add(new ChildFragment());
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), listViews));
        mPager.setOffscreenPageLimit(1);
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());


    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int arg0) {
            if (fragmentList == null || fragmentList.size() == 0)
                return null;
            else
                return fragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            if (fragmentList == null) {
                return 0;
            } else {
                return fragmentList.size();
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d(TAG, "destroyItem");
            super.destroyItem(container, position, object);
            //recycleHand();
        }

        private void recycleHand() {
            // 先判断是否已经回收
            System.gc();
        }

        private void unbindDrawables(View view) {
            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                ((ViewGroup) view).removeAllViews();
            }
        }
    }

    /******
     * click to jump a different page
     *************/
    public class MainFrameMyOnClickListener implements View.OnClickListener {

        private int index = 0;

        public MainFrameMyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }

    }


    /**
     * 初始化动画
     */
    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.mainframe_underline)
                .getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        int one = offset * 2 + bmpW;
        int two = one * 2;
        int three = one * 3;

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(three, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(three, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(three, two, 0, 0);
                    } else if (currIndex == 0) {
                        animation = new TranslateAnimation(0, two, 0, 0);
                    }
                    break;
                case 3:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, three, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, three, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, three, 0, 0);
                    }
                    break;
            }


            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tobepaid) {
            // Handle the camera action
        } else if (id == R.id.nav_tobereceived) {

        } else if (id == R.id.nav_returned) {

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_complain) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
