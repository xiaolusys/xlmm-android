package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.res.AssetManager;
import android.content.res.Resources;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;

import java.lang.reflect.Method;

/**
 * Created by wisdom on 16/11/21.
 */

public class ProxyActivity extends BaseSwipeBackCompatActivity {

    private AssetManager mAssetManager;
    private Object mDexPath;
    private Resources mResources;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_proxy;
    }

    @Override
    protected void initViews() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, mDexPath);
            mAssetManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = super.getResources();
        mResources = new Resources(mAssetManager, superRes.getDisplayMetrics(),
                superRes.getConfiguration());
    }

    @Override
    protected void initData() {

    }

}
