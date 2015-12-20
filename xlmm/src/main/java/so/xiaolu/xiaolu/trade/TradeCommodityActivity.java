package so.xiaolu.xiaolu.trade;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import so.xiaolu.xiaolu.R;

public class TradeCommodityActivity extends AppCompatActivity {
    String TAG = "TradeCommodityActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradecommodity);
        this.setTitle("购物车");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //ActionBar actionbar = getActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG,"nav back");
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
