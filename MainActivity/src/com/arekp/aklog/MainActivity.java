package com.arekp.aklog;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
           // Fragment fragment = new DummySectionFragment();
            Fragment fragment; 
            Bundle args = new Bundle();  
            switch(position){
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
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
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
        private EditText band;
        private EditText mode;
        private EditText start;
        private EditText callSign;
        private EditText rstR;
        private EditText rstS;

        public MyFragment1() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
        	    Bundle savedInstanceState) {
        	    View v = inflater.inflate(R.layout.fragment_main_dummy, null);
        	    return v;
        }
        
 //       public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
 //          // View rootView = inflater.inflate(R.layout.fragment_main_dummy, false);
 //           View rootView = inflater.inflate(R.layout.m, null);
 //           TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
 //           dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
 //           return rootView;
 //       }
        public void zapiszPlik(View view,Context fileContext){
        	band = (EditText) getFragmentManager().findFragmentById(R.layout.fragment_main_dummy).getView().findViewById(R.id.editBand);
        	callSign = (EditText) getFragmentManager().findFragmentById(R.layout.fragment_main_dummy).getView().findViewById(R.id.editCallsign);
        	rstR = (EditText) getFragmentManager().findFragmentById(R.layout.fragment_main_dummy).getView().findViewById(R.id.editRstR);
        	rstS = (EditText) getFragmentManager().findFragmentById(R.layout.fragment_main_dummy).getView().findViewById(R.id.editRstS);
        	String linia = band.getText().toString()+";"+callSign.getText().toString()+";"+rstR.getText().toString()+";"+rstS.getText().toString();
        	
        	try {
    			FileOutputStream fos = fileContext.openFileOutput("lista.txt",MODE_PRIVATE);
    			fos.write(linia.getBytes());
    			fos.close();
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        }
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
        	    Bundle savedInstanceState) {
        	    View v = inflater.inflate(R.layout.fragment_zegar_dummy, null);
         //   TextView dummyTextView = (TextView) v.findViewById(R.id.section_label);
          //  dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
        	    Bundle savedInstanceState) {
        	    View v = inflater.inflate(R.layout.fragment_lista_dummy, null);
         //   TextView dummyTextView = (TextView) v.findViewById(R.id.section_label);
         //   dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return v;
        }
    }

   

}
