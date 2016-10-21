package com.jimei.xiaolumeimei.widget;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimei.library.utils.FileUtils;
import com.jimei.xiaolumeimei.adapter.CategoryAdapter;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jude.utils.JUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by wisdom on 16/8/8.
 */

public class CategoryTask extends AsyncTask<String, Integer, List<CategoryBean>> {

    private CategoryAdapter adapter;
    private LinearLayout mLinearLayout;

    public CategoryTask(CategoryAdapter adapter) {
        this.adapter = adapter;
    }

    public CategoryTask(CategoryAdapter adapter, LinearLayout linearLayout) {
        this.adapter = adapter;
        mLinearLayout = linearLayout;
    }

    @Override
    protected List<CategoryBean> doInBackground(String... params) {
        String categoryStr;
        InputStream in = null;
        try {
            if (FileUtils.isFileExist(XlmmConst.CATEGORY_JSON)) {
                File file = new File(XlmmConst.CATEGORY_JSON);
                in = new FileInputStream(file);
            } else {
                return null;
            }
            byte[] arrayOfByte = new byte[in.available()];
            in.read(arrayOfByte);
            categoryStr = new String(arrayOfByte, "UTF-8");
            Gson gson = new Gson();
            List<CategoryBean> list = gson.fromJson(categoryStr, new TypeToken<List<CategoryBean>>() {
            }.getType());
            if ("".equals(params[0])) {
                return list.get(0).getChilds();
            }
            for (int i = 0; i < list.size(); i++) {
                if (params[0].equals(list.get(i).getCid())) {
                    return list.get(i).getChilds();
                }
            }
            return null;
        } catch (Exception e) {
            JUtils.Log(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    JUtils.Log(e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<CategoryBean> list) {
        if (list != null && list.size() > 0) {
            adapter.updateWithClear(list);
        } else {
            adapter.clear();
            if (mLinearLayout!=null) {
                mLinearLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}