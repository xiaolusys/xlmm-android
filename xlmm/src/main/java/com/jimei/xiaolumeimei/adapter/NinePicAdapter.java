package com.jimei.xiaolumeimei.adapter;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.data.FilePara;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.okhttp3.FileParaCallback;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.ImagePagerActivity;
import com.jimei.xiaolumeimei.utils.CameraUtils;
import com.jimei.xiaolumeimei.utils.FileUtils;
import com.jimei.xiaolumeimei.widget.ninepicimagview.MultiImageView;
import com.jude.utils.JUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhy.http.okhttp.OkHttpUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/29.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class NinePicAdapter extends BaseAdapter {
    private Context mcontext;
    private List<NinePicBean> mlist = new ArrayList<>();

    public NinePicAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    public void setDatas(List<NinePicBean> datas) {
        if (datas != null) {
            this.mlist = datas;
        }
    }

    @Override
    public int getCount() {
        return mlist == null ? 0 : mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mcontext, R.layout.item_ninepic, null);
            holder.headIv = (ImageView) convertView.findViewById(R.id.head_iv);
            holder.timeTv = (TextView) convertView.findViewById(R.id.time_tv);
            holder.contentTv = (TextView) convertView.findViewById(R.id.contentTv);
            holder.save = (Button) convertView.findViewById(R.id.save);
            ViewStub linkOrImgViewStub =
                    (ViewStub) convertView.findViewById(R.id.imageviewstub);

            linkOrImgViewStub.setLayoutResource(R.layout.viewstub_imgbody);
            linkOrImgViewStub.inflate();
            MultiImageView multiImageView =
                    (MultiImageView) convertView.findViewById(R.id.multiImagView);
            if (multiImageView != null) {
                holder.multiImageView = multiImageView;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int id = -1;
        switch (mlist.get(position).getTurnsNum()) {
            case 1:
                id = R.drawable.lun_head_1;
                break;
            case 2:
                id = R.drawable.lun_head_2;
                break;
            case 3:
                id = R.drawable.lun_head_3;
                break;
            case 4:
                id = R.drawable.lun_head_4;
                break;
            case 5:
                id = R.drawable.lun_head_5;
                break;
        }
        holder.headIv.setImageResource(0);
        if (id != -1) {
            holder.headIv.setImageResource(id);
        }
        holder.contentTv.setText(mlist.get(position).getDescription());
        holder.timeTv.setText(mlist.get(position).getStartTime().replace("T", " "));
        List<String> picArry = mlist.get(position).getPicArry();

        if (picArry != null && picArry.size() > 0) {
            holder.multiImageView.setVisibility(View.VISIBLE);
            holder.multiImageView.setList(picArry);
            holder.multiImageView.setOnItemClickListener(
                    new MultiImageView.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // 因为单张图片时，图片实际大小是自适应的，imageLoader缓存时是按测量尺寸缓存的
                            //ImagePagerActivity.imageSize =
                            //    new ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                            ImagePagerActivity.startImagePagerActivity(mcontext, picArry, position);
                        }
                    });
        } else {
            holder.multiImageView.setVisibility(View.GONE);
        }

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assert picArry != null;

                RxPermissions.getInstance(mcontext)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the camera now
                                downloadNinepic(picArry);

                                copy(mlist.get(position).getDescription(), mcontext);

                                new MaterialDialog.Builder(mcontext).
                                        content("文字已经复制,商品图片正在后台保存，请稍后前往分享吧").
                                        positiveText("OK").
                                        callback(new MaterialDialog.ButtonCallback() {
                                            @Override
                                            public void onPositive(MaterialDialog dialog) {
                                                super.onPositive(dialog);
                                                dialog.dismiss();
                                            }
                                        }).show();
                            } else {
                                // Oups permission denied
                                JUtils.Toast("小鹿美美需要存储权限存储图片,请再次点击保存并打开权限许可.");
                            }
                        });
            }
        });

        return convertView;
    }

    private void downloadNinepic(List<String> picArry) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JUtils.Log("NinePic", "new thread ,pic size " + picArry.size());
                final boolean[] bflag = {true};
                for (int i = 0; i < picArry.size(); i++) {
                    while (!bflag[0]) SystemClock.sleep(100);
                    JUtils.Log("NinePic", "download " + picArry.get(picArry.size() - 1 - i));
                    if (bflag[0]) {
                        final int finalI = i;
                        OkHttpUtils.get()
                                .url(picArry.get(picArry.size() - 1 - i)
                                        + "?imageMogr2/thumbnail/580/format/jpg")
                                .build()
                                .execute(new FileParaCallback() {
                                    @Override
                                    public void onError(Call call, Exception e,int id) {
                                        bflag[0] = true;
                                    }

                                    @Override
                                    public void onResponse(FilePara response,int id) {
                                        if (response != null) {
                                            bflag[0] = true;
                                            JUtils.Log("NinePic", "download " + finalI + " finished.");
                                            try {
                                                String pic_indicate_name =
                                                        picArry.get(picArry.size() - 1 - finalI)
                                                                .substring(picArry.get(picArry.size() - 1 - finalI)
                                                                        .lastIndexOf('/'));
                                                String newName = Environment.getExternalStorageDirectory() +
                                                        CameraUtils.XLMM_IMG_PATH + pic_indicate_name + ".jpg";
                                                JUtils.Log("NinePic", "newName=" + newName);
                                                if (FileUtils.isFileExist(newName)) {
                                                    JUtils.Log("NinePic", "newName has existed,delete");
                                                    FileUtils.deleteFile(newName);
                                                }
                                                new File(response.getFilePath()).renameTo(new File(newName));

                                                Uri uri = Uri.fromFile(new File(newName));
                                                // 通知图库更新
                                                Intent scannerIntent =
                                                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                                                scannerIntent.setData(uri);
                                                mcontext.sendBroadcast(scannerIntent);
                                                if (picArry.size() - 1 == finalI) {
                                                    JUtils.Log("NinePic", "download finished");
                                                    JUtils.Toast("商品图片保存完成");
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                    }
                    bflag[0] = false;
                }
            }
        }).start();
    }

    class ViewHolder {
        MultiImageView multiImageView;
        TextView timeTv;
        TextView contentTv;
        Button save;
        ImageView headIv;
    }
}
