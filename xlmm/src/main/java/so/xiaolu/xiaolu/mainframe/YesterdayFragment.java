package so.xiaolu.xiaolu.mainframe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.asynctask.PosterAsyncTask;
import so.xiaolu.xiaolu.asynctask.YesterdayAsyncTask;
import so.xiaolu.xiaolu.mainsetting.MainUrl;

public class YesterdayFragment extends Fragment {
    View view;
    Context context;
    private static String TAG = "YesterdayFragment";
    public YesterdayFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        view = inflater.inflate(R.layout.yesterday_main_fragment, container, false);
        MainUrl url = new MainUrl();
        YesterdayAsyncTask yesterdayAsyncTask = new YesterdayAsyncTask(view,context, url.getYESTERDAY_URL());  // 昨日主页
        yesterdayAsyncTask.execute(1000);
        PosterAsyncTask posterasyncTask = new PosterAsyncTask(view,context, url.getYESTERDAY_POSTER_URL()); // 昨日海报
        posterasyncTask.execute(1000);
        return view;
    }


}