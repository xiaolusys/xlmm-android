package com.jimei.xiaolumeimei.ui.activity.user;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.yoojia.zxing.qrcode.Encoder;
import com.jimei.library.utils.BitmapUtil;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.WxPubAuthInfo;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import butterknife.Bind;

/**
 * Created by wulei on 2016/2/4.
 */
public class WxPubTwoDimenCodeActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {
    String TAG = "WxPubTwoDimenCodeActivity";
    @Bind(R.id.img_2dimen)
    ImageView img_2dimen;
    @Bind(R.id.btn_save)
    Button btn_save;
    @Bind(R.id.layout)
    LinearLayout layout;

    WxPubAuthInfo wxPubAuthInfo;
    Bitmap bitmap;
    private Encoder mEncoder;

    @Override
    protected void setListener() {
        btn_save.setOnClickListener(this);
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wxpub_2dimen_code;
    }

    @Override
    protected void initData() {
        final int dimension = 400;
        mEncoder = new Encoder.Builder().setBackgroundColor(0xFFFFFF) // 指定背景颜色，默认为白色
                .setCodeColor(0xFF000000) // 指定编码块颜色，默认为黑色
                .setOutputBitmapWidth(dimension) // 生成图片宽度
                .setOutputBitmapHeight(dimension) // 生成图片高度
                .setOutputBitmapPadding(0) // 设置为没有白边
                .build();
        addSubscription(UserModel.getInstance()
                .getWxPubAuthInfo()
                .subscribe(new ServiceResponse<WxPubAuthInfo>() {
                    @Override
                    public void onNext(WxPubAuthInfo wxpub) {
                        if (wxpub != null) {
                            JUtils.Log(TAG, "wxPubAuthInfo:" + wxpub.toString());
                            wxPubAuthInfo = wxpub;
                            bitmap = mEncoder.encode(wxpub.getAuthLink());
                            bitmap = getNewBitMap(bitmap, wxpub.getAuthMsg());
                            if (!"".equals(wxpub.getAuthMsg())) {
                                JUtils.Toast(wxpub.getAuthMsg());
                            }
                            img_2dimen.setImageBitmap(bitmap);
                        }
                    }
                }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                JUtils.Log(TAG, "btn_save save 2 dimen code");
                save_2dimencode();
                if (null != wxPubAuthInfo && !TextUtils.isEmpty(wxPubAuthInfo.getAuthMsg())) {
                    JUtils.Toast("保存成功，" + wxPubAuthInfo.getAuthMsg());
                }
                finish();
                break;
        }
    }

    private void save_2dimencode() {
        String fileName = Environment.getExternalStorageDirectory()
                + "/"
                + Environment.DIRECTORY_DCIM
                + "/Camera/"
                + getResources().getString(R.string.wxpub_2dimen_pic_name)
                + ".jpg";
        JUtils.Log(TAG, "filename:" + fileName);
        if (null != bitmap) {
            BitmapUtil.saveBitmap(bitmap, fileName);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public Bitmap getNewBitMap(Bitmap bmp, String text) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Bitmap newBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawRect(0, 0, 500, 500, paint);
        canvas.drawBitmap(bmp, 50, 0, null);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30.0F);
        StaticLayout sl = new StaticLayout(text, textPaint, newBitmap.getWidth() - 8,
                Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.translate(6, 410);
        sl.draw(canvas);
        return newBitmap;
    }
}