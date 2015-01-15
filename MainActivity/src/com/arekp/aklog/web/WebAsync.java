package com.arekp.aklog.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arekp.aklog.DxClasterFragment2;
import com.arekp.aklog.R;
import com.arekp.aklog.RaportBean;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

public class WebAsync extends AsyncTask<String, Void, List<WebBean>> {

	private Document doc = null;

	private List<WebBean> WebBean_data;

	private WebAdapter adapter;

	private Context context = null;

	private ListView listView1;

	private ProgressBar progres;

	DxClasterFragment2 df1;

	private static final String DEBUG_TAG = "WebAsync";

	@Override
	protected void onPreExecute() {
		 super.onPreExecute();
		Log.d(DEBUG_TAG, "jestesmy w onPreExecute przed preference");
		progres.setVisibility(View.VISIBLE);
		
		
	}

	public WebAsync(Context context, ListView listView2, ProgressBar progres1) {
		Log.d(DEBUG_TAG, "jestesmy w konstruktorze");
		this.context = context;
		// this.listView1 = listView2;
		 this.progres = progres1;

	}

	@Override
	protected List<WebBean> doInBackground(String... params) {
		Log.d(DEBUG_TAG, "WYKONUJEMY");
		// TODO Auto-generated method stub
		String url = params[0];

		List<WebBean> tmp = null;
		tmp = getWebBean_data(url);
		return tmp;
	}

	protected void onPostExecute(List<WebBean> result) {
		Log.d(DEBUG_TAG, "Wydalamy dane");
		progres.setVisibility(View.GONE);

		// super.onPostExecute(result);
		// adapter = new WebAdapter(this.context, R.layout.web_row, result);
		// listView1.setAdapter(adapter);
		super.onPostExecute(result);
	}

	private List<WebBean> getWebBean_data(String url1) {
		WebBean_data = new ArrayList<WebBean>();
		Log.e(DEBUG_TAG, "adres " + url1);
		try {
			doc = Jsoup.connect(url1).get();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(DEBUG_TAG, "blad czytania " + e.getMessage());
			e.printStackTrace();

		}
		Elements tables = doc.select("tbody");
		// Log.e(DEBUG_TAG,"mamy tablice" + doc.html());
		for (Element table : tables) {

			Elements trs = table.select("tr");
			Log.e(DEBUG_TAG, "mamy wiersz: " + trs.size());
			// Log.e("WEB_SPIN table",trs.size());
			String[][] trtd = new String[trs.size()][];
			// for (Element tableRow : trs){
			int wiersze =0;
			if (trs.size() > 20) wiersze=20; else wiersze=trs.size();
			for (int i = 0; i < wiersze; i++) {
				Elements tds = trs.get(i).select("td");
				trtd[i] = new String[tds.size()];
				// Log.e(DEBUG_TAG,"mamy kolumne");
				// Log.e(DEBUG_TAG,new String(null, tds.size()));
				//
				if (tds.size() == 5) {
					WebBean w = new WebBean(tds.get(0).text(), tds.get(1).text(), tds.get(2).text(), tds.get(3).text(),
							"url", tds.get(4).text());
					WebBean_data.add(w);
					// Log.e(DEBUG_TAG,w.toString());
				}
				/*
				 * for (int j = 0; j < tds.size(); j++) { // trtd[i][j] = tds.get(j).text();
				 * Log.e(DEBUG_TAG,tds.get(j).text()); }
				 */
				// Log.e(DEBUG_TAG,"----------");
			}
			// trtd now contains the desired array for this table
		}
		return WebBean_data;
	}

}
