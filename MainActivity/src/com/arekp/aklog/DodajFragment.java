package com.arekp.aklog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DodajFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	public DodajFragment() {
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
