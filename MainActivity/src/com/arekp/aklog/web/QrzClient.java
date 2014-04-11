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

import com.arekp.aklog.R;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public class QrzClient extends AsyncTask<String, Void, Void> {

	private static final String DEBUG_TAG = "QRZClient";

	private Context context;
	private QrzSession sess;
	private String text;
	private String sessionid = "";
	private Dialog dialog;
	SharedPreferences sharedpreferences;

	// public static final String MyPREFERENCES = "MyPrefs" ;

	@Override
	protected void onPostExecute(Void result) {
		Log.d(DEBUG_TAG, "jestesmy w onPostExecute " + sess.getKey());
		Editor editor = sharedpreferences.edit();
		dialog.setTitle("Dane z QRZ");
		TextView text = (TextView) dialog.findViewById(R.id.textDialogInfo);

		if (sess.getError().equals("")) {
			Log.d(DEBUG_TAG,
					"DANE " + sess.getError() + "name : " + sess.getName()
							+ "session :" + sess.getKey());
			text.setText(Html.fromHtml(sess.getHTMLCallsign()));
			// text.setText(Html.fromHtml(sess.getHTMLDialog()));
			editor.putString("session", sess.getKey());
		} else if (sess.getError().equals("Invalid session key")){
			editor.putString("session", "");
			text.setText(Html.fromHtml(sess.getHTMLDialog()));
		}else if (sess.getError().equals("Session Timeout")){
			editor.putString("session", "");
			text.setText(Html.fromHtml(sess.getHTMLDialog()));
		}else {
			text.setText(Html.fromHtml(sess.getHTMLDialog()));
		} 
		editor.commit();
		// 04-11 09:19:58.988: D/QRZClient(30470): Error: Session Timeout

		dialog.show();
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		Log.d(DEBUG_TAG, "jestesmy w onPreExecute przed preference");
		sharedpreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Log.d(DEBUG_TAG, "jestesmy w onPreExecute PO PREFERENCE ");
		// TODO Auto-generated method stub
		Log.d(DEBUG_TAG,
				"Przed sprawdzeniem klucza :"
						+ sharedpreferences.getString("session", ""));
		if (!sharedpreferences.getString("session", "").equals("")) {
			sessionid = sharedpreferences.getString("session", "");
		}

		dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_info);
		Log.d(DEBUG_TAG, "wczytano dialog");
		dialog.setTitle("Pobieramy Dane");
		dialog.show();
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		Log.d(DEBUG_TAG, "jestesmy w onProgressUpdate ");
		super.onProgressUpdate(values);
	}

	public QrzClient(Context context) {
		this.context = context;
	}

	public QrzSession getkey(String user, String passwd, String callsign)
			throws ClientProtocolException, URISyntaxException, IOException {
		String url = "";
		QrzSession qrzSession = null;
		try {
			// URL url = new
			// URL("http://xmldata.qrz.com/xml/current/?username=sq5ssz1;password=Moniczka1!");
			// username=sq5ssz;password=Moniczka1!
			if (passwd.equals("")) {
				url = "http://xmldata.qrz.com/xml/current/?s=" + user
						+ ";callsign=" + callsign;
			} else {
				url = "http://xmldata.qrz.com/xml/current/?username=" + user
						+ ";password=" + passwd;
			}
			try {
				XmlPullParserFactory parserCreator = XmlPullParserFactory
						.newInstance();
				XmlPullParser parser = parserCreator.newPullParser();
				Log.d(DEBUG_TAG, "url: " + url);
				// parser.setInput(getInputStream(url), "UTF_8");
				parser.setInput(getUrlData(url), "UTF_8");
				int eventType = parser.getEventType();
				qrzSession = new QrzSession();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					String tagname = parser.getName();
					switch (eventType) {
					case XmlPullParser.START_TAG:

						if (tagname.equalsIgnoreCase("Session")) {

							// qrzSession = new QrzSession();
							// Log.d(DEBUG_TAG, "START_TAG: Session " + text+
							// "KONIEC SESSION");
						} else if (tagname.equalsIgnoreCase("Callsign")) {
							// create a new instance of employee
							// qrzSession = new QrzSession();
							// Log.d(DEBUG_TAG, "START_TAG: Callsign " + text+
							// "KONIEC SESSION");
						}
						break;

					case XmlPullParser.TEXT:
						text = parser.getText();
						// Log.d(DEBUG_TAG, "TEXT: "+text+"Koniec TEXT");
						break;

					case XmlPullParser.END_TAG:
						if (tagname.equalsIgnoreCase("Session")) {
							// add employee object to list
							Log.d(DEBUG_TAG, "Session: " + text);

						} else if (tagname.equalsIgnoreCase("Key")) {
							Log.d(DEBUG_TAG, "KEY: " + text);
							qrzSession.setKey(text);
							// employee.setName(text);
						} else if (tagname.equalsIgnoreCase("Count")) {
							qrzSession.setCount(text);
							Log.d(DEBUG_TAG, "Count: " + text);
							// employee.setId(Integer.parseInt(text));
						} else if (tagname.equalsIgnoreCase("SubExp")) {
							Log.d(DEBUG_TAG, "SubExp: " + text);
							qrzSession.setSubExp(text);
							// employee.setDepartment(text);
						} else if (tagname.equalsIgnoreCase("GMTime")) {
							Log.d(DEBUG_TAG, "GMTime: " + text);
							qrzSession.setGMTime(text);
							// employee.setEmail(text);
						} else if (tagname.equalsIgnoreCase("Message")) {
							qrzSession.setMessage(text);
							Log.d(DEBUG_TAG, "Message: " + text);
						} else if (tagname.equalsIgnoreCase("Error")) {
							qrzSession.setError(text);
							Log.d(DEBUG_TAG, "Error: " + text);
						}
						// ///////////// Callsign //////////////////////
						else if (tagname.equalsIgnoreCase("call")) {
							qrzSession.setCall(text);
							Log.d(DEBUG_TAG, "call: " + text);
						} else if (tagname.equalsIgnoreCase("dxcc")) {
							qrzSession.setDxcc(text);
							Log.d(DEBUG_TAG, "dxcc: " + text);
						} else if (tagname.equalsIgnoreCase("fname")) {
							qrzSession.setFname(text);
							Log.d(DEBUG_TAG, "fname: " + text);
						} else if (tagname.equalsIgnoreCase("name")) {
							qrzSession.setName(text);
							Log.d(DEBUG_TAG, "name: " + text);
						} else if (tagname.equalsIgnoreCase("addr1")) {
							qrzSession.setAddr1(text);
							Log.d(DEBUG_TAG, "addr1: " + text);
						} else if (tagname.equalsIgnoreCase("addr2")) {
							qrzSession.setAddr2(text);
							Log.d(DEBUG_TAG, "addr2: " + text);
						} else if (tagname.equalsIgnoreCase("state")) {
							qrzSession.setState(text);
							Log.d(DEBUG_TAG, "state: " + text);
						} else if (tagname.equalsIgnoreCase("zip")) {
							qrzSession.setZip(text);
							Log.d(DEBUG_TAG, "zip: " + text);
						} else if (tagname.equalsIgnoreCase("country")) {
							qrzSession.setCountry(text);
							Log.d(DEBUG_TAG, "country: " + text);
						} else if (tagname.equalsIgnoreCase("ccode")) {
							qrzSession.setCcode(text);
							Log.d(DEBUG_TAG, "ccode: " + text);
						} else if (tagname.equalsIgnoreCase("grid")) {
							qrzSession.setGrid(text);
							Log.d(DEBUG_TAG, "grid: " + text);
						} else if (tagname.equalsIgnoreCase("county")) {
							qrzSession.setCounty(text);
							Log.d(DEBUG_TAG, "county: " + text);
						} else if (tagname.equalsIgnoreCase("land")) {
							qrzSession.setLand(text);
							Log.d(DEBUG_TAG, "land: " + text);
						} else if (tagname.equalsIgnoreCase("qslmgr")) {
							qrzSession.setQslmgr(text);
							Log.d(DEBUG_TAG, "qslmgr: " + text);
						} else if (tagname.equalsIgnoreCase("email")) {
							qrzSession.setEmail(text);
							Log.d(DEBUG_TAG, "email: " + text);
						} else if (tagname.equalsIgnoreCase("u_views")) {
							qrzSession.setU_views(text);
							Log.d(DEBUG_TAG, "u_views: " + text);
						} else if (tagname.equalsIgnoreCase("cqzone")) {
							qrzSession.setCqzone(text);
							Log.d(DEBUG_TAG, "cqzone: " + text);
						} else if (tagname.equalsIgnoreCase("ituzone")) {
							qrzSession.setItuzone(text);
							Log.d(DEBUG_TAG, "ituzone: " + text);
						} else if (tagname.equalsIgnoreCase("lotw")) {
							qrzSession.setLotw(text);
							Log.d(DEBUG_TAG, "lotw: " + text);
						} else if (tagname.equalsIgnoreCase("iota")) {
							qrzSession.setIota(text);
							Log.d(DEBUG_TAG, "iota: " + text);
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

		return res.getEntity().getContent();

	}

	@Override
	protected Void doInBackground(String... params) {
		// params[0] - login/sessionid
		// params[1] - haslo/null
		// params[2] - callsign

		Log.d(DEBUG_TAG, "w watku doInBackground");
		sess = new QrzSession();
		Log.d(DEBUG_TAG, "Powolano qrzsession");

		// dialog.setTitle("Dane z QRZ");

		try {
			Log.d(DEBUG_TAG, "Przed sprawdzeniem klucza: " + sessionid);
			if (sessionid.equals("")) {
				sess = getkey(params[0], params[1], params[2]);
			} else {
				Log.d(DEBUG_TAG, "Przed pobraniem danych: " + sessionid);
				sess = getkey(sessionid, "", params[2]);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.d(DEBUG_TAG, "blad ClientProtocolException");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			Log.d(DEBUG_TAG, "blad URISyntaxException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(DEBUG_TAG, "blad IOException");
			e.printStackTrace();
		}

		// set the custom dialog components - text, image and button
		// TextView text = (TextView) dialog.findViewById(R.id.textDialogInfo);
		// text.setText(Html.fromHtml(sess.getHTMLDialog()));
		// dialog.show();

		return null;
	}

}
