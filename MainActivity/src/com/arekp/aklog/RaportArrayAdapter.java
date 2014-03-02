package com.arekp.aklog;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RaportArrayAdapter extends ArrayAdapter<RaportBean> {
	 private final LayoutInflater mInflater;
	 
	    public RaportArrayAdapter(Context context) {
	        super(context, android.R.layout.simple_list_item_2);
	        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }
	 
	    public void setData(List<RaportBean> data) {
	        clear();
	        if (data != null) {
	            for (RaportBean appEntry : data) {
	                add(appEntry);
	            }
	        }
	    }
	 
	    /**
	     * Populate new items in the list.
	     */
	    @Override public View getView(int position, View convertView, ViewGroup parent) {
	        View view;
	 
	        if (convertView == null) {
	            view = mInflater.inflate(R.layout.lista_wiersz, parent, false);
	        } else {
	            view = convertView;
	        }
	 
	        RaportBean item = getItem(position);
	        TextView t1 = (TextView)view.findViewById(R.id.text_lista_wiersz1);
	        t1.setText(item.getFrequency().toString());
	        TextView t2 = (TextView)view.findViewById(R.id.text_lista_wiersz2);
	        t2.setText(item.getMode());
	        TextView t3 = (TextView)view.findViewById(R.id.text_lista_wiersz3);
	        t3.setText(item.getTime());
	        TextView t4 = (TextView)view.findViewById(R.id.text_lista_wiersz4);
	        t4.setText(item.getCallsign());
	       // TextView t5 = (TextView)view.findViewById(R.id.text_lista_wiersz5);
	      //  t5.setText(item.getRs()+" "+item.getRt());

	 
	        return view;
	    }
	    
/*	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        RowBeanHolder holder = null;
	 
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);
	 
	            holder = new RowBeanHolder();
	            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
	            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
	 
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
	    }*/
	} 

