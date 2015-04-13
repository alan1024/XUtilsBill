package com.alan.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.alan.model.Bill;
import com.alan.xutilsbill.R;


import java.util.List;
import java.util.Map;

/**
 * Created by aaa on 15-4-10.
 * User:alan
 * Date:
 * Email:
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Bill>  list;

    public MyAdapter(Context context,List<Bill>  list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view= (View) LayoutInflater.from(context).inflate(R.layout.item_listview,null);
            viewHolder=new ViewHolder();
            viewHolder.textView_time= (TextView) view.findViewById(R.id.textView_time);
            viewHolder.textView_money= (TextView) view.findViewById(R.id.textView_money);
            viewHolder.textView_title= (TextView) view.findViewById(R.id.textView_title);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.textView_title.setText(list.get(i).getCategory().getType());
        viewHolder.textView_money.setText(Float.toString(list.get(i).getMoney()));
        viewHolder.textView_time.setText(list.get(i).getDate());
        return view;
    }
    class ViewHolder{
        private TextView textView_title,textView_money,textView_time;
    }
}
