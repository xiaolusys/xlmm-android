package com.jimei.xiaolumeimei.widget;

import android.os.AsyncTask;
import android.os.Environment;
import android.view.Menu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CategoryAdapter;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.utils.FileUtils;
import com.jude.utils.JUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by wisdom on 16/8/8.
 */

public class CategoryTask extends AsyncTask<String, Integer, List<CategoryBean.ChildsBean>> {

    private CategoryAdapter adapter;
    private Menu menu;

    public CategoryTask(CategoryAdapter adapter, Menu menu) {
        this.adapter = adapter;
        this.menu = menu;
    }

    @Override
    protected List<CategoryBean.ChildsBean> doInBackground(String... params) {
        String categoryStr;
        InputStream in = null;
        String fileaddress = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/xlmmcategory/" + "category.json";
        try {
            if (FileUtils.isFileExist(fileaddress)) {
                File file = new File(fileaddress);
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
    protected void onPostExecute(List<CategoryBean.ChildsBean> list) {
        if (list != null && list.size() > 0) {
            adapter.update(list);
        } else {
            menu.removeItem(R.id.category);
        }
    }
}