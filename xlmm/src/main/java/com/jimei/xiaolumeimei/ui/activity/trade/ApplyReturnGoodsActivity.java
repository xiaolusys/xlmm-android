package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.Toast;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.QiniuTokenBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.utils.CameraUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import com.jimei.xiaolumeimei.R;

import butterknife.Bind;
import com.squareup.okhttp.ResponseBody;
import java.io.File;
import rx.schedulers.Schedulers;

public class ApplyReturnGoodsActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{

    String TAG = "ApplyReturnGoodsActivity";
  String slect_reason[]  = new String[] {  "未收到货","商品质量问题","收到商品不符", "收到商品破损","商品错发/漏发","其他原因"};

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.img_good) ImageView img_good;
  @Bind(R.id.tx_good_name) TextView tx_good_name;
  @Bind(R.id.tx_good_price) TextView tx_good_price;
  @Bind(R.id.tx_good_size) TextView tx_good_size;
  @Bind(R.id.tx_good_num) TextView tx_good_num;
  @Bind(R.id.delete) TextView delete;
  @Bind(R.id.tx_refund_num) TextView tx_refund_num;
  @Bind(R.id.add) TextView add;
  @Bind(R.id.tx_refundfee) TextView tx_refundfee;
  @Bind(R.id.et_refund_info) EditText et_refund_info;
  @Bind(R.id.et_refund_reason) EditText et_refund_reason;
  @Bind(R.id.imgbtn_camera_pic) ImageButton imgbtn_camera_pic;
  @Bind(R.id.btn_commit) Button btn_commit;
  @Bind(R.id.rl_proof_pic1) RelativeLayout rl_proof_pic1;

  ImageView img_proof_pic;
  AllOrdersBean.ResultsEntity.OrdersEntity goods_info;
  TradeModel model = new TradeModel();
  String reason = "";
  int num = 0;
  double apply_fee = 0;
  String desc = "";
  String proof_pic="";

  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
    btn_commit.setOnClickListener(this);
    imgbtn_camera_pic.setOnClickListener(this);
    add.setOnClickListener(this);
    delete.setOnClickListener(this);
    et_refund_info.setOnClickListener(this);
    et_refund_reason.setOnTouchListener(new View.OnTouchListener(){
      public boolean onTouch(View v, MotionEvent event){
        //et_refund_reason.setInputType(InputType.TYPE_NULL); //关闭软键盘
        if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
          Log.d(TAG, "choose reason");
          chooseReason();
        }
        return false;
      }
    });
  }

  @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_apply_return_goods;
    }

  @Override protected void initViews() {
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.back);

    img_proof_pic = (ImageView)rl_proof_pic1.findViewById(R.id.img_proof_pic);
  }

  //从server端获得所有订单数据，可能要查询几次
  @Override protected void initData() {
    goods_info = getIntent().getExtras().getParcelable("goods_info");
    fillDataToView(goods_info);
    getQiniuToken();
  }

    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

  private void fillDataToView(AllOrdersBean.ResultsEntity.OrdersEntity goods) {
    ViewUtils.loadImgToImgView(this, img_good, goods.getPicPath());

    tx_good_name.setText(goods.getTitle());
    tx_good_price.setText("￥" + goods.getTotalFee());

    tx_good_size.setText("尺码：" + goods.getSkuName());
    tx_good_num.setText("×" + goods.getNum());

    num = goods.getNum();
    tx_refund_num.setText(Integer.toString(num));
    tx_refundfee.setText("￥" + apply_fee);

  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.toolbar:
        finish();
        break;
      case R.id.btn_commit:
        desc = et_refund_info.getText().toString().trim();
        commit_apply();
        finish();
        break;
      case R.id.et_refund_info:
        Log.i(TAG,"et_refund_info ");
        et_refund_info.setCursorVisible(true);
        et_refund_info.setFocusable(true);
        et_refund_info.setFocusableInTouchMode(true);
        et_refund_info.requestFocus();
        break;
      case R.id.add:
        Log.i(TAG,"add ");
        if(goods_info.getNum() > num) {
          num++;
        }
        tx_refund_num.setText(Integer.toString(num));
        break;
      case R.id.delete:
        Log.i(TAG,"delete ");
        if(num > 1) {
          num--;
        }
        tx_refund_num.setText(Integer.toString(num));
        break;

      case R.id.imgbtn_camera_pic:
        Log.i(TAG,"camera ");
        Image_Picker_Dialog();
        break;
    }
  }

  private void commit_apply(){
    model.refund_create(goods_info.getId(), reason, num, apply_fee, desc, proof_pic)
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<ResponseBody>() {
          @Override public void onNext(ResponseBody resp) {

            Log.i(TAG,"commit_apply "+ resp.toString());
          }
        });
  }

  private void chooseReason(){
    new AlertDialog.Builder(this).setTitle("")
          .setItems(slect_reason, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
            /*
            * ad变量用final关键字定义，因为在隐式实现的Runnable接口 的run()方法中 需要访问final变量。
             */
              Log.d(TAG, "你选择的是：" + which + ": " + slect_reason[which]);
              reason = slect_reason[which];
              et_refund_reason.setText(reason);
              dialog.dismiss();
            }
          })
          .setNegativeButton("取消", null).show();
  }

  public void Image_Picker_Dialog()
  {

    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
    myAlertDialog.setTitle("照片选择");
    //myAlertDialog.setMessage("选择照片模式");

    myAlertDialog.setPositiveButton("相册", new DialogInterface.OnClickListener(){
      public void onClick(DialogInterface arg0, int arg1)
      {
        CameraUtils.pictureActionIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
        CameraUtils.pictureActionIntent.setType("image/*");
        CameraUtils.pictureActionIntent.putExtra("return-data", true);
        startActivityForResult(CameraUtils.pictureActionIntent, CameraUtils.GALLERY_PICTURE);
      }
    });

    myAlertDialog.setNegativeButton("照相机", new DialogInterface.OnClickListener(){
      public void onClick(DialogInterface arg0, int arg1)
      {
        CameraUtils.pictureActionIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(CameraUtils.pictureActionIntent, CameraUtils.CAMERA_PICTURE);
      }
    });
    myAlertDialog.show();

  }

  // After the selection of image you will retun on the main activity with bitmap image
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CameraUtils.GALLERY_PICTURE)
    {
      // data contains result
      // Do some task
      Image_Selecting_Task(data);
    } else if (requestCode == CameraUtils.CAMERA_PICTURE)
    {
      // Do some task
      Image_Photo_Task(data);
    }
  }

  public void Image_Selecting_Task(Intent data)
  {
    try
    {
      CameraUtils.uri = data.getData();
      if (CameraUtils.uri != null)
      {
        read_img_from_uri();

      } else
      {
        Toast toast = Toast.makeText(this, "对不起，您还没有选择任何照片。", Toast.LENGTH_LONG);
        toast.show();
      }
    } catch (Exception e)
    {
      // you get this when you will not select any single image
      Log.e("onActivityResult", "" + e);

    }
  }

  public void Image_Photo_Task(Intent data)
  {
    try
    {
      Bundle bundle = data.getExtras();
      Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

      CameraUtils.uri = data.getData();
      if (CameraUtils.uri == null){
        Log.d("onActivityResult", "return uri null, get image again.");
        CameraUtils.uri  = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
      }
      if (CameraUtils.uri != null)
      {
        img_proof_pic.setImageBitmap(read_img_from_uri());

      } else
      {

        Toast toast = Toast.makeText(this, "对不起，照相机返回照片失败。", Toast.LENGTH_LONG);
        toast.show();
      }
    } catch (Exception e)
    {
      // you get this when you will not select any single image
      Log.e("onActivityResult", "" + e);

    }
  }

  public Bitmap read_img_from_uri()
  {
    // User had pick an image.
    Cursor cursor = getContentResolver().query(CameraUtils.uri, new String[]
            { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
    cursor.moveToFirst();
    // Link to the image
    final String imageFilePath = cursor.getString(0);

    //Assign string path to File
    CameraUtils.Default_DIR = new File(imageFilePath);

    // Create new dir MY_IMAGES_DIR if not created and copy image into that dir and store that image path in valid_photo
    CameraUtils.Create_MY_IMAGES_DIR();

    // Copy your image
    CameraUtils.copyFile(CameraUtils.Default_DIR, CameraUtils.MY_IMG_DIR);

    // Get new image path and decode it
    Bitmap b = CameraUtils.decodeFile(CameraUtils.Paste_Target_Location);

    // use new copied path and use anywhere
    String valid_photo = CameraUtils.Paste_Target_Location.toString();
    b = Bitmap.createScaledBitmap(b, 150, 150, true);

    //set your selected image in image view
    //img_proof_pic.setImageBitmap(b);
    cursor.close();

    return b;
  }

  private void getQiniuToken(){
    model.getQiniuToken()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<QiniuTokenBean>() {
          @Override public void onNext(QiniuTokenBean resp) {

            Log.i(TAG,"getQiniuToken "+ resp.toString());
          }
        });
  }
}
