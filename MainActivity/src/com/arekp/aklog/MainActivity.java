package com.arekp.aklog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
	private static TextView  tekst;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

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
	            //getFragmentManager().beginTransaction()
                //.replace(android.R.id.content, new View_Preference()).commit();
	            //Intent i = new Intent(this, View_Preference.class);
	            //startActivityForResult(i, 1);
	    		Intent intent = new Intent(this,SettingsActivity.class);
	    		startActivity(intent);
	            break;
	        case R.id.action_settings2:

	        	ktoryElement = "drugi";
	            break;
	            /*     case R.id.item3:
	            ktoryElement = "trzeci";
	            break;*/
	        default:
	            ktoryElement = "żaden";
	 
	        }
	 
	        Toast.makeText(getApplicationContext(), "Element: " + ktoryElement,
	                Toast.LENGTH_LONG).show();
	 
	        return true;


	//Czytaj więcej na: http://javastart.pl/programowanie-android/menu/#ixzz2svy3jBTM
	}
	

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
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


	public void zapiszPlikKarta(final View view) {
		band = (EditText) findViewById(R.id.editBand);
		callSign = (EditText)findViewById(R.id.editCallsign);
		rstR = (EditText)findViewById(R.id.editRstR);
		rstS = (EditText)findViewById(R.id.editRstS);
		Spinner spinner = (Spinner) findViewById(R.id.mode1Spin);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDateandTime = sdf.format(new Date());
		Log.e("DodawanieB","band test");
		Log.e("DodawanieB","band "+band.getText().toString());
		Log.e("DodawanieC","call "+callSign.getText().toString());
		 
		if (band.getText().toString().equals("")){
			Log.e("Dodawanie1","band"+band.getText().toString());
			Toast.makeText(getBaseContext(),
					"Frequency nie może być puste",
					Toast.LENGTH_SHORT).show();
		}else if (callSign.getText().toString().isEmpty()) {
			Log.e("Dodawanie2","call"+callSign.getText().toString());
			Toast.makeText(getBaseContext(),
					"Callsign nie może być puste",
					Toast.LENGTH_SHORT).show();
		}else{
		final String linia= band.getText().toString() + ";"
				+ callSign.getText().toString() + ";" 
				+ spinner.getSelectedItem().toString()+ ";"
				+ currentDateandTime + ";"
				+ rstR.getText().toString() + ";"
				+ rstS.getText().toString();
		
		final File plik = new File(this.getExternalFilesDir(null), "dane.txt");
		Toast.makeText(getBaseContext(),
				linia,
				Toast.LENGTH_SHORT).show();
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
		}finally{
			
		}
		callSign.setText("");
		rstR.setText("59");
		rstS.setText("59");
		}
	}
	public void kasujPola (final View view){
		band = (EditText) findViewById(R.id.editBand);
		callSign = (EditText)findViewById(R.id.editCallsign);
		rstR = (EditText)findViewById(R.id.editRstR);
		rstS = (EditText)findViewById(R.id.editRstS);
		callSign.setText("");
		rstR.setText("59");
		rstS.setText("59");
		band.setText("");
		
	}
    

}
