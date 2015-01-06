package com.arekp.aklog.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arekp.aklog.R;
import com.arekp.aklog.RaportBean;

import android.os.AsyncTask;
import android.util.Log;

public class WebAsync extends AsyncTask<String, Void, Void> {

	 private Document doc=null;
	 private  List<WebBean> WebBean_data;
	 
	private static final String DEBUG_TAG = "WebAsync";
	@Override
	protected void onPreExecute() {
		Log.d(DEBUG_TAG, "jestesmy w onPreExecute przed preference");
	}
	
	@Override
	protected Void doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		getWebBean_data("test");
		return null;
		
	}
	  @Override
	    protected void onPostExecute(Void result) {
		 
	        super.onPostExecute(result);
	    }
	  
	  
	  private void getWebBean_data(String Url){
		  WebBean_data= new ArrayList<WebBean>();
		  
		  try {
				doc = Jsoup.connect("http://dxcluster.sdr-radio.com/top_250_ALL.html").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 Log.e(DEBUG_TAG,"blad czytania " + e.getMessage());
				 e.printStackTrace();
				
			}
      	 Elements tables = doc.select("tbody");
      	//  Log.e(DEBUG_TAG,"mamy tablice" + doc.html());
      	 for (Element table : tables) {

      	     Elements trs = table.select("tr");
      	     Log.e(DEBUG_TAG,"mamy wiersz: "+trs.size());
      	     //Log.e("WEB_SPIN table",trs.size());
      	     String[][] trtd = new String[trs.size()][];
      	     //for (Element tableRow : trs){  
      	     for (int i = 0; i < trs.size(); i++) {
      	         Elements tds = trs.get(i).select("td");
      	         trtd[i] = new String[tds.size()];
      	  	     Log.e(DEBUG_TAG,"mamy kolumne");
      	  	//     Log.e(DEBUG_TAG,new String(null, tds.size()));
      	//	
     if(tds.size() == 5){
    	 WebBean w =new WebBean(tds.get(0).text(), tds.get(1).text(), tds.get(2).text(), tds.get(3).text(), "url", tds.get(4).text());
   	 WebBean_data.add(w);
         	   Log.e(DEBUG_TAG,w.toString());
     }      
/*   	         for (int j = 0; j < tds.size(); j++) {
      	           //  trtd[i][j] = tds.get(j).text(); 
      	             Log.e(DEBUG_TAG,tds.get(j).text());
      	         }*/
   	      Log.e(DEBUG_TAG,"----------");
      	     }
      	     // trtd now contains the desired array for this table
      	 }
	  }

}
