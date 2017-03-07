package com.jimei.library.utils;

/**
 * Created by wulei on 2016/1/26.
 */

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class CameraUtils {

    public static String XLMM_IMG_PATH = "/xlmm/xiaolumeimei";
    public static File Copy_sourceLocation;
    public static File Paste_Target_Location;
    public static File MY_IMG_DIR, Default_DIR;
    public static Uri uri;
    public static Intent pictureActionIntent = null;
    public static final int CAMERA_PICTURE = 1;
    public static final int GALLERY_PICTURE = 2;
    public static final int SELECT_PICTURE = 0;
    public static final int SELECT_CAMERA = 1;

    public static Bitmap compressImage(Bitmap bm, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 60;
        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于50kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
            if(options<0){
                break;
            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    // 把Bitmap转换成Base64
    public static String getBitmapStrBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, 0);
    }

    public static void getSystemPicture(Activity activity) {
        RxPermissions.getInstance(activity)
            .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe(granted -> {
                if (granted) {
                    ImagePickerDialog(activity);
                } else {
                    JUtils.Toast("小鹿美美需要照相机和相册权限,请再次点击并打开权限许可.");
                }
            });
    }

    private static void ImagePickerDialog(Activity activity) {
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(activity)
            .setTitle("选择图片来源")
            .setItems(items, (dialog, which) -> {
                if (which == SELECT_PICTURE) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activity.startActivityForResult(intent, SELECT_CAMERA);
                }
            })
            .create().show();
    }

    public static File getFileFromUri(Uri uri, ContentResolver cr) {
        Cursor cursor = cr.query(uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA},
            null, null, null);
        if (cursor == null) {
            return null;
        }
        cursor.moveToFirst();
        final String imageFilePath = cursor.getString(0);
        return new File(imageFilePath);
    }

    public static String Get_Random_File_Name() {
        final Calendar c = Calendar.getInstance();
        int myYear = c.get(Calendar.YEAR);
        int myMonth = c.get(Calendar.MONTH);
        int myDay = c.get(Calendar.DAY_OF_MONTH);
        return "" + myDay + myMonth + myYear + "_" + Math.random();
    }

    // Copy your image into specific folder
    public static File copyFile(File current_location, File destination_location) {
        Copy_sourceLocation = new File("" + current_location);
        Paste_Target_Location = new File("" + destination_location + "/" + Get_Random_File_Name() + ".jpg");

        Log.v("Purchase-File", "sourceLocation: " + Copy_sourceLocation);
        Log.v("Purchase-File", "targetLocation: " + Paste_Target_Location);
        try {
            // 1 = move the file, 2 = copy the file
            int actionChoice = 2;
            // moving the file to another directory
            if (actionChoice == 1) {
                if (Copy_sourceLocation.renameTo(Paste_Target_Location)) {
                    Log.i("Purchase-File", "Move file successful.");
                } else {
                    Log.i("Purchase-File", "Move file failed.");
                }
            }

            // we will copy the file
            else {
                // make sure the target file exists
                if (Copy_sourceLocation.exists()) {

                    InputStream in = new FileInputStream(Copy_sourceLocation);
                    OutputStream out = new FileOutputStream(Paste_Target_Location);

                    // Copy the bits from instream to outstream
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    Log.i("copyFile", "Copy file successful.");

                } else {
                    Log.i("copyFile", "Copy file failed. Source file missing.");
                }
            }

        } catch (NullPointerException e) {
            Log.i("copyFile", "" + e);

        } catch (Exception e) {
            Log.i("copyFile", "" + e);
        }
        return Paste_Target_Location;
    }

    // 	decode your image into bitmap format
    public static Bitmap decodeFile(File f) {
        try {
            Log.i("decodeFile", "length " + f.length() / 1024);
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            Log.e("decodeFile", "" + e);
        }
        return null;
    }

    // Create New Dir (folder) if not exist
    public static File Create_MY_IMAGES_DIR() {
        try {
            // Get SD Card path & your folder name
            MY_IMG_DIR = new File(Environment.getExternalStorageDirectory(),
                    XLMM_IMG_PATH);

            // check if exist
            if (!MY_IMG_DIR.exists()) {
                // Create New folder
                MY_IMG_DIR.mkdirs();
                Log.i("path", ">>.." + MY_IMG_DIR);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Create_MY_IMAGES_DIR", "" + e);
        }
        return MY_IMG_DIR;
    }
}
