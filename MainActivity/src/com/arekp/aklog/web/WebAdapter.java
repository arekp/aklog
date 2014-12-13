package com.arekp.aklog.web;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WebAdapter extends ArrayAdapter<WebBean>{
 
	// http://javastart.pl/static/programowanie-android/wlasny-widok-listowy/
	
    Context context;
    int layoutResourceId;   
    WebBean data[] = null;
 
    public WebAdapter(Context context, int layoutResourceId, WebBean[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;
 
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(web_row, parent, false);
 
            holder = new RowBeanHolder();
            holder.Name = (TextView)row.findViewById(R.id.txtName);
            holder.Freq = (TextView)row.findViewById(R.id);
            holder.UTC = (TextView)row.findViewById(R.id.imgIcon);
            holder.Spotter = (TextView)row.findViewById(R.id.imgIcon);
            holder.Comment = (TextView)row.findViewById(R.id.imgIcon);
   
          
 
            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }
 
        RowBean object = data[position];
        holder.txtTitle.setText(object.title);
        holder.imgIcon.setImageResource(object.icon);
 
        return row;
    }
 
    static class RowBeanHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }

}
