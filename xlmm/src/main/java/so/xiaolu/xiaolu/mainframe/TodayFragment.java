package so.xiaolu.xiaolu.mainframe;

import so.xiaolu.xiaolu.R;
import so.xiaolu.xiaolu.asynctask.IndexAsyncTask;
import so.xiaolu.xiaolu.asynctask.PosterAsyncTask;
import so.xiaolu.xiaolu.mainsetting.MainUrl;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.squareup.okhttp.OkHttpClient;


public class TodayFragment extends Fragment {
    View view;
    Context context;

    public TodayFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        view = inflater.inflate(R.layout.today_main_fragment, container, false);
        MainUrl url = new MainUrl();
        IndexAsyncTask asyncTask = new IndexAsyncTask(view, context, url.getTODAY_URL());               //首页商品
        PosterAsyncTask posterasyncTask = new PosterAsyncTask(view, context, url.getTODAYPOSTER_URL());  //海报进程
        asyncTask.execute(1000);
        posterasyncTask.execute(1000);
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
}