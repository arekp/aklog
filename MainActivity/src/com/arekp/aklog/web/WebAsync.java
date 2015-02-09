package com.arekp.aklog.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import android.widget.Spinner;

public class WebAsync extends AsyncTask<String, Void, List<WebBean>> {

	private Document doc = null;

	private List<WebBean> WebBean_data;

	private WebAdapter adapter;

	private Context context = null;

	private ListView listView1;

	private ProgressBar progres;
	private Spinner spin;

	  private final static int GET = 1;
	  private final static int POST = 2;
	  private JSONArray contacts = null;
	  
	private static final String DEBUG_TAG = "WebAsync";

	@Override
	protected void onPreExecute() {
		 super.onPreExecute();
		Log.d(DEBUG_TAG, "jestesmy w onPreExecute przed preference");
		progres.setVisibility(View.VISIBLE);
		spin.setVisibility(View.GONE);
		
	}

	public WebAsync(Context context, ListView listView2, ProgressBar progres1, Spinner spin1 ) {
		Log.d(DEBUG_TAG, "jestesmy w konstruktorze");
		this.context = context;
		this.listView1 = listView2;
		 this.spin = spin1;

		 this.progres = progres1;

	}

	@Override
	protected List<WebBean> doInBackground(String... params) {
		Log.d(DEBUG_TAG, "WYKONUJEMY");
		// TODO Auto-generated method stub
		String url = params[0];

		List<WebBean> tmp = null;
		if (url.startsWith("http://www.dxsummit.fi")) {
			tmp = getJsonWebBean(url);
		}
		else {
			tmp = getWebBean_data(url);
		}
		
		return tmp;
	}

	protected void onPostExecute(List<WebBean> result) {
		Log.d(DEBUG_TAG, "Wydalamy dane");
		progres.setVisibility(View.GONE);
		spin.setVisibility(View.VISIBLE);
		WebBean_data = result;
		// super.onPostExecute(result);
		 adapter = new WebAdapter(this.context, R.layout.web_row, result);
		 listView1.setAdapter(adapter);
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
			WebBean w = new WebBean("Problem z połaczeniem","Data error","","","","");
			WebBean_data.add(w);
			return WebBean_data;
		}
		Elements tables = doc.select("tbody");
		// Log.e(DEBUG_TAG,"mamy tablice" + doc.html());
		for (Element table : tables) {

			Elements trs = table.select("tr");
			Log.e(DEBUG_TAG, "mamy wiersz: " + trs.size());
			// Log.e("WEB_SPIN table",trs.size());
			String[][] trtd = new String[trs.size()][];
			// for (Element tableRow : trs){
			//int wiersze =0;
			//if (trs.size() > 20) wiersze=20; else wiersze=trs.size();
			for (int i = 0; i < trs.size(); i++) {
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

	public String makeServiceCall(String url, int method) {
		String   response="";
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
             
            // Checking http request method type
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // adding post params
             //   if (params != null) {
             //       httpPost.setEntity(new UrlEncodedFormEntity(params));
             //   }
 
                httpResponse = httpClient.execute(httpPost);
 
            } else if (method == GET) {
                // appending params to url
             //   if (params != null) {
             //       String paramString = URLEncodedUtils
             //               .format(params, "utf-8");
              //      url += "?" + paramString;
              //  }
                HttpGet httpGet = new HttpGet(url);
 
                httpResponse = httpClient.execute(httpGet);
 
            }
            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        return response;
 
    }
	private List<WebBean>  getJsonWebBean(String url){
		WebBean_data = new ArrayList<WebBean>();
		 // Creating service handler class instance
     //   ServiceHandler sh = new ServiceHandler();

        // Making a request to url and getting response
        String jsonStr = makeServiceCall(url, GET);

        Log.d(DEBUG_TAG,">> " + jsonStr +" <<");

        if (jsonStr != null) {
        	  Log.d(DEBUG_TAG,"Obiekt do parsowanie nie jest pusty");
            try {
                //JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray jsonarray = new JSONArray(jsonStr);
                Log.d(DEBUG_TAG,"Mamy obiekt json");
                Log.d(DEBUG_TAG,"nazwa tablicy "+ jsonarray.toString());
                // Getting JSON Array node
             //   contacts = jsonObj.getJSONArray("");
                Log.d(DEBUG_TAG,"Mamy tabli json "+jsonarray.length());
                // looping through All Contacts
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject c = jsonarray.getJSONObject(i);
      /*               
                    String id = c.getString(TAG_ID);
                    String name = c.getString(TAG_NAME);
                    String email = c.getString(TAG_EMAIL);
                    String address = c.getString(TAG_ADDRESS);
                    String gender = c.getString(TAG_GENDER);

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject(TAG_PHONE);
                    String mobile = phone.getString(TAG_PHONE_MOBILE);
                    String home = phone.getString(TAG_PHONE_HOME);
                    String office = phone.getString(TAG_PHONE_OFFICE);

                    // tmp hashmap for single contact
                    HashMap<String, String> contact = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    contact.put(TAG_ID, id);
                    contact.put(TAG_NAME, name);
                    contact.put(TAG_EMAIL, email);
                    contact.put(TAG_PHONE_MOBILE, mobile);*/
                    
                    WebBean w = new WebBean(c.getString("dx_call"), c.getString("frequency"), c.getString("time"),c.getString("de_call"),
							"url",c.getString("info"));
                    WebBean_data.add(w);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(DEBUG_TAG, "Couldn't get any data from the url");
        }
        Log.d(DEBUG_TAG, "Koniec pobierania danych");
        return WebBean_data;
	}
}
