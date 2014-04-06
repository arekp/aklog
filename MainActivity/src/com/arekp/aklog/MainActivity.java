package com.arekp.aklog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.client.ClientProtocolException;

import com.arekp.aklog.database.RaportDbAdapter;
import com.arekp.aklog.web.QrzClient;
import com.arekp.aklog.web.QrzSession;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity {
	private static final String DEBUG_TAG = "MainActivity";
	private EditText band;
	private EditText mode;
	private EditText start;
	private EditText callSign;
	private EditText rstR;
	private EditText rstS;
	private EditText note;
	private static String sessionID;
	private static TextView tekst;
	protected PowerManager.WakeLock mWakeLock;
	private    RaportDbAdapter Raportdb;
	
	SharedPreferences zapisane_ustawienia;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}. FragmentActivity
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	private SharedPreferences preferences;

	/**
	 * The {@link ViewPager} that will host the section contents. 
	 */
	ViewPager mViewPager;


	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
         .detectDiskReads()
         .detectDiskWrites()
         .detectNetwork()   // or .detectAll() for all detectable problems
         .penaltyLog()
         .build());
 StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
         .detectLeakedSqlLiteObjects()
         .detectLeakedClosableObjects()
         .penaltyLog()
         .penaltyDeath()
         .build());
 
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);	    
	    
	// Action bar //////////////////////////////
	    ActionBar actionBar = getActionBar();
// actionBar.setIcon(R.drawable.sys_eq);
	    actionBar.show();
	    //actionBar.setTitle("Pasek action Bar" );
//////////////////////////////////////////	    
	    
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		zapisane_ustawienia = PreferenceManager
				.getDefaultSharedPreferences(this);

		
	}

	@Override
	public void onResume() {
		
		if (zapisane_ustawienia.getBoolean("screen_off", false)){
			Log.e("screen_off_Main","true");
			mViewPager.setKeepScreenOn(true);
		}else if (zapisane_ustawienia.getBoolean("screen_off", false)==false) {
			Log.e("screen_off_Main","false");
			mViewPager.setKeepScreenOn(false);
		}
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu_actions, menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String ktoryElement = "";

		switch (item.getItemId()) {

		case R.id.main_pref:
			ktoryElement = "pierwszy";
			// getFragmentManager().beginTransaction()
			// .replace(android.R.id.content, new View_Preference()).commit();
			// Intent i = new Intent(this, View_Preference.class);
			// startActivityForResult(i, 1);
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
		case R.id.action_settings2:
			Info();
			ktoryElement = "drugi";
			break;
		case R.id.action_KodQ:
			ktoryElement = "kodQ";
			KodQ();
			break;
		case R.id.actionBandPlan:
			ktoryElement = "action_BandPlan";
			BandPlan();
			break;
		case R.id.actionLiterowanie:
			Literowanie();
			break;
		case R.id.action_add:
			zapiszDbKarta(item.getActionView().findViewById(R.layout.fragment_main_dummy));
		break;
		/*
		 * case R.id.item3: ktoryElement = "trzeci"; break;
		 */
		default:
			ktoryElement = "zaden";

		}

		return true;

		// Czytaj więcej na:
		// http://javastart.pl/programowanie-android/menu/#ixzz2svy3jBTM
	}
	public void Literowanie(){
		
		final Dialog dialog = new Dialog(mViewPager.getContext());
		dialog.setContentView(R.layout.dialog_info);
		dialog.setTitle(R.string.literowanieTytul);

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.textDialogInfo);
		text.setText(Html.fromHtml(getString(R.string.literowanieHtml)));
		dialog.show();
	}
