package net.justudio.sanguoyy.sanguoyy;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import net.justudio.sanguoyy.sanguoyy.adapter.DetailAdapter;
import net.justudio.sanguoyy.sanguoyy.db.DetailDB;
import net.justudio.sanguoyy.sanguoyy.model.Detail;
import net.justudio.sanguoyy.sanguoyy.util.Constants;
import net.justudio.sanguoyy.sanguoyy.util.HttpUtil;
import net.justudio.sanguoyy.sanguoyy.util.JsoupUtil;

import java.util.List;

/**
 * 内容页活动
 */
public class DetailActivity extends Activity {

    private ListView listView;
    private DetailAdapter detailAdapter;
    public static String url;
    private DetailDB db;
    private boolean isLoad=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sanguo_detail);
        init();
        initComponent();
        if (isLoad==false){
            isLoad=true;
            new MainTask().execute(url,
                    "load");
        } else {
            List<Detail> list=db.query();
            detailAdapter.setList(list);
            detailAdapter.notifyDataSetChanged();
        }

    }

    private void init(){
        db=new DetailDB(DetailActivity.this);
        detailAdapter=new DetailAdapter(DetailActivity.this);
        url=getIntent().getExtras().getString("link");
    }

    private void initComponent(){
        listView=(ListView)findViewById(R.id.main_d);
        listView.setAdapter(detailAdapter);
    }

    private class MainTask extends AsyncTask<String, Void, Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            String temp = HttpUtil.httpGet(params[0]);
            if (temp==null){
                return Constants.DEF_RESOUT_CODE.ERROR;
            }

            List<Detail> list=JsoupUtil.getDetailList(temp);
            if (params[1].equals("load")) {
                detailAdapter.setList(list);
                return Constants.DEF_RESOUT_CODE.LOAD;
            } else {
                return Constants.DEF_RESOUT_CODE.ERROR;
            }
        }

        @Override
        protected void onPostExecute(Integer result){
            detailAdapter.notifyDataSetChanged();
            switch (result){
                case Constants.DEF_RESOUT_CODE.ERROR:
                    Toast.makeText(DetailActivity.this, "网络信号不佳", Toast.LENGTH_LONG);
                    break;
                case Constants.DEF_RESOUT_CODE.LOAD:
                        db.insert(detailAdapter.getList());
                    break;
                default:
                    break;
            }
            super.onPostExecute(result);
        }
    }
}

