package com.jimei.xiaolumeimei.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.jimei.xiaolumeimei.data.FilePara;
import com.jimei.xiaolumeimei.utils.CameraUtils;
import com.jude.utils.JUtils;
import com.zhy.http.okhttp.callback.Callback;
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
      CameraUtils.Create_MY_IMAGES_DIR();
      File Target_Location = new File("" + CameraUtils.MY_IMG_DIR + "/" +
          CameraUtils.Get_Random_File_Name() + ".jpg");
      OutputStream os = new FileOutputStream(Target_Location);
      int bytesRead = 0;
      byte[] buffer = new byte[8192];
      while((bytesRead = response.body().byteStream().read(buffer, 0, 8192)) != -1){
        os.write(buffer,0,bytesRead);
      }
      os.close();
      response.body().byteStream().close();

      BitmapFactory.Options optins  = new BitmapFactory.Options();
      optins.inJustDecodeBounds = true;
      Bitmap bmp = BitmapFactory.decodeFile(Target_Location.getName(), optins);
      FilePara filePara = new FilePara();
      filePara.setHeight(optins.outHeight);
      filePara.setWidth(optins.outWidth);
      JUtils.Log("FileParaCallback", "bmp is null? "+(bmp==null) + "height= "+optins.outHeight
              +"width= " + optins.outWidth);
      return filePara;

    }


}
