package com.jimei.xiaolumeimei.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jimei.library.utils.CameraUtils;
import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.ninepicimagview.MultiImageView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.library.entities.FilePara;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.okhttp3.FileParaCallback;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.ImagePagerActivity;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/29.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class NinePicAdapter extends BaseAdapter {
    private Activity mContext;
    private List<NinePicBean> mList;
    private String codeLink;
    private List<Uri> mFiles;

    public NinePicAdapter(Activity mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        mFiles = new ArrayList<>();
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
        NinePicBean ninePicBean = mList.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_ninepic, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String description = ninePicBean.getDescription();
        holder.titleTv.setText(ninePicBean.getTitle_content());
        holder.contentTv.setText(description);
        holder.timeTv.setText(ninePicBean.getStartTime().replace("T", " "));
        holder.likeTv.setText(ninePicBean.getSave_times() + "");
        holder.shareTv.setText(ninePicBean.getSave_times() + "");
//        List<String> picArray = ninePicBean.getPicArry();
        List<String> picArray = new ArrayList<>();
        if (ninePicBean.getPicArry() != null && ninePicBean.getPicArry().size() > 0) {
            for (int i = 0; i < ninePicBean.getPicArry().size(); i++) {
                String picUrl = ninePicBean.getPicArry().get(i);
                if (picUrl != null && !"".equals(picUrl)
                    && picUrl.length() > 20) {
                    picArray.add(picUrl);
                }
            }
            if (picArray.size() > 0) {
                if (codeLink != null & !"".equals(codeLink) && ninePicBean.isShow_qrcode()) {
                    if (ninePicBean.getPicArry().size() == 9) {
                        picArray.set(4, "code" + codeLink);
                    } else if (ninePicBean.getPicArry().size() < 9) {
                        picArray.add(picArray.size() / 2, "code" + codeLink);
                    }
                }
                holder.multiImageView.setVisibility(View.VISIBLE);
                holder.multiImageView.setList(picArray);
                holder.multiImageView.setOnItemClickListener((view, currentPosition) ->
                    ImagePagerActivity.startImagePagerActivity(mContext, picArray, currentPosition));
            } else {
                holder.multiImageView.setVisibility(View.GONE);
            }
        } else {
            holder.multiImageView.setVisibility(View.GONE);
        }
        holder.save.setOnClickListener(v -> {
            MobclickAgent.onEvent(mContext, "NinePic_save");
            XlmmApp.getVipInteractor(mContext)
                .saveTime(ninePicBean.getId(), 1, new ServiceResponse<>());
            if (mContext instanceof BaseSwipeBackCompatActivity) {
                ((BaseSwipeBackCompatActivity) mContext).showIndeterminateProgressDialog(true);
            }
            RxPermissions.getInstance(mContext)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        mFiles.clear();
                        JUtils.copyToClipboard(description);
                        if (picArray.size() > 0) {
                            downloadNinepic(picArray, description);
                        } else {
                            shareToWx(description);
                        }
                        JUtils.ToastLong("努力分享中,请稍等几秒钟...");
                    } else {
                        JUtils.Toast("小鹿美美需要存储权限存储图片,请再次点击保存并打开权限许可.");
                    }
                }, Throwable::printStackTrace);

        });
        holder.copy.setOnClickListener(v -> {
            JUtils.copyToClipboard(description);
            JUtils.Toast("文案复制成功啦~可以发送给您的好友哦!");
        });
        return convertView;
    }

    private void downloadNinepic(List<String> picArry, String desc) {
        new Thread(() -> {
            JUtils.Log("NinePic", "new thread ,pic size " + picArry.size());
            for (int i = 0; i < picArry.size(); i++) {
                String str = picArry.get(i);
                String fileName = "";
                if (str.contains("code")) {
                    if (i > 0) {
                        str = picArry.get(i - 1) + "code";
                    } else {
                        str = picArry.get(0) + "code";
                    }
                }
                fileName = str.substring(str.lastIndexOf("/"));
                String newFileName = Environment.getExternalStorageDirectory() +
                    CameraUtils.XLMM_IMG_PATH + fileName + ".jpg";
                if (FileUtils.isFileExist(newFileName)) {
                    Uri uri = Uri.fromFile(new File(newFileName));
                    mFiles.add(uri);
                    if (mFiles.size() == picArry.size()) {
                        mContext.runOnUiThread(() -> shareToWx(desc));
                    }
                } else {
                    if (!str.contains("code")) {
                        str = str + "?imageMogr2/thumbnail/640/format/jpg/quality/90";
                    } else {
                        str = picArry.get(i).substring(4);
                    }
                    JUtils.Log("NinePic", "download " + str);
                    final int finalI = i;
                    OkHttpUtils.get()
                        .url(str)
                        .build()
                        .execute(new FileParaCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                hideLoading();
                                JUtils.Toast("分享失败,请重新分享!");
                            }

                            @Override
                            public void onResponse(FilePara response, int id) {
                                if (response != null) {
                                    JUtils.Log("NinePic", "download " + finalI + " finished.");
                                    try {
                                        String currentStr = picArry.get(finalI);
                                        String pic_indicate_name;
                                        if (currentStr.contains("code")) {
                                            if (finalI > 0) {
                                                currentStr = picArry.get(finalI - 1) + "code";
                                            } else {
                                                currentStr = picArry.get(0) + "code";
                                            }
                                        }
                                        pic_indicate_name = currentStr.substring(currentStr.lastIndexOf("/"));
                                        String newName = Environment.getExternalStorageDirectory() +
                                            CameraUtils.XLMM_IMG_PATH + pic_indicate_name + ".jpg";
                                        JUtils.Log("NinePic", "newName=" + newName);
                                        if (FileUtils.isFileExist(newName)) {
                                            JUtils.Log("NinePic", "newName has existed,delete");
                                            FileUtils.deleteFile(newName);
                                        }
                                        File file = new File(response.getFilePath());
                                        file.renameTo(new File(newName));
                                        Uri uri = Uri.fromFile(new File(newName));
                                        mFiles.add(uri);
                                        // 通知图库更新
                                        Intent scannerIntent =
                                            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                                        scannerIntent.setData(uri);
                                        mContext.sendBroadcast(scannerIntent);
                                        if (mFiles.size() == picArry.size()) {
                                            shareToWx(desc);
                                        }
                                    } catch (Exception e) {
                                        hideLoading();
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                }
            }
        }).start();
    }

    private void hideLoading() {
        if (mContext instanceof BaseSwipeBackCompatActivity) {
            ((BaseSwipeBackCompatActivity) mContext).hideIndeterminateProgressDialog();
        }
    }

    private void shareToWx(String desc) {
        hideLoading();
        ArrayList<Uri> imageUris = new ArrayList<>();
        Collections.sort(mFiles);
        for (int i1 = 0; i1 < mFiles.size(); i1++) {
            if (i1 < 9) {
                imageUris.add(mFiles.get(i1));
            }
        }
        try {
            if (imageUris.size() > 0) {
                Intent intent = new Intent();
                ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                intent.setComponent(comp);
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                intent.putExtra("Kdescription", desc);
                mContext.startActivity(intent);
            }
        } catch (Exception e) {
            if (e instanceof ActivityNotFoundException) {
                JUtils.Toast("您手机没有安装微信客户端,图片已保存到本地,请手动分享!");
            }
        }
    }

    public void setCodeLink(String codeLink) {
        this.codeLink = codeLink;
        notifyDataSetChanged();
    }

    class ViewHolder {
        MultiImageView multiImageView;
        TextView timeTv, contentTv, titleTv, likeTv, shareTv;
        Button save, copy;

        public ViewHolder(View itemView) {
            timeTv = (TextView) itemView.findViewById(R.id.time_tv);
            contentTv = (TextView) itemView.findViewById(R.id.contentTv);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
            likeTv = (TextView) itemView.findViewById(R.id.like_tv);
            shareTv = (TextView) itemView.findViewById(R.id.share_tv);
            save = (Button) itemView.findViewById(R.id.save);
            copy = (Button) itemView.findViewById(R.id.copy);
            ViewStub linkOrImgViewStub = (ViewStub) itemView.findViewById(R.id.view_stub);
            linkOrImgViewStub.setLayoutResource(R.layout.viewstub_imgbody);
            linkOrImgViewStub.inflate();
            multiImageView = (MultiImageView) itemView.findViewById(R.id.multiImagView);
        }
    }
}
