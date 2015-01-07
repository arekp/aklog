package com.arekp.aklog.web;

import java.util.ArrayList;
import java.util.List;

import com.arekp.aklog.R;

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
    List<WebBean> data = null;
 
    public WebAdapter(Context context, int layoutResourceId,  List<WebBean> result) {
        super(context, layoutResourceId, result);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = result;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;
 
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.web_row, parent, false);
 
            holder = new RowBeanHolder();
            holder.Name = (TextView)row.findViewById(R.id.txtName);
            holder.Freq = (TextView)row.findViewById(R.id.TextFreq);
            holder.UTC = (TextView)row.findViewById(R.id.TextUtc);
            holder.Spotter = (TextView)row.findViewById(R.id.TextSpotter);
            holder.Comment = (TextView)row.findViewById(R.id.TextComment);
   
          
 
            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }
 
        WebBean object = data.get(position);
        holder.Name.setText(object.Name);
        holder.Freq.setText(object.Freq);
        holder.UTC.setText(object.UTC);
        holder.Comment.setText(object.Comment);
        holder.Spotter.setText(object.Spotter);
     
 
        return row;
    }
 
    static class RowBeanHolder
    {
    	TextView Name ;
    	TextView Freq ;
    	TextView UTC;
    	TextView Spotter;
    	TextView SpotterUrl;
    	TextView Comment;
  
    }

}
