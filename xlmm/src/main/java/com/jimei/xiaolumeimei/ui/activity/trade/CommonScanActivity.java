/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.widget.utils.Constant;
import com.jimei.xiaolumeimei.widget.zxing.ScanListener;
import com.jimei.xiaolumeimei.widget.zxing.ScanManager;
import com.jimei.xiaolumeimei.widget.zxing.decode.Utils;

import butterknife.Bind;

public final class CommonScanActivity extends BaseSwipeBackCompatActivity implements ScanListener, View.OnClickListener {

    ScanManager scanManager;

    @Bind(R.id.capture_preview)
    SurfaceView scanPreview;
    @Bind(R.id.capture_container)
    View scanContainer;
    @Bind(R.id.capture_crop_view)
    View scanCropView;
    @Bind(R.id.capture_scan_line)
    ImageView scanLine;
    @Bind(R.id.qrcode_g_gallery)
    TextView qrcode_g_gallery;
    @Bind(R.id.qrcode_ic_back)
    TextView qrcode_ic_back;
    @Bind(R.id.iv_light)
    TextView iv_light;

    @Override
    protected void setListener() {
        qrcode_g_gallery.setOnClickListener(this);
        qrcode_ic_back.setOnClickListener(this);
        iv_light.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_scan_code;
    }

    @Override
    protected void initViews() {
        scanManager = new ScanManager(this, scanPreview, scanContainer, scanCropView, scanLine,
                Constant.REQUEST_SCAN_MODE_ALL_MODE, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        scanManager.onPause();
    }

    @Override
    public void scanResult(Result rawResult, Bundle bundle) {
        Intent intent = new Intent(this, WriteLogisticsInfoActivty.class);
        intent.putExtra("number", rawResult.getText());
        setResult(2, intent);
        finish();
    }

    @Override
    public void scanError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        if (e.getMessage() != null && e.getMessage().startsWith("相机")) {
            scanPreview.setVisibility(View.INVISIBLE);
        }
    }

    public void showPictures(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String photo_path;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 66:
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(data.getData(), proj, null, null, null);
                    assert cursor != null;
                    if (cursor.moveToFirst()) {
                        int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photo_path = cursor.getString(colum_index);
                        if (photo_path == null) {
                            photo_path = Utils.getPath(getApplicationContext(), data.getData());
                        }
                        scanManager.scanningImage(photo_path);
                    }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qrcode_g_gallery:
                showPictures(66);
                break;
            case R.id.iv_light:
                scanManager.switchLight();
                break;
            case R.id.qrcode_ic_back:
                finish();
                break;
            default:
                break;
        }
    }

}