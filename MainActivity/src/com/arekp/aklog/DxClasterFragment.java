
package com.arekp.aklog;

import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class DxClasterFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	private WebView web;
	private ProgressBar progres;
	private Spinner spin;
	
	static HashMap<String, String> codeHash = new HashMap<String, String>();
	 
	public static void init() {
	        codeHash.put("ALL", "http://dxcluster.sdr-radio.com/top_250_ALL.html");
	        codeHash.put("HF", "http://dxcluster.sdr-radio.com/top_250_HF.html");
	        codeHash.put("137kHz", "http://dxcluster.sdr-radio.com/top_250_137_kHz.html");
	        codeHash.put("1.8MHz", "http://dxcluster.sdr-radio.com/top_250_1.8_MHz.html");
	        codeHash.put("3.5MHz", "http://dxcluster.sdr-radio.com/top_250_3.5_MHz.html");
	        codeHash.put("5MHz", "http://dxcluster.sdr-radio.com/top_250_5_MHz.html");
	        codeHash.put("7MHz", "http://dxcluster.sdr-radio.com/top_250_7_MHz.html");
	        codeHash.put("10MHz", "http://dxcluster.sdr-radio.com/top_250_10_MHz.html");
	        codeHash.put("14MHz", "http://dxcluster.sdr-radio.com/top_250_14_MHz.html");
	        codeHash.put("18MHz", "http://dxcluster.sdr-radio.com/top_250_18_MHz.html");
	        codeHash.put("21MHz", "http://dxcluster.sdr-radio.com/top_250_21_MHz.html");
	        codeHash.put("24MHz", "http://dxcluster.sdr-radio.com/top_250_24_MHz.html");
	        codeHash.put("28MHz", "http://dxcluster.sdr-radio.com/top_250_28_MHz.html");
	        codeHash.put("50MHz", "http://dxcluster.sdr-radio.com/top_250_50_MHz.html");
	        codeHash.put("70MHz", "http://dxcluster.sdr-radio.com/top_250_70_MHz.html");
	        codeHash.put("144MHz", "http://dxcluster.sdr-radio.com/top_250_144_MHz.html");
	        codeHash.put("220MHz", "http://dxcluster.sdr-radio.com/top_250_220_MHz.html");
	        codeHash.put("430MHz", "http://dxcluster.sdr-radio.com/top_250_430_MHz.html");
	    }

	public DxClasterFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		init();
		final View v = inflater.inflate(R.layout.fragment_dx_claster_dummy, null);
		spin = (Spinner) v.findViewById(R.id.web_spinner);
		
		web = (WebView) v.findViewById(R.id.webView1);
		web.setWebViewClient(new MyWebViewClient());
		
		progres = (ProgressBar) v.findViewById(R.id.progressBar1);
		progres.setVisibility(View.GONE);
		
		web.getSettings().setJavaScriptEnabled(true);
		web.loadUrl(codeHash.get("ALL"));
		spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        /**
	         * Called when a new item is selected (in the Spinner)
	         */
	         public void onItemSelected(AdapterView<?> parent, View view, 
	                    int pos, long id) {
	                // An spinnerItem was selected. You can retrieve the selected item using
	                // parent.getItemAtPosition(pos)
	
	        	 Log.e("WEB_SPIN",spin.getSelectedItem().toString());
	     	    web.getSettings().setJavaScriptEnabled(true);
	    		web.loadUrl(codeHash.get(spin.getSelectedItem().toString()));
        	 	            }

	            public void onNothingSelected(AdapterView<?> parent) {
	                // Do nothing, just another required interface callback
	            }

	    }); // (optional)
		
		
	 //   web.getSettings().setJavaScriptEnabled(true);
		//web.loadUrl(codeHash.get("ALL"));
		// TextView dummyTextView = (TextView)
		// v.findViewById(R.id.section_label);
		// dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
		return v;
	}
	
	
	

	  private class MyWebViewClient extends WebViewClient {  
	         @Override
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	             if (Uri.parse(url).getHost().equals("dxcluster.sdr-radio.com")) {
	                 // This is my web site, so do not override; let my WebView load the page
	                 return false;
	             }
	             // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
	             Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	             startActivity(intent);
	             return true;
	             
	             //   view.loadUrl(url);
	              //  return true;
	            }
	 
	         @Override
	        public void onPageFinished(WebView view, String url) {
	        	 progres.setVisibility(View.GONE);
	        	 DxClasterFragment.this.progres.setProgress(100);
	            super.onPageFinished(view, url);
	        }
	 
	         @Override
	        public void onPageStarted(WebView view, String url, Bitmap favicon) {
	        	 progres.setVisibility(View.VISIBLE);
	             DxClasterFragment.this.progres.setProgress(0);
	            super.onPageStarted(view, url, favicon);
	        }
	    }
	 
/*	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.web_view, menu);
	        return true;
	    }*/
	 
	    public void setValue(int progress) {
	        this.progres.setProgress(progress);       
	    }
	}

