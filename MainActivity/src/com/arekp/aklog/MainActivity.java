package com.arekp.aklog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.arekp.aklog.database.RaportDbAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private EditText band;
	private EditText mode;
	private EditText start;
	private EditText callSign;
	private EditText rstR;
	private EditText rstS;
	private EditText note;
	private static TextView tekst;
	protected PowerManager.WakeLock mWakeLock;
	
	SharedPreferences zapisane_ustawienia;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}. 
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	private SharedPreferences preferences;

	/**
	 * The {@link ViewPager} that will host the section contents. 
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		zapisane_ustawienia = PreferenceManager
				.getDefaultSharedPreferences(this);
		
		//  final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
      //    this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
		
	//	Log.e("screen_off_Main",zapisane_ustawienia.getBoolean("screen_off", false)); 

		
	}

	@Override
	protected void onResume() {
		
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

			ktoryElement = "drugi";
			break;
		/*
		 * case R.id.item3: ktoryElement = "trzeci"; break;
		 */
		default:
			ktoryElement = "żaden";

		}

		return true;

		// Czytaj więcej na:
		// http://javastart.pl/programowanie-android/menu/#ixzz2svy3jBTM
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
		
		RaportBean rap = new RaportBean(band.getText().toString(), mode.getSelectedItem().toString(), currentDateandTime, callSign.getText().toString(), rstS.getText().toString(), rstR.getText().toString(), note.getText().toString());
	    RaportDbAdapter Raportdb = new RaportDbAdapter(view.getContext());
	    Raportdb.open();
	    Raportdb.insertRaport(rap);
	    Raportdb.close();
		callSign.setText("");
		rstR.setText("");
		rstS.setText("");
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
		callSign.setText("");
		rstR.setText("");
		rstS.setText("");
		band.setText("");

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

}
