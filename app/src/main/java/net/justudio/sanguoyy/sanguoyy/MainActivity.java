package net.justudio.sanguoyy.sanguoyy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.justudio.sanguoyy.sanguoyy.adapter.HuiMuAdapter;
import net.justudio.sanguoyy.sanguoyy.db.HuiMuDB;
import net.justudio.sanguoyy.sanguoyy.model.HuiMu;
import net.justudio.sanguoyy.sanguoyy.util.Constants;
import net.justudio.sanguoyy.sanguoyy.util.HttpUtil;
import net.justudio.sanguoyy.sanguoyy.util.JsoupUtil;
import net.justudio.sanguoyy.sanguoyy.util.UrlUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HuiMuDB db;
    private HuiMuAdapter adapter;
    private ListView listView;
    private boolean isLoad=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initComponent();
        if (isLoad==false){
            isLoad=true;
            new MainTask().execute(UrlUtil.Home,
                    "load");
        } else {
            List<HuiMu> list=db.query();
            adapter.setList(list);
            adapter.notifyDataSetChanged();
        }

    }

    private void init(){
        db=new HuiMuDB(MainActivity.this);
        adapter = new HuiMuAdapter(MainActivity.this);
    }

    private void initComponent(){
        listView=(ListView)findViewById(R.id.main_l);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HuiMu huiMu=(HuiMu)adapter.getItem(position);
                Intent i =new Intent();
                i.setClass(MainActivity.this,DetailActivity.class );
                i.putExtra("link", huiMu.getLink());
                startActivity(i);
            }
        });
    }

    private class MainTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String...params){
            String temp= HttpUtil.httpGet(params[0]);
            if (temp==null){
                return Constants.DEF_RESOUT_CODE.ERROR;
            }
            List<HuiMu> list= JsoupUtil.getHuiMuList(temp);
            if (params[1].equals("load")) {
                adapter.setList(list);
                return Constants.DEF_RESOUT_CODE.LOAD;
            } else {
                return Constants.DEF_RESOUT_CODE.ERROR;
            }
        }


        @Override
        protected void onPostExecute(Integer result){
            adapter.notifyDataSetChanged();
            switch (result){
                case Constants.DEF_RESOUT_CODE.ERROR:
                    Toast.makeText(MainActivity.this, "网络信号不佳", Toast.LENGTH_LONG);
                    break;
                case Constants.DEF_RESOUT_CODE.LOAD:
                    db.insert(adapter.getList());
                    break;
                default:
                    break;
            }
            super.onPostExecute(result);
        }
    }
}