public void KodQ(){
	
	final Dialog dialog = new Dialog(mViewPager.getContext());
	dialog.setContentView(R.layout.dialog_info);
	dialog.setTitle(R.string.kodQTytyl);

	// set the custom dialog components - text, image and button
	TextView text = (TextView) dialog.findViewById(R.id.textDialogInfo);
	text.setText(Html.fromHtml(getString(R.string.kodQHtml)));
	dialog.show();
}
public void Info(){
	
	final Dialog dialog = new Dialog(mViewPager.getContext());
	dialog.setContentView(R.layout.dialog_info);
	dialog.setTitle(R.string.kodInfoDetal);

	// set the custom dialog components - text, image and button
	TextView text = (TextView) dialog.findViewById(R.id.textDialogInfo);
	ImageView img = (ImageView)dialog.findViewById(R.id.imageDialogInfo);
	img.setVisibility(mViewPager.VISIBLE);
	text.setText(Html.fromHtml(getString(R.string.kodInfo)));
	dialog.show();
}
public void BandPlan(){
	final Dialog dialog = new Dialog(mViewPager.getContext());
	dialog.setContentView(R.layout.dialog_info);
	dialog.setTitle(R.string.bandPlanTytul);

	// set the custom dialog components - text, image and button
	TextView text = (TextView) dialog.findViewById(R.id.textDialogInfo);
	text.setText(Html.fromHtml(getString(R.string.bandPlanHtml)));
	dialog.show();
}
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(final FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(final int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			// Fragment fragment = new DummySectionFragment();
			Fragment fragment;
			final Bundle args = new Bundle();
			switch (position) {
			case 0:
				fragment = new DodajFragment();
				args.putInt(DodajFragment.ARG_SECTION_NUMBER, position + 1);
				break;
			case 1:
				fragment = new ZegarFragment();
				break;
			case 2:
				fragment = new RaportFragment();
				break;
			case 3:
				fragment = new DxClasterFragment();
				break;
			default:
				throw new IllegalArgumentException("Invalid section number");
			}

			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(final int position) {
			final Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;
		}
	}
	public void qrzSprawdz (final View view) {
		Log.d(DEBUG_TAG," qrzSprawd nacisnieto przyskicka" );
		//QrzSession sess= new QrzSession();
		if(zapisane_ustawienia.getBoolean("qrzsynch", false)){
			Log.d(DEBUG_TAG," przed wywolaniem watku" );
			new QrzClient(this).execute(zapisane_ustawienia.getString("qrzLogin",null),zapisane_ustawienia.getString("qrzPasswd",null));
			Log.d(DEBUG_TAG," POOOwwolaniu watku" );
			/*		QrzClient q = new QrzClient();
		QrzSession sess = new QrzSession();
		try {
			 sess = q.getkey(zapisane_ustawienia.getString("qrzLogin",null),zapisane_ustawienia.getString("qrzPasswd",null));
			final Dialog dialog = new Dialog(mViewPager.getContext());
			dialog.setContentView(R.layout.dialog_info);
			dialog.setTitle("Dane z QRZ");

			// set the custom dialog components - text, image and button
			TextView text = (TextView) dialog.findViewById(R.id.textDialogInfo);
			text.setText(Html.fromHtml(sess.getHTMLDialog()));
			dialog.show();		
	
		
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
*/
		}
		else
		{
		callSign = (EditText) findViewById(R.id.editCallsign);
		String url="http://www.qrz.com/db/"+callSign.getText().toString();
	    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
		}
	}
	public void zapiszDbKarta(final View view) {
		band = (EditText) findViewById(R.id.editBand);
		callSign = (EditText) findViewById(R.id.editCallsign);
		rstR = (EditText) findViewById(R.id.editRstR);
		rstS = (EditText) findViewById(R.id.editRstS);
		note = (EditText) findViewById(R.id.editNote);
		Spinner mode = (Spinner) findViewById(R.id.mode1Spin);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		if (zapisane_ustawienia.getBoolean("utm_checkbox", false)){
		sdf.setTimeZone(TimeZone.getTimeZone("Zulu"));
		}
		String currentDateandTime = sdf.format(new Date());

/*		Date data=new Date();
		try {
			data = sdf.parse(currentDateandTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		if (band.getText().toString().equals("")) {
			Log.e("Dodawanie1", "band" + band.getText().toString());
			Toast.makeText(getBaseContext(), "Frequency nie może być puste",
					Toast.LENGTH_SHORT).show();
		} else if (callSign.getText().toString().isEmpty()) {
			Log.e("Dodawanie2", "call" + callSign.getText().toString());
			Toast.makeText(getBaseContext(), "Callsign nie może być puste",
					Toast.LENGTH_SHORT).show();
		} else if (rstR.getText().toString().isEmpty()) {
			Log.e("Dodawanie2", "call" + callSign.getText().toString());
			Toast.makeText(getBaseContext(), "rstt nie może być puste",
					Toast.LENGTH_SHORT).show();
		} else if (rstS.getText().toString().isEmpty()) {
			Log.e("Dodawanie2", "call" + callSign.getText().toString());
			Toast.makeText(getBaseContext(), "rstS nie może być puste",
					Toast.LENGTH_SHORT).show();
		}else {
		
		RaportBean rap = new RaportBean(0,band.getText().toString(), mode.getSelectedItem().toString(), currentDateandTime, callSign.getText().toString(), rstS.getText().toString(), rstR.getText().toString(), note.getText().toString());
	     Raportdb = new RaportDbAdapter(view.getContext());
	    Raportdb.open();
	    Raportdb.insertRaport(rap);
	    Raportdb.close();
		callSign.setText("");
		rstR.setText("");
		rstS.setText("");
		note.setText("");
		}
	}
	
	public void zapiszPlikKarta(final View view) {
		band = (EditText) findViewById(R.id.editBand);
		callSign = (EditText) findViewById(R.id.editCallsign);
		rstR = (EditText) findViewById(R.id.editRstR);
		rstS = (EditText) findViewById(R.id.editRstS);
		note = (EditText) findViewById(R.id.editNote);
		Spinner spinner = (Spinner) findViewById(R.id.mode1Spin);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		if (zapisane_ustawienia.getBoolean("utm_checkbox", false)){
		sdf.setTimeZone(TimeZone.getTimeZone("Zulu"));
		}
		String currentDateandTime = sdf.format(new Date());


		if (band.getText().toString().equals("")) {
			Log.e("Dodawanie1", "band" + band.getText().toString());
			Toast.makeText(getBaseContext(), "Frequency nie może być puste",
					Toast.LENGTH_SHORT).show();
		} else if (callSign.getText().toString().isEmpty()) {
			Log.e("Dodawanie2", "call" + callSign.getText().toString());
			Toast.makeText(getBaseContext(), "Callsign nie może być puste",
					Toast.LENGTH_SHORT).show();
		} else if (rstR.getText().toString().isEmpty()) {
			Log.e("Dodawanie2", "call" + callSign.getText().toString());
			Toast.makeText(getBaseContext(), "rstt nie może być puste",
					Toast.LENGTH_SHORT).show();
		} else if (rstS.getText().toString().isEmpty()) {
			Log.e("Dodawanie2", "call" + callSign.getText().toString());
			Toast.makeText(getBaseContext(), "rstS nie może być puste",
					Toast.LENGTH_SHORT).show();
		}else {
			final String linia = band.getText().toString() + ";"
					+ callSign.getText().toString() + ";"
					+ spinner.getSelectedItem().toString() + ";"
					+ currentDateandTime + ";" + rstR.getText().toString()
					+ ";" + rstS.getText().toString()
					+ ";" + note.getText().toString();

			if(isExternalStorageWritable()){
			final File plik = new File(this.getExternalFilesDir(null),
					zapisane_ustawienia.getString("plik", "dane.txt"));
			Log.e("main_zpisz_plik",this.getExternalFilesDir(null).toString());
			
			Toast.makeText(getBaseContext(), linia, Toast.LENGTH_SHORT).show();
			try {
				final FileOutputStream fos1 = new FileOutputStream(plik, true);
				fos1.write(linia.getBytes());
				fos1.write(13);
				fos1.write(10);
				fos1.close();
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
			callSign.setText("");
			rstR.setText("");
			rstS.setText("");
		}
			} 
				
	
		
	}

	public void kasujPola(final View view) {

				band = (EditText) findViewById(R.id.editBand);
		callSign = (EditText) findViewById(R.id.editCallsign);
		rstR = (EditText) findViewById(R.id.editRstR);
		rstS = (EditText) findViewById(R.id.editRstS);
		note = (EditText) findViewById(R.id.editNote);
		callSign.setText("");
		rstR.setText("");
		rstS.setText("");
		band.setText("");
		note.setText("");

	}

	/* Sprawdza czy zewnętrzna pamięć jest gotowa do zapisu */
	public boolean isExternalStorageWritable() {
	 String state = Environment.getExternalStorageState();
	 if (Environment.MEDIA_MOUNTED.equals(state)) {
	 return true;
	 }
	 return false;
	}
	/* Sprawdza czy zewnętrzna pamięć jest gotowa do odczytu */
	public boolean isExternalStorageReadable() {
	 String state = Environment.getExternalStorageState();
	 if (Environment.MEDIA_MOUNTED.equals(state) ||
	 Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	 return true;
	 }
	 return false;
	}
/*  @Override
	public void onDestroy() {
    	if (Raportdb != null){
			Raportdb.updateAllOffStatus();
			Raportdb.close();
		}
        super.onDestroy();
    }*/


}
