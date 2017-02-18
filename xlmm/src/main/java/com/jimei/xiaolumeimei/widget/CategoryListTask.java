package com.jimei.xiaolumeimei.widget;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.adapter.CategoryNameListAdapter;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CategoryBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by wisdom on 16/9/23.
 */

public class CategoryListTask extends AsyncTask<String, Integer, List<CategoryBean>> {

    private CategoryNameListAdapter adapter;

    public CategoryListTask(CategoryNameListAdapter adapter) {
        this.adapter = adapter;
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
            List<CategoryBean> list = new Gson().fromJson(categoryStr, new TypeToken<List<CategoryBean>>() {
            }.getType());
            if (list.size() > 0) {
                return list;
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
        }
    }
}