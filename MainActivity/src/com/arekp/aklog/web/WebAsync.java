package com.arekp.aklog.web;

import android.os.AsyncTask;
import android.util.Log;

public class WebAsync extends AsyncTask<String, Void, Void> {

	private static final String DEBUG_TAG = "WebAsync";
	@Override
	protected void onPreExecute() {
		Log.d(DEBUG_TAG, "jestesmy w onPreExecute przed preference");
	}
	
	@Override
	protected Void doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	  @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
	    }
	  

}
