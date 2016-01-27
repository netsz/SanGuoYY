package net.justudio.sanguoyy.sanguoyy.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.justudio.sanguoyy.sanguoyy.R;
import net.justudio.sanguoyy.sanguoyy.model.Detail;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/20 0020.
 */
public class DetailAdapter extends BaseAdapter {
    private ViewHolder holder;
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Detail> list;

    public DetailAdapter(Context context){
        super();
        this.context=context;
        layoutInflater=layoutInflater.from(context);
        list=new ArrayList<Detail>();
    }

    public void setList(List<Detail> list){
        this.list=list;
    }

    public void addList(List<Detail> list){
        this.list.addAll(list);
    }

    public List<Detail> getList(){
        return list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.detail_item, null);
            holder = new ViewHolder();
            holder.content=(TextView)convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder)convertView.getTag();
        }

        Detail detail=list.get(position);
        if(detail!=null){
            holder.content.setText(Html.fromHtml(detail.getContent()));
        }

        return convertView;
    }

    public class ViewHolder{
        TextView id;
        TextView content;
    }
}
