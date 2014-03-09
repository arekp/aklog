package com.arekp.aklog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.arekp.aklog.database.RaportDbAdapter;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;
import android.widget.AdapterView.OnItemClickListener;

public class RaportFragment extends Fragment {
	private static TextView tekst;

	public static final String ARG_SECTION_NUMBER = "section_number";

	private ListView rozbudowana_lista;
	private RaportDbAdapter Raportdb;

	SharedPreferences zapisane_ustawienia;

	public RaportFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		final View v = inflater.inflate(R.layout.fragment_lista_dummy, null);
		zapisane_ustawienia = PreferenceManager.getDefaultSharedPreferences(v
				.getContext());

		rozbudowana_lista = (ListView) v.findViewById(R.id.listRaportu);
		View backToTop = inflater.inflate(R.layout.lista_wiersz_footer, null);

		rozbudowana_lista.addFooterView(backToTop);
		List<RaportBean> przykladowe_dane2 = new ArrayList<RaportBean>();

		Raportdb = new RaportDbAdapter(v.getContext());
		Raportdb.open();
		Cursor mCursor = Raportdb.getAllReports();
		getActivity().startManagingCursor(mCursor);

		while (mCursor.moveToNext()) {
			RaportBean rap = new RaportBean(mCursor);
			przykladowe_dane2.add(rap);
		}

		// Raportdb.close();

		RaportArrayAdapter RaportArrayAdapter1 = new RaportArrayAdapter(
				v.getContext());
		RaportArrayAdapter1.setData(przykladowe_dane2);

		rozbudowana_lista.setAdapter(RaportArrayAdapter1);

		rozbudowana_lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if(Raportdb.updateTodoStatus(pos,true)){
					Log.d("RapotFragment","true");
				Toast.makeText(v.getContext(),				
						new Integer(pos).toString(), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(v.getContext(),				
							new Integer(pos).toString()+"NIe udalo sie :( ", Toast.LENGTH_SHORT).show();
				}

			}
		});
	
		return v;
	}


	   @Override
	public void onResume() {
	        // TODO Auto-generated method stub
	        super.onResume();
	    }

	    @Override
		public void onPause() {
	        // TODO Auto-generated method stub
	        super.onPause();
	    }
	    @Override
		public void onDestroy() {
	    	
	        if(Raportdb != null)
	        	Raportdb.updateAllOffStatus();
	        	Raportdb.close();
	        super.onDestroy();
	    }
}
