package com.jimei.xiaolumeimei.ui.activity.trade;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jimei.library.utils.CameraUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.QiniuTokenBean;
import com.jimei.xiaolumeimei.entities.RefundMsgBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.Bind;

public class ApplyReturnGoodsActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

    String TAG = "ApplyReturnGoodsActivity";
    String slect_reason[] = new String[]{
        "七天无理由退换货", "发票问题", "与描述不符", "未收到货", "发错货/漏发", "开线/脱色/脱毛/有色差/有虫洞", "错拍", "其他原因"
    };

    @Bind(R.id.img_good)
    ImageView img_good;
    @Bind(R.id.tx_good_name)
    TextView tx_good_name;
    @Bind(R.id.tx_good_price)
    TextView tx_good_price;
    @Bind(R.id.tx_good_size)
    TextView tx_good_size;
    @Bind(R.id.tx_good_num)
    TextView tx_good_num;
    @Bind(R.id.delete)
    TextView delete;
    @Bind(R.id.tx_refund_num)
    TextView tx_refund_num;
    @Bind(R.id.add)
    TextView add;
    @Bind(R.id.tx_refundfee)
    TextView tx_refundfee;
    @Bind(R.id.et_refund_info)
    EditText et_refund_info;
    @Bind(R.id.et_refund_reason)
    EditText et_refund_reason;
    @Bind(R.id.imgbtn_camera_pic)
    ImageView imgbtn_camera_pic;
    @Bind(R.id.btn_commit)
    Button btn_commit;
    @Bind(R.id.rl_proof_pic1)
    RelativeLayout rl_proof_pic1;
    @Bind(R.id.rl_proof_pic2)
    RelativeLayout rl_proof_pic2;
    @Bind(R.id.rl_proof_pic3)
    RelativeLayout rl_proof_pic3;
    @Bind(R.id.layout)
    RelativeLayout layout;

    ImageView img_proof_pic1;
    ImageView img_proof_pic2;
    ImageView img_proof_pic3;
    ImageView img_delete1;
    ImageView img_delete2;
    ImageView img_delete3;
    AllOrdersBean.ResultsEntity.OrdersEntity goods_info;
    String reason = "";
    int num = 0;
    double apply_fee = 0;
    String desc = "";
    String proof_pic = "";
    String uptoken;
    File tmpPic[] = new File[3];
    String uploadPic[] = new String[3];
    int picNum = 0;
    // 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
    UploadManager uploadManager = new UploadManager();
    private OrderDetailBean.ExtrasBean extraBean;
    private int id;
    private int position;

    @Override
    protected void setListener() {
        btn_commit.setOnClickListener(this);
        imgbtn_camera_pic.setOnClickListener(this);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        img_delete1.setOnClickListener(v -> {
            Log.d(TAG, "delete pic 1");
            tmpPic[0] = null;
            uploadPic[0] = null;
            if (picNum >= 1) {
                for (char i = 1; i < picNum; i++) {
                    tmpPic[i - 1] = tmpPic[i];
                    uploadPic[i - 1] = uploadPic[i];
                }
                picNum--;
            }
            showPic();
        });
        img_delete2.setOnClickListener(v -> {
            Log.d(TAG, "delete pic 2");
            tmpPic[1] = null;
            uploadPic[1] = null;
            if (picNum >= 2) {
                for (char i = 2; i < picNum; i++) {
                    tmpPic[i - 1] = tmpPic[i];
                    uploadPic[i - 1] = uploadPic[i];
                }
                picNum--;
            }
        });
        img_delete3.setOnClickListener(v -> {
            Log.d(TAG, "delete pic 3");
            tmpPic[2] = null;
            uploadPic[2] = null;
            if (picNum >= 3) {
                for (char i = 3; i < picNum; i++) {
                    tmpPic[i - 1] = tmpPic[i];
                    uploadPic[i - 1] = uploadPic[i];
                }
                picNum--;
            }
        });

        et_refund_info.setOnClickListener(this);
        et_refund_reason.setOnTouchListener((v, event) -> {
            //et_refund_reason.setInputType(InputType.TYPE_NULL); //关闭软键盘
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                Log.d(TAG, "choose reason");
                chooseReason();
            }
            return false;
        });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = extras.getInt("id");
        position = extras.getInt("position");
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_apply_return_goods;
    }

    @Override
    protected void initViews() {

        img_proof_pic1 = (ImageView) rl_proof_pic1.findViewById(R.id.img_proof_pic);
        img_proof_pic2 = (ImageView) rl_proof_pic2.findViewById(R.id.img_proof_pic);
        img_proof_pic3 = (ImageView) rl_proof_pic3.findViewById(R.id.img_proof_pic);

        img_delete1 = (ImageView) rl_proof_pic1.findViewById(R.id.img_delete);
        img_delete2 = (ImageView) rl_proof_pic2.findViewById(R.id.img_delete);
        img_delete3 = (ImageView) rl_proof_pic3.findViewById(R.id.img_delete);

        addSubscription(XlmmApp.getTradeInteractor(this)
            .getOrderDetail(id, new ServiceResponse<OrderDetailBean>() {
                @Override
                public void onNext(OrderDetailBean orderDetailBean) {
                    extraBean = orderDetailBean.getExtras();
                    goods_info = orderDetailBean.getOrders().get(position);
                    fillDataIntoView();
                }

                @Override
                public void onError(Throwable e) {
                    JUtils.Toast("加载失败");
                }
            }));


    }

    private void fillDataIntoView() {
        fillDataToView(goods_info);
        getQiniuToken();
    }

    private void fillDataToView(AllOrdersBean.ResultsEntity.OrdersEntity goods) {
        if ((goods == null)) return;

        ViewUtils.loadImgToImgView(this, img_good, goods.getPic_path());

        tx_good_name.setText(goods.getTitle());
        tx_good_price.setText("￥" + goods.getTotal_fee());

        tx_good_size.setText(goods.getSku_name());
        tx_good_num.setText("×" + goods.getNum());

        num = goods.getNum();
        tx_refund_num.setText(Integer.toString(num));
        tx_refundfee.setText("￥" + goods.getPayment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                desc = et_refund_info.getText().toString().trim();
                proof_pic = "";
                for (int i = 0; i < picNum; i++) {
                    if (uploadPic[i] != null) {
                        if (i != picNum - 1) {
                            proof_pic += XlmmApi.QINIU_UPLOAD_URL_BASE + uploadPic[i] + ",";
                        } else {
                            proof_pic += XlmmApi.QINIU_UPLOAD_URL_BASE + uploadPic[i];
                        }
                    }
                }
                if (reason.equals("")) {
                    JUtils.Toast("请选择退货原因！");
                } else {
                    commit_apply();
                }
                break;
            case R.id.et_refund_info:
                Log.i(TAG, "et_refund_info ");
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                et_refund_info.setCursorVisible(true);
                et_refund_info.setFocusable(true);
                et_refund_info.setFocusableInTouchMode(true);
                et_refund_info.requestFocus();
                break;
            case R.id.add:
                Log.i(TAG, "add ");
                if (goods_info.getNum() > num) {
                    num++;
                }
                tx_refund_num.setText(Integer.toString(num));
                break;
            case R.id.delete:
                Log.i(TAG, "delete ");
                if (num > 1) {
                    num--;
                }
                tx_refund_num.setText(Integer.toString(num));
                break;

            case R.id.imgbtn_camera_pic:
                Log.i(TAG, "camera ");
                RxPermissions.getInstance(this)
                    .request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            Image_Picker_Dialog();
                        } else {
                            // Oups permission denied
                            JUtils.Toast("小鹿美美需要照相机和相册权限,请再次点击并打开权限许可.");
                        }
                    });

                break;
        }
    }

    private void commit_apply() {
        Log.i(TAG, "commit_apply "
            + goods_info.getId()
            + " "
            + reason
            + " "
            + num
            + " "
            + apply_fee
            + " "
            + desc
            + " "
            + proof_pic);
        addSubscription(XlmmApp.getTradeInteractor(this)
            .refundCreate(goods_info.getId(), XlmmConst.get_reason_num(reason), num,
                apply_fee, desc, proof_pic, new ServiceResponse<RefundMsgBean>() {
                    @Override
                    public void onNext(RefundMsgBean resp) {
                        JUtils.Toast(resp.getInfo());
                        Log.i(TAG, "commit_apply " + resp.toString());
                        if (resp.getCode() == 0) {
                            AlertDialog dialog = new AlertDialog.Builder(ApplyReturnGoodsActivity.this)
                                .setMessage(getResources().getString(R.string.refund_msg))
                                .setCancelable(false)
                                .setPositiveButton("确认", (dialog1, which) -> ApplyReturnGoodsActivity.this.finish())
                                .create();
                            dialog.show();
                        } else {
                            finish();
                        }
                    }
                }));
    }

    private void chooseReason() {
        new AlertDialog.Builder(this).setTitle("")
            .setItems(slect_reason, (dialog, which) -> {
        /*
        * ad变量用final关键字定义，因为在隐式实现的Runnable接口 的run()方法中 需要访问final变量。
         */
                Log.d(TAG, "你选择的是：" + which + ": " + slect_reason[which]);
                reason = slect_reason[which];
                et_refund_reason.setText(reason);
                dialog.dismiss();
            })
            .setNegativeButton("取消", null)
            .show();
    }

    public void Image_Picker_Dialog() {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("请选择照片模式");
        //myAlertDialog.setMessage("选择照片模式");

        final String[] arrayPhotoType = new String[]{"照相机", "相册"};
        myAlertDialog.setItems(arrayPhotoType, (dialog, which) -> {
            JUtils.Log(TAG, "click item " + arrayPhotoType[which]);
            if (0 == which) {
                CameraUtils.pictureActionIntent =
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(CameraUtils.pictureActionIntent,
                    CameraUtils.CAMERA_PICTURE);
            } else if (1 == which) {
                CameraUtils.pictureActionIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
                CameraUtils.pictureActionIntent.setType("image/*");
                CameraUtils.pictureActionIntent.putExtra("return-data", true);
                startActivityForResult(CameraUtils.pictureActionIntent,
                    CameraUtils.GALLERY_PICTURE);
            }
        });
        myAlertDialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File b = null;
        if (data == null) {
            JUtils.Log(TAG, "choose no pic");
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraUtils.GALLERY_PICTURE) {
            // data contains result
            // Do some task
            b = Image_Selecting_Task(data);
        } else if (requestCode == CameraUtils.CAMERA_PICTURE) {
            // Do some task
            b = Image_Photo_Task(data);
        }

        if (b != null) {
            if (picNum <= 2) {
                tmpPic[picNum] = b;
                uploadPic[picNum] = b.getName();
                picNum++;
            } else {
                tmpPic[picNum - 1] = b;
                uploadPic[picNum - 1] = b.getName();
            }

            showPic();
            uploadFile(b);
        }
    }

    public File Image_Selecting_Task(Intent data) {
        File b = null;
        try {
            CameraUtils.uri = data.getData();
            if (CameraUtils.uri != null) {
                b = read_img_from_uri();
            } else {
                Toast toast = Toast.makeText(this, "对不起，您还没有选择任何照片。", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            // you get this when you will not select any single image
            Log.e("onActivityResult", "" + e);
        }
        return b;
    }

    public File Image_Photo_Task(Intent data) {
        File b = null;
        try {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

            CameraUtils.uri = data.getData();
            if (CameraUtils.uri == null) {
                Log.d("onActivityResult", "return uri null, get image again.");
                CameraUtils.uri = Uri.parse(
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,
                        null));
            }
            if (CameraUtils.uri != null) {
                b = read_img_from_uri();
            } else {

                Toast toast = Toast.makeText(this, "对不起，照相机返回照片失败。", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            // you get this when you will not select any single image
            Log.e("onActivityResult", "" + e);
        }
        return b;
    }

    public File read_img_from_uri() {
        Cursor cursor = getContentResolver().query(CameraUtils.uri,
            new String[]{android.provider.MediaStore.Images.ImageColumns.DATA}, null, null,
            null);
        if (cursor == null) return null;
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
        //Bitmap b = CameraUtils.decodeFile(CameraUtils.Paste_Target_Location);

        // use new copied path and use anywhere
        //String valid_photo = CameraUtils.Paste_Target_Location.toString();
        //b = Bitmap.createScaledBitmap(b, 150, 150, true);

        //set your selected image in image view
        //img_proof_pic.setImageBitmap(b);
        cursor.close();

        return CameraUtils.Paste_Target_Location;
    }

    private void getQiniuToken() {
        addSubscription(XlmmApp.getTradeInteractor(this)
            .getQiniuToken(new ServiceResponse<QiniuTokenBean>() {
                @Override
                public void onNext(QiniuTokenBean resp) {
                    uptoken = resp.getUptoken();
                    Log.i(TAG, "getQiniuToken " + resp.toString());
                }
            }));
    }

    private void uploadFile(File file) {
        Log.i(TAG, "qiniu uploadFile");
        //compress first
        byte[] file_byte = {};
        try {
            Bitmap b = CameraUtils.decodeFile(file);
            file_byte = compressImage(b);
        } catch (Exception e) {
            e.printStackTrace();
            JUtils.Log(TAG, "compress file failed.return");
            JUtils.Toast("文件压缩失败,请重新选择文件!");
            return;
        }
        //upload
        //File data = file;
        String key = file.getName();
        String token = uptoken;
        uploadManager.put(file_byte, key, token, (key1, info, res) -> {
            //  res 包含hash、key等信息，具体字段取决于上传策略的设置。
            Log.i("qiniu",
                "complete key=" + key1 + ", " + "info =" + info + ", " + "res " + res);
        }, new UploadOptions(null, null, false, (key12, percent) -> Log.i("qiniu", "percent key=" + key12 + ": " + percent), null));
    }

    public byte[] compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100,
            baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        JUtils.Log(TAG, "before compressed file length " + baos.toByteArray().length / 1024);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 300) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options,
                baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 5;// 每次都减少10
            JUtils.Log(TAG, "compressed file length " + baos.toByteArray().length / 1024);
        }

        return baos.toByteArray();
    }

    private void showPic() {
        Log.i(TAG, "showPic picNum " + picNum);
        for (int i = 0; i < picNum; i++) {
            Bitmap b = CameraUtils.decodeFile(tmpPic[i]);
            b = Bitmap.createScaledBitmap(b, 150, 150, true);
            if (i == 0) {
                img_proof_pic1.setImageBitmap(b);
                rl_proof_pic1.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                img_proof_pic2.setImageBitmap(b);
                rl_proof_pic2.setVisibility(View.VISIBLE);
            } else if (i == 2) {
                img_proof_pic3.setImageBitmap(b);
                rl_proof_pic3.setVisibility(View.VISIBLE);
            }
        }

        if (picNum < 3) {
            for (int i = picNum; i < 3; i++) {
                if (i == 0) {
                    rl_proof_pic1.setVisibility(View.INVISIBLE);
                } else if (i == 1) {
                    rl_proof_pic2.setVisibility(View.INVISIBLE);
                } else if (i == 2) {
                    rl_proof_pic3.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
