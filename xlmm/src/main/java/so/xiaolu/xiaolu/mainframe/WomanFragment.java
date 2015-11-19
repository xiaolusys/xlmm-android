package so.xiaolu.xiaolu.mainframe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.asynctask.PosterAsyncTask;
import so.xiaolu.xiaolu.asynctask.ProductListAsyncTask;
import so.xiaolu.xiaolu.asynctask.YesterdayAsyncTask;
import so.xiaolu.xiaolu.mainsetting.MainUrl;

public class WomanFragment extends Fragment {
    View view;
    Context context;
    String TAG = "huangyan";
    public WomanFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        view = inflater.inflate(R.layout.woman_main_fragment, container, false);
        MainUrl url = new MainUrl();
        ProductListAsyncTask yesterdayAsyncTask = new ProductListAsyncTask(view,context, url.getWOMAN_URL());
        yesterdayAsyncTask.execute(1000);
        return view;
    }


}