package com.arekp.aklog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RaportFragment extends Fragment {
	private static TextView tekst;
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	private ListView rozbudowana_lista;

	 SharedPreferences zapisane_ustawienia;

	public RaportFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final View v = inflater.inflate(R.layout.fragment_lista_dummy, null);
	    zapisane_ustawienia = PreferenceManager.getDefaultSharedPreferences(v.getContext());

	    rozbudowana_lista = (ListView) v.findViewById(R.id.listRaportu);
	    List<RaportBean> przykladowe_dane2 = new ArrayList<RaportBean>();
	    
	    przykladowe_dane2.add(new RaportBean("frq", "callsign", "mode", "data", "rs", "rt"));

	 
	    
		final File file = new File(container.getContext().getExternalFilesDir(
				null),zapisane_ustawienia.getString("plik", "dane.txt"));
		StringBuilder text = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;

			while ((line = br.readLine()) != null) {
				Log.e("wiersze_danych",line);
				String[] parts = line.split(";");
				 przykladowe_dane2.add(new RaportBean(parts[0],parts[1], parts[2], parts[3], parts[4], parts[5]));
				//text.append(line);
				//text.append('\n');
			}
		} catch (IOException e) {

		}
		

	    RaportArrayAdapter RaportArrayAdapter1 =new RaportArrayAdapter(v.getContext());
	    RaportArrayAdapter1.setData(przykladowe_dane2);
	    
	    rozbudowana_lista.setAdapter(RaportArrayAdapter1);

		// TextView dummyTextView = (TextView)
		// v.findViewById(R.id.section_label);
		// dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
		return v;
	}

}