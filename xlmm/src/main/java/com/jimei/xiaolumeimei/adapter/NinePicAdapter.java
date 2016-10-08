package com.jimei.xiaolumeimei.adapter;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.FilePara;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.okhttp3.FileParaCallback;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.ImagePagerActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.utils.CameraUtils;
import com.jimei.xiaolumeimei.utils.FileUtils;
import com.jimei.xiaolumeimei.widget.ninepicimagview.MultiImageView;
import com.jude.utils.JUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
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
    private MMNinePicActivity mcontext;
    private List<NinePicBean> mList;
    private String codeLink;

    public NinePicAdapter(MMNinePicActivity mcontext) {
        this.mcontext = mcontext;
        mList = new ArrayList<>();
    }

    public void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void update(List<NinePicBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_ninepic, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.headTv.setText("" + mList.get(position).getTurnsNum());
        holder.contentTv.setText(mList.get(position).getDescription());
        holder.timeTv.setText(mList.get(position).getStartTime().replace("T", " "));
        List<String> picArry = mList.get(position).getPicArry();
        if (picArry != null && picArry.size() > 0) {
            if (codeLink != null & !"".equals(codeLink)) {
                if (picArry.size() == 9) {
                    picArry.set(4, "code" + codeLink);
                } else {
                    picArry.set(picArry.size() / 2, "code" + codeLink);
                }
            }
            holder.multiImageView.setVisibility(View.VISIBLE);
            holder.multiImageView.setList(picArry);
            holder.multiImageView.setOnItemClickListener(
                    (view) -> {
                        // 因为单张图片时，图片实际大小是自适应的，imageLoader缓存时是按测量尺寸缓存的
                        //ImagePagerActivity.imageSize =
                        //    new ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                        ImagePagerActivity.startImagePagerActivity(mcontext, picArry);
                    });
        } else {
            holder.multiImageView.setVisibility(View.GONE);
        }

        holder.save.setOnClickListener(v -> {
            MobclickAgent.onEvent(mcontext, "NinePic_save");
            assert picArry != null;
            RxPermissions.getInstance(mcontext)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            downloadNinepic(picArry);

                            copy(mList.get(position).getDescription(), mcontext);

                            new AlertDialog.Builder(mcontext)
                                    .setMessage("文字已经复制,商品图片正在后台保存，请稍后前往分享吧")
                                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                    .show();
                        } else {
                            // Oups permission denied
                            JUtils.Toast("小鹿美美需要存储权限存储图片,请再次点击保存并打开权限许可.");
                        }
                    });
        });

        return convertView;
    }

    private void downloadNinepic(List<String> picArry) {
        new Thread(() -> {
            JUtils.Log("NinePic", "new thread ,pic size " + picArry.size());
            final boolean[] bflag = {true};
            for (int i = 0; i < picArry.size(); i++) {
                while (!bflag[0]) SystemClock.sleep(100);
                String str = picArry.get(picArry.size() - 1 - i);
                if (!str.contains("code")) {
                    str = str + "?imageMogr2/thumbnail/580/format/jpg";
                }else {
                    str = str.substring(4);
                }
                JUtils.Log("NinePic", "download " + str);
                if (bflag[0]) {
                    final int finalI = i;
                    OkHttpUtils.get()
                            .url(str)
                            .build()
                            .execute(new FileParaCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    bflag[0] = true;
                                }

                                @Override
                                public void onResponse(FilePara response, int id) {
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
        }).start();
    }

    public void setCodeLink(String codeLink) {
        this.codeLink = codeLink;
        notifyDataSetChanged();
    }

    class ViewHolder {
        MultiImageView multiImageView;
        TextView timeTv, contentTv, headTv;
        Button save;

        public ViewHolder(View itemView) {
            headTv = (TextView) itemView.findViewById(R.id.head_tv);
            timeTv = (TextView) itemView.findViewById(R.id.time_tv);
            contentTv = (TextView) itemView.findViewById(R.id.contentTv);
            save = (Button) itemView.findViewById(R.id.save);
            ViewStub linkOrImgViewStub = (ViewStub) itemView.findViewById(R.id.view_stub);
            linkOrImgViewStub.setLayoutResource(R.layout.viewstub_imgbody);
            linkOrImgViewStub.inflate();
            multiImageView = (MultiImageView) itemView.findViewById(R.id.multiImagView);
        }
    }
}
