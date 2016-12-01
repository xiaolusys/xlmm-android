package com.jimei.xiaolumeimei.ui.fragment.mminfo;


import android.os.Bundle;
import android.webkit.WebView;

import com.jimei.xiaolumeimei.base.BaseWebViewFragment;
import com.jimei.xiaolumeimei.model.MamaInfoModel;

public class MamaSecondFragment extends BaseWebViewFragment {

    public static MamaSecondFragment newInstance(String title, int id) {
        MamaSecondFragment fragment = new MamaSecondFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        addSubscription(MamaInfoModel.getInstance()
                .getMamaUrl()
                .subscribe(mamaUrl -> {
                    loadUrl(mamaUrl.getResults().get(0).getExtra().getForum());
                    hideIndeterminateProgressDialog();
                }, throwable -> {
                    throwable.printStackTrace();
                    hideIndeterminateProgressDialog();
                }));
    }

    public WebView getWebView() {
        if (b != null) {
            return b.wbView;
        }
        return null;
    }

//    public ValueCallback<Uri> mUploadMessage;
//    public ValueCallback<Uri[]> mUploadMessageForAndroid5;
//
//    private final static int FILECHOOSER_RESULTCODE = 1;
//    private final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
//    private static final String TITLE = "title";
//    private static final String ID = "id";
//    private int id;
//    private SharedPreferences preferences;
//
//    public static MamaSecondFragment newInstance(String title, int id) {
//        MamaSecondFragment fragment = new MamaSecondFragment();
//        Bundle args = new Bundle();
//        args.putString(TITLE, title);
//        args.putInt(ID, id);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            id = getArguments().getInt(ID);
//        }
//    }
//
//    @Override
//    public void initData() {
//        addSubscription(MamaInfoModel.getInstance()
//                .getMamaUrl()
//                .subscribe(this::fillDataToView, throwable -> {
//                    throwable.printStackTrace();
//                    hideIndeterminateProgressDialog();
//                }));
//    }
//
//    private void fillDataToView(MamaUrl mamaUrl) {
//        try {
//            Map<String, String> extraHeaders = new HashMap<>();
//            extraHeaders.put("Cookie", preferences.getString("cookiesString", ""));
//            b.webView.loadUrl(mamaUrl.getResults().get(0).getExtra().getForum(), extraHeaders);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        hideIndeterminateProgressDialog();
//    }
//
//    @Override
//    protected void initViews() {
//        preferences = mActivity.getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
//        try {
//            if (Build.VERSION.SDK_INT >= 19) {
//                b.webView.getSettings().setLoadsImagesAutomatically(true);
//            } else {
//                b.webView.getSettings().setLoadsImagesAutomatically(false);
//            }
//            String userAgentString = b.webView.getSettings().getUserAgentString();
//            b.webView.getSettings().setUserAgentString(userAgentString
//                    + "; xlmm/" + BuildConfig.VERSION_NAME + ";");
//            b.webView.getSettings().setJavaScriptEnabled(true);
//            b.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
//            }
//            b.webView.setWebViewClient(new WebViewClient());
//            b.webView.setWebChromeClient(new WebChromeClient() {
//                @Override
//                public void onProgressChanged(WebView view, int newProgress) {
//                    b.progressBar.setProgress(newProgress);
//                    if (newProgress == 100) {
//                        b.progressBar.setVisibility(View.GONE);
//                    } else {
//                        b.progressBar.setVisibility(View.VISIBLE);
//                    }
//                }
//
//                //扩展浏览器上传文件
//                //3.0++版本
//                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
//                    openFileChooserImpl(uploadMsg);
//                }
//
//                //3.0--版本
//                public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                    openFileChooserImpl(uploadMsg);
//                }
//
//                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//                    openFileChooserImpl(uploadMsg);
//                }
//
//                // For Android > 5.0
//                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg,
//                                                 WebChromeClient.FileChooserParams fileChooserParams) {
//                    openFileChooserImplForAndroid5(uploadMsg);
//                    return true;
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        syncCookie(mActivity);
//    }
//
//    public void syncCookie(Context context) {
//        try {
//            CookieSyncManager.createInstance(context);
//            CookieManager cookieManager = CookieManager.getInstance();
//            cookieManager.removeSessionCookie();// 移除
//            cookieManager.removeAllCookie();
//            cookieManager.setAcceptCookie(true);
//            cookieManager.setCookie(preferences.getString("cookiesDomain", ""),
//                    preferences.getString("cookiesString", ""));
//            CookieSyncManager.getInstance().sync();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
//        mUploadMessage = uploadMsg;
//        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//        i.addCategory(Intent.CATEGORY_OPENABLE);
//        i.setType("image/*");
//        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
//    }
//
//    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
//        mUploadMessageForAndroid5 = uploadMsg;
//        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
//        contentSelectionIntent.setType("image/*");
//
//        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
//        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
//
//        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
//    }
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.fragment_mama_second;
//    }
//
//    public WebView getWebView() {
//        if (b != null) {
//            return b.webView;
//        }
//        return null;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        if (requestCode == FILECHOOSER_RESULTCODE) {
//            if (null == mUploadMessage) return;
//            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
//            mUploadMessage.onReceiveValue(result);
//            mUploadMessage = null;
//        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
//            if (null == mUploadMessageForAndroid5) return;
//            Uri result = (intent == null || resultCode != Activity.RESULT_OK) ? null : intent.getData();
//            if (result != null) {
//                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
//            } else {
//                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
//            }
//            mUploadMessageForAndroid5 = null;
//        }
//    }
//
//    @Override
//    public View getLoadingView() {
//        return b.layout;
//    }
}
