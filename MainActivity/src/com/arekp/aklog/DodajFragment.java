package com.arekp.aklog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.arekp.aklog.web.WebBean;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DodajFragment extends Fragment {

	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	SharedPreferences zapisane_ustawienia;
	private static final String DEBUG_TAG = "DodajFragment";
	private TextView textCzasDodaj;

	private Handler mHandler = new Handler();
	
	 public DodajFragment() {
		 Log.d(DEBUG_TAG ,"Jestesmy w konstruktorze");
	 }

	@Override
	public View onCreateView(final LayoutInflater inflater,
		final ViewGroup container, final Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_main_dummy, null);
		zapisane_ustawienia = PreferenceManager.getDefaultSharedPreferences(v.getContext());
	 textCzasDodaj = (TextView) v.findViewById(R.id.textCzasDodaj);
	 
/*		if (zapisane_ustawienia.getBoolean("screen_off",false)){
			Log.e("screen_off_Dodaj","true");
			v.setKeepScreenOn(true);

		}else if (!zapisane_ustawienia.getBoolean("screen_off",false)) {
			Log.e("screen_off_Dodaj","false");
			v.setKeepScreenOn(false);
		}*/
		//w celu spersonalizowania actionbar
		setHasOptionsMenu(true);
		
/*		  Bundle args = getArguments();
		  WebBean aa = (WebBean) args.getSerializable("arek");
			Log.e(DEBUG_TAG,aa.getName());*/
		return v;
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.add_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
/*	    case R.id.activity_menu_item:
	        // Not implemented here
	        return false;*/
	    case R.id.action_add:
	        // Do Fragment menu item stuff here
	        return true;
	    default:
	        break;
	    }

	    return false;
	}
	
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (zapisane_ustawienia.getBoolean("utm_checkbox", false)) {
				sdf.setTimeZone(TimeZone.getTimeZone("Zulu"));
			}
			String currentDateandTime = sdf.format(new Date());

			textCzasDodaj.setText(currentDateandTime);
			mHandler.postAtTime(this, (SystemClock.uptimeMillis() + 1000));
		}
	};

	@Override
	public void onStart() {
		super.onPause();
		mHandler.removeCallbacks(mUpdateTimeTask);
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	@Override
	public void onPause() {
		super.onPause();
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

    @Override
	public void onDestroy() {

        super.onDestroy();
    }

}
