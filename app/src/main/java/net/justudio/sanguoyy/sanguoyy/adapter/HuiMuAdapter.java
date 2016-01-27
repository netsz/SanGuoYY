package net.justudio.sanguoyy.sanguoyy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.justudio.sanguoyy.sanguoyy.R;
import net.justudio.sanguoyy.sanguoyy.model.HuiMu;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/20 0020.
 */
public class HuiMuAdapter extends BaseAdapter {

    private ViewHolder holder;
    private LayoutInflater inflater;
    private Context context;
    private List<HuiMu> list;

    public HuiMuAdapter(Context context) {
        super();
        this.context=context;
        inflater=LayoutInflater.from(context);
        list=new ArrayList<HuiMu>();
    }

    public void setList(List<HuiMu> list){
        this.list=list;
    }

    public void addList(List<HuiMu> list){
        this.list.addAll(list);
    }

    public List<HuiMu> getList(){
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


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.huimuu);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HuiMu huiMu = list.get(position);
            if (huiMu != null) {
                holder.title.setText(huiMu.getName());
            }


            return convertView;



    }


    private class ViewHolder{
        TextView id;
        TextView title;
    }
}
