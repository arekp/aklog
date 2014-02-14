package com.arekp.aklog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DigitalClock;
import android.widget.TextView;

public class ZegarFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	private TextView mTimeLabel;
	private Handler mHandler = new Handler();
	SharedPreferences zapisane_ustawienia;

	public ZegarFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_zegar_dummy, null);

  	mTimeLabel = (TextView) v.findViewById(R.id.textmTimeLabel);
		zapisane_ustawienia = PreferenceManager.getDefaultSharedPreferences(v.getContext());
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
		   // Landscape
			mTimeLabel.setTextSize(80);
		}
		else {
			mTimeLabel.setTextSize(30);
		}
		// TextView dummyTextView = (TextView)
		// v.findViewById(R.id.section_label);
		
		// dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
		return v;
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (zapisane_ustawienia.getBoolean("utm_checkbox", false)) {
				sdf.setTimeZone(TimeZone.getTimeZone("Zulu"));
			}
			String currentDateandTime = sdf.format(new Date());

			mTimeLabel.setText(currentDateandTime);
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

}
