package so.xiaolu.xiaolu.mainframe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.asynctask.ProductListAsyncTask;
import so.xiaolu.xiaolu.mainsetting.MainUrl;

public class ChildFragment extends Fragment {
    View view;
    Context context;
    String TAG = "huangyan";
    public ChildFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        view = inflater.inflate(R.layout.child_main_fragment, container, false);
        MainUrl url = new MainUrl();
        Log.d("huangyan","woman");
        ProductListAsyncTask yesterdayAsyncTask = new ProductListAsyncTask(view,context, url.getCHILD_URL());
        yesterdayAsyncTask.execute(1000);
        return view;
    }


}