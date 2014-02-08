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
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
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
	private TextView  tekst;
	
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
				fragment = new MyFragment1();
				args.putInt(MyFragment1.ARG_SECTION_NUMBER, position + 1);
				break;
			case 1:
				fragment = new MyFragment2();
				break;
			case 2:
				fragment = new MyFragment3();
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
			return 3;
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
			}
			return null;
		}
	}

	public static class MyFragment1 extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public MyFragment1() {
		}

		@Override
		public View onCreateView(final LayoutInflater inflater,
				final ViewGroup container, final Bundle savedInstanceState) {
			final View v = inflater.inflate(R.layout.fragment_main_dummy, null);
			return v;
		}

		/*
		 * public View onCreateView(LayoutInflater inflater, ViewGroup
		 * container, Bundle savedInstanceState) { // View rootView =
		 * inflater.inflate(R.layout.fragment_main_dummy, false); View rootView
		 * = inflater.inflate(R.layout.m, null); TextView dummyTextView =
		 * (TextView) rootView.findViewById(R.id.section_label);
		 * dummyTextView.setText
		 * (Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER))); return
		 * rootView; }
		 */
		/**
		 * @param view
		 * @param fileContext
		 */
		/*
		 * public void zapiszPlik(View view, Context fileContext) { band =
		 * (EditText) getFragmentManager()
		 * .findFragmentById(R.layout.fragment_main_dummy).getView()
		 * .findViewById(R.id.editBand); callSign = (EditText)
		 * getFragmentManager()
		 * .findFragmentById(R.layout.fragment_main_dummy).getView()
		 * .findViewById(R.id.editCallsign); rstR = (EditText)
		 * getFragmentManager()
		 * .findFragmentById(R.layout.fragment_main_dummy).getView()
		 * .findViewById(R.id.editRstR); rstS = (EditText) getFragmentManager()
		 * .findFragmentById(R.layout.fragment_main_dummy).getView()
		 * .findViewById(R.id.editRstS); String linia =
		 * band.getText().toString() + ";" + callSign.getText().toString() + ";"
		 * + rstR.getText().toString() + ";" + rstS.getText().toString();
		 * 
		 * try { FileOutputStream fos = fileContext.openFileOutput("lista.txt",
		 * MODE_PRIVATE); fos.write(linia.getBytes()); fos.close(); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 * 
		 * }
		 */
	}

	public static class MyFragment2 extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public MyFragment2() {
		}

		@Override
		public View onCreateView(final LayoutInflater inflater,
				final ViewGroup container, final Bundle savedInstanceState) {
			final View v = inflater
					.inflate(R.layout.fragment_zegar_dummy, null);
			// TextView dummyTextView = (TextView)
			// v.findViewById(R.id.section_label);
			// dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return v;
		}
	}

	public static class MyFragment3 extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public MyFragment3() {
		}

		@Override
		public View onCreateView(final LayoutInflater inflater,
				final ViewGroup container, final Bundle savedInstanceState) {
			final View v = inflater
					.inflate(R.layout.fragment_lista_dummy, null);
			// TextView dummyTextView = (TextView)
			// v.findViewById(R.id.section_label);
			// dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return v;
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
		
		
		
	}
    
	public void Wczytaj(View view){
    tekst= (TextView) findViewById(R.id.textDane2);
    
    	final File file = new File(this.getExternalFilesDir(null), "dane.txt");
    
    	Toast.makeText(getBaseContext(),
				"test",
				Toast.LENGTH_SHORT).show();

    	StringBuilder text = new StringBuilder();

    	try {
    	    BufferedReader br = new BufferedReader(new FileReader(file));
    	    String line;

    	    while ((line = br.readLine()) != null) {
    	        text.append(line);
    	        text.append('\n');
    	    }
    	}
    	catch (IOException e) {
    	    //You'll need to add proper error handling here
    	}

    	tekst.setText(text);
  
}
}
