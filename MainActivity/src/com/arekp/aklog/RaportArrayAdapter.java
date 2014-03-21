package com.arekp.aklog;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
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
	 
	    static class ViewHolder {
	        public TextView lvFreq;
	        public TextView lvMode;
	        public TextView lvDate;
	        public TextView lvCall;
	        public TextView lvRst;
	        public TextView lvRsr;
	        public TextView lvNote;
	    }
	    
	    /**
	     * Populate new items in the list.
	     */
	    @Override public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        ViewHolder viewHolder;

	        if (view == null) {
	            view = mInflater.inflate(R.layout.lista_wiersz, parent, false);
	            viewHolder = new ViewHolder();
	            viewHolder.lvFreq = (TextView)view.findViewById(R.id.text_lista_wiersz1);
	            viewHolder.lvCall = (TextView)view.findViewById(R.id.text_lista_wiersz2);
	            viewHolder.lvMode = (TextView)view.findViewById(R.id.text_lista_wiersz3);
	            viewHolder.lvDate = (TextView)view.findViewById(R.id.text_lista_wiersz4);
	            view.setTag(viewHolder);
	        } 
	     else {
	    	    viewHolder = (ViewHolder) view.getTag();
	           // view = convertView;
	        }
	 
	        RaportBean item = getItem(position);
	        viewHolder.lvFreq.setText(new DecimalFormat("#.####").format(item.getFrequency()).toString());
	        viewHolder.lvMode.setText(item.getMode());
	        viewHolder.lvDate.setText(item.getTime());
	        viewHolder.lvCall.setText(item.getCallsign());
//Log.d("ArrayAdapter",item.getStatus().toString());
/*     if(item.getStatus()) {
	            viewHolder.lvFreq
	                .setPaintFlags(viewHolder.lvFreq.getPaintFlags() |
	                        Paint.STRIKE_THRU_TEXT_FLAG);
	        } else {
	            viewHolder.lvFreq
	                .setPaintFlags(viewHolder.lvFreq.getPaintFlags() &
	                        ~Paint.STRIKE_THRU_TEXT_FLAG);
	        }*/
	        return view;
	    }
	    

	} 

