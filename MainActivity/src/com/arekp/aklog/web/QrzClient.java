package com.arekp.aklog.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

public class QrzClient {
	 private static final String DEBUG_TAG = "QRZClient";
	 
	    private Context context;
	    private QrzSession qrzSession;
	    private  String text;
	    
	   public QrzClient(Context context) {
	        this.context = context;
	    }
	   public QrzClient() {
	       
	    }
	public QrzSession getkey(String user, String passwd) throws ClientProtocolException, URISyntaxException, IOException{
		QrzSession qrzSession = null;
		try {
			//URL url = new URL("http://xmldata.qrz.com/xml/current/?username=sq5ssz1;password=Moniczka1!");
			//username=sq5ssz;password=Moniczka1!
			String url = "http://xmldata.qrz.com/xml/current/?username="+user+";password="+passwd;
	try {
		XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
		XmlPullParser parser = parserCreator.newPullParser();
		
		//parser.setInput(getInputStream(url), "UTF_8");
		parser.setInput(getUrlData(url), "UTF_8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagname = parser.getName();
            switch (eventType) {
            case XmlPullParser.START_TAG:
                if (tagname.equalsIgnoreCase("Session")) {
                    // create a new instance of employee
                	qrzSession = new QrzSession();
                    Log.d(DEBUG_TAG, "START_TAG: Session "+text);
                }
                break;

            case XmlPullParser.TEXT:
               text = parser.getText();
                Log.d(DEBUG_TAG, text);
                break;

            case XmlPullParser.END_TAG:
                if (tagname.equalsIgnoreCase("Session")) {
                    // add employee object to list
                	Log.d(DEBUG_TAG, "Session: "+text);
                	
                } else if (tagname.equalsIgnoreCase("Key")) {
                	  Log.d(DEBUG_TAG,"KEY: "+ text);
                	  qrzSession.setKey(text);
                	  //employee.setName(text);
                } else if (tagname.equalsIgnoreCase("Count")) {
                	qrzSession.setCount(text);
                	Log.d(DEBUG_TAG,"Count: "+ text);
                	//employee.setId(Integer.parseInt(text));
                } else if (tagname.equalsIgnoreCase("SubExp")) {
                	Log.d(DEBUG_TAG,"SubExp: "+ text);
                	qrzSession.setSubExp(text);
                	//employee.setDepartment(text);
                } else if (tagname.equalsIgnoreCase("GMTime")) {
                	Log.d(DEBUG_TAG, "GMTime: "+text);
                	//employee.setEmail(text);
                } else if (tagname.equalsIgnoreCase("Message")) {
                	qrzSession.setGMTime(text);
                 	Log.d(DEBUG_TAG, "Message: "+text);
                }else if (tagname.equalsIgnoreCase("Error")) {
                	qrzSession.setError(text);
                 	Log.d(DEBUG_TAG, "Error: "+text);
                }
                break;

            default:
                break;
            }
            try {
				eventType = parser.next();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
		
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		   } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return qrzSession;
	   }
	   
	   public InputStream getInputStream(URL url) {
		   try {
		     return url.openConnection().getInputStream();
		   } catch (IOException e) {
			   Log.d(DEBUG_TAG, "Blad pobierania danych z adresu");
		     return null;
		   }
		 }
	   
	   public InputStream getUrlData(String url) 

			   throws URISyntaxException, ClientProtocolException, IOException {

			     DefaultHttpClient client = new DefaultHttpClient();

			     HttpGet method = new HttpGet(new URI(url));

			     HttpResponse res = client.execute(method);

			     return  res.getEntity().getContent();

			   }
	   

}
