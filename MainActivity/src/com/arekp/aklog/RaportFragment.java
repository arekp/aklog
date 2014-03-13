package com.arekp.aklog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.arekp.aklog.database.RaportDbAdapter;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
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
    private View v;
    private List<RaportBean> przykladowe_dane2;
    private RaportArrayAdapter RaportArrayAdapter1;
	SharedPreferences zapisane_ustawienia;

	public RaportFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		//final View 
		v = inflater.inflate(R.layout.fragment_lista_dummy, null);
		zapisane_ustawienia = PreferenceManager.getDefaultSharedPreferences(v
				.getContext());

		rozbudowana_lista = (ListView) v.findViewById(R.id.listRaportu);
	

	przykladowe_dane2 = new ArrayList<RaportBean>();

		Raportdb = new RaportDbAdapter(v.getContext());
		Raportdb.open();
		Cursor mCursor = Raportdb.getAllReports();
		getActivity().startManagingCursor(mCursor);

		while (mCursor.moveToNext()) {
			RaportBean rap = new RaportBean(mCursor);
			przykladowe_dane2.add(rap);
		}

		 RaportArrayAdapter1 = new RaportArrayAdapter(
				v.getContext());
		RaportArrayAdapter1.setData(przykladowe_dane2);

		RaportArrayAdapter1.notifyDataSetChanged();
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
							new Integer(pos).toString()+" NIe udalo sie :( ", Toast.LENGTH_SHORT).show();
				}

			}
		});
	//Podpinamy menu pod liste
		registerForContextMenu(rozbudowana_lista);
		return v;
	}
	
	public void refreshList()
	{

		Cursor mCursor=	 mCursor = Raportdb.getAllReports();
		przykladowe_dane2.clear();
 	while (mCursor.moveToNext()) {
		RaportBean rap = new RaportBean(mCursor);
		przykladowe_dane2.add(rap);
	}
	RaportArrayAdapter1.setData(przykladowe_dane2);
	RaportArrayAdapter1.notifyDataSetChanged();
	
	rozbudowana_lista.refreshDrawableState();
	}

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        
        switch (item.getItemId()) {
        case R.id.menuedytuj:
        
            Toast.makeText(v.getContext(), 
                    "Opcja item01 na elemencie: " + przykladowe_dane2.get(info.position).getCallsign() , 
                    Toast.LENGTH_LONG).show();
            break;
            
        case R.id.menuusun:
         	Raportdb.deleteTodo(przykladowe_dane2.get(info.position).getId());
         	refreshList();
                  	
/*        	Toast.makeText(v.getContext(), 
                    "Opcja item02 na elemencie: " + przykladowe_dane2.get(info.position).getCallsign(), 
                    Toast.LENGTH_LONG).show();*/
            break;
            
        default:
            break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        
        inflater.inflate(R.menu.listraport_context_menu, menu);
        
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        
        menu.setHeaderTitle(przykladowe_dane2.get(info.position).getCallsign()+" "+przykladowe_dane2.get(info.position).getFrequency() );
        
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
