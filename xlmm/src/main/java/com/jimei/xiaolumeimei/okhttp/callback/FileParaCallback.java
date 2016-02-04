package com.jimei.xiaolumeimei.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jimei.xiaolumeimei.data.FilePara;
import com.jimei.xiaolumeimei.utils.CameraUtils;
import com.jude.utils.JUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.utils.Exceptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import okhttp3.Response;

/**
 * Created by wulei on 2016/2/4.
 */
public abstract class FileParaCallback extends Callback<FilePara> {

    @Override
    public FilePara parseNetworkResponse(Response response) throws Exception
    {
      BitmapFactory.Options optins = new BitmapFactory.Options();

      try {
        CameraUtils.Create_MY_IMAGES_DIR();
        File Target_Location = new File("" + Environment.getExternalStorageDirectory() + "/xlmm/img/" + "/" +
                CameraUtils.Get_Random_File_Name() + ".jpg");

        JUtils.Log("FileParaCallback", "Target_Location= " + Target_Location.getAbsolutePath());
        Target_Location.createNewFile();

        OutputStream os = new FileOutputStream(Target_Location);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = response.body().byteStream().read(buffer, 0, 8192)) != -1) {
          os.write(buffer, 0, bytesRead);
        }
        os.close();
        response.body().byteStream().close();


        optins.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(Target_Location.getAbsolutePath(), optins);
      }
      catch (Exception e){
        e.printStackTrace();
      }
      FilePara filePara = new FilePara();
      filePara.setHeight(optins.outHeight);
      filePara.setWidth(optins.outWidth);
      JUtils.Log("FileParaCallback", " height= "+optins.outHeight +"width= " + optins.outWidth);
      return filePara;

    }


}
