package com.jude.beam.model;

import android.content.Context;

/**
 * Created by zhuchenxi on 15/6/7.
 */
public abstract class AbsModel {
    public static final <T extends AbsModel> T getInstance(Class<T> clazz){
        AbsModel model = ModelManager.mModelMap.get(clazz);
        if (model == null)throw new RuntimeException(clazz.getName()+" No Found , Have you declare MODEL in the manifests?");
        return (T) model;
    }
    protected <N> N getNetManager(){return null;}
    protected void onAppCreate(Context ctx){}
    protected void onAppCreateOnBackThread(Context ctx){}
    protected final void runOnBackThread(Runnable runnable){
        ModelManager.runOnBackThread(runnable);
    }
}
