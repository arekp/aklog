package com.arekp.aklog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import com.arekp.aklog.database.RaportDbAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class RaportFragment extends Fragment {
	private static TextView tekst;

	public static final String ARG_SECTION_NUMBER = "section_number";

	private ListView rozbudowana_lista;
	private RaportDbAdapter Raportdb;
	private View v;
	private List<RaportBean> przykladowe_dane2;
	private RaportArrayAdapter RaportArrayAdapter1;
	private Spinner spinerCzas;
	SharedPreferences zapisane_ustawienia;
	long DAY_IN_MS = 1000 * 60 * 60 * 24;
	private long id_tmp; 

	public RaportFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {

		// final View
		v = inflater.inflate(R.layout.fragment_lista_dummy, null);
		zapisane_ustawienia = PreferenceManager.getDefaultSharedPreferences(v
				.getContext());

		rozbudowana_lista = (ListView) v.findViewById(R.id.listRaportu);

		przykladowe_dane2 = new ArrayList<RaportBean>();

		Raportdb = new RaportDbAdapter(v.getContext());
		Raportdb.open();
		Cursor mCursor = Raportdb.getAllReports();
	//getActivity().startManagingCursor(mCursor);

		while (mCursor.moveToNext()) {
			RaportBean rap = new RaportBean(mCursor);
			przykladowe_dane2.add(rap);
		}

		RaportArrayAdapter1 = new RaportArrayAdapter(v.getContext());
		RaportArrayAdapter1.setData(przykladowe_dane2);

		RaportArrayAdapter1.notifyDataSetChanged();
		rozbudowana_lista.setAdapter(RaportArrayAdapter1);
		mCursor.close();
		Raportdb.close();
		
		rozbudowana_lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				edit(przykladowe_dane2.get(pos));
			

			}
		});
	
		
		// Podpinamy menu pod liste
		registerForContextMenu(rozbudowana_lista);

		final ImageButton loginButton = (ImageButton) v
				.findViewById(R.id.buttonListaExport1);
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				exportcsv(v);
				exportAdiShort(v);
				Toast.makeText(
						v.getContext(),
						"Dane zostały wyeksportowane do skonfigurowanego katalogu",
						Toast.LENGTH_SHORT).show();
			}
		});

		spinerCzas = (Spinner) v.findViewById(R.id.spinnerOkresLista);
		spinerCzas.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Object item = arg0.getItemAtPosition(arg2);
		

				if (item != null) {					
					if (item.equals("Wszystko")) {
						refreshList(null);
					} else if (item.equals("Dzień")) {
						
						refreshList(new Date(System.currentTimeMillis()
								- (1 * DAY_IN_MS)));
						Log.d("RaportFragment", "wybrana opcja "+new Date(System.currentTimeMillis()
								- (1 * DAY_IN_MS)).toString());
						
					} else if (item.equals("Tydzień")) {
						refreshList(new Date(System.currentTimeMillis()
								- (7 * DAY_IN_MS)));
						Log.d("RaportFragment", "wybieramy " + new Date(System.currentTimeMillis()
								- (7 * DAY_IN_MS)).toString());
					} else if (item.equals("Miesiąc")) {
						refreshList(new Date(System.currentTimeMillis()
								- (30 * DAY_IN_MS)));
					}
					/*Toast.makeText(v.getContext(), item.toString(),
							Toast.LENGTH_SHORT).show();*/
				}
		/*		Toast.makeText(v.getContext(), "Nic nie wybrano",
						Toast.LENGTH_SHORT).show();*/
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		return v;
	}

	public void refreshList(Date data) {
		Raportdb.open();
		if (data == null) {
			Log.d("RaportFragment", "refresh list null");
			
			Cursor mCursor = mCursor = Raportdb.getAllReports();
			przykladowe_dane2.clear();
			while (mCursor.moveToNext()) {
				RaportBean rap = new RaportBean(mCursor);
				przykladowe_dane2.add(rap);
			}
			RaportArrayAdapter1.setData(przykladowe_dane2);
			RaportArrayAdapter1.notifyDataSetChanged();
			mCursor.close();
			rozbudowana_lista.refreshDrawableState();
		} else {
			Log.d("RaportFragment", "refresh list not null " + data.toLocaleString());
			Cursor mCursor = mCursor = Raportdb.getReportsSortDate(data);
			przykladowe_dane2.clear();
			while (mCursor.moveToNext()) {
				RaportBean rap = new RaportBean(mCursor);
				przykladowe_dane2.add(rap);
			}
			RaportArrayAdapter1.setData(przykladowe_dane2);
			RaportArrayAdapter1.notifyDataSetChanged();
			mCursor.close();
			rozbudowana_lista.refreshDrawableState();
		}
		 Raportdb.close();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
/*		case R.id.menuedytuj:
			Intent intent = new Intent(v.getContext(), EditListActivity.class);
			intent.putExtra("id", przykladowe_dane2.get(info.position).getId());
			startActivity(intent);
			
			 * Toast.makeText(v.getContext(), "Opcja item01 na elemencie: " +
			 * przykladowe_dane2.get(info.position).getCallsign() ,
			 * Toast.LENGTH_LONG).show();
			 
			break;*/
		case R.id.menudetal:
			detal(przykladowe_dane2.get(info.position));
			break;
		case R.id.menuusun:
			/*
			 * Raportdb.deleteTodo(przykladowe_dane2.get(info.position).getId());
			 * refreshList();
			 */

			AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
					v.getContext());

			// Setting Dialog Title
			alertDialog2.setTitle("Potwierdz usuniecie...");

			// Setting Dialog Message
			alertDialog2
					.setMessage("Na pewno chcesz usunac ten wpis "
							+ przykladowe_dane2.get(info.position)
									.getCallsign()
							+ " "
							+ przykladowe_dane2.get(info.position)
									.getFrequency() + "?");

			alertDialog2.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							Raportdb.open();
							Raportdb.deleteTodo(przykladowe_dane2.get(
									info.position).getId());
							Raportdb.close();
							refreshList(null);
						}
					});
			// Setting Negative "NO" Btn
			alertDialog2.setNegativeButton("NO",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							dialog.cancel();
						}
					});

			// Showing Alert Dialog
			alertDialog2.show();
			/*
			 * Toast.makeText(v.getContext(), "Opcja item02 na elemencie: " +
			 * przykladowe_dane2.get(info.position).getCallsign(),
			 * Toast.LENGTH_LONG).show();
			 */
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

		menu.setHeaderTitle(przykladowe_dane2.get(info.position).getCallsign()
				+ " " + przykladowe_dane2.get(info.position).getFrequency());

	}

	public void detal(RaportBean rap) {

		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.dialog_info);
		dialog.setTitle(R.string.raportDetal);

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.textDialogInfo);
		text.setText(Html.fromHtml(rap.detalHtml()));
		dialog.show();
	}

	public void edit(RaportBean rap) {

		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.dialog_edit);
		dialog.setTitle("Edit");

		final EditText band = (EditText) dialog.findViewById(R.id.editBand);
		final EditText callSign = (EditText) dialog.findViewById(R.id.editCallsign);
		final EditText rstR = (EditText) dialog.findViewById(R.id.editRstR);
		final EditText rstS = (EditText) dialog.findViewById(R.id.editRstS);
		final EditText note = (EditText) dialog.findViewById(R.id.editNote);
		final Spinner mode = (Spinner) dialog.findViewById(R.id.mode1Spin);
		final TextView textCzasDodaj = (TextView) dialog.findViewById(R.id.textCzasDodaj);
		final TextView id = (TextView) dialog.findViewById(R.id.edit_id);
		final ImageButton ok = (ImageButton) dialog.findViewById(R.id.buttonListaExport1);
		
		band.setText(rap.getFrequency().toString());
		callSign.setText(rap.getCallsign());
		rstR.setText(new Integer(rap.getRt()).toString());
		rstS.setText(new Integer(rap.getRs()).toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		id_tmp=rap.getId();
		textCzasDodaj.setText(sdf.format(rap.getData()));
		mode.setSelection(getIndex(mode, rap.getMode()));
		note.setText(rap.getNote());
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				if (band.getText().toString().equals("")) {
					Log.e("Dodawanie1", "band" + band.getText().toString());
					Toast.makeText(v.getContext(), "Frequency nie może być puste",
							Toast.LENGTH_SHORT).show();
				} else if (callSign.getText().toString().isEmpty()) {
					Log.e("Dodawanie2", "call" + callSign.getText().toString());
					Toast.makeText(v.getContext(), "Callsign nie może być puste",
							Toast.LENGTH_SHORT).show();
				} else if (rstR.getText().toString().isEmpty()) {
					Log.e("Dodawanie2", "call" + callSign.getText().toString());
					Toast.makeText(v.getContext(), "rstt nie może być puste",
							Toast.LENGTH_SHORT).show();
				} else if (rstS.getText().toString().isEmpty()) {
					Log.e("Dodawanie2", "call" + callSign.getText().toString());
					Toast.makeText(v.getContext(), "rstS nie może być puste",
							Toast.LENGTH_SHORT).show();
				}else {
				
				RaportBean rap = new RaportBean(id_tmp,band.getText().toString(), mode.getSelectedItem().toString(), textCzasDodaj.getText().toString(), callSign.getText().toString(), rstS.getText().toString(), rstR.getText().toString(), note.getText().toString());
			  //  RaportDbAdapter Raportdb = new RaportDbAdapter(v.getContext());
			    Raportdb.open();
			    Raportdb.updateRaport(rap);
			    Raportdb.close();
			    refreshList(null);
			    dialog.cancel();
			}}
		});

		
		dialog.show();
	}
	
	private int getIndex(Spinner spinner, String myString)
	 {
	  int index = 0;

	  for (int i=0;i<spinner.getCount();i++){
	   if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
	    index = i;
	    i=spinner.getCount();//will stop the loop, kind of break, by making condition false
	   }
	  }
	  return index;
	 } 
	@Override
	public void onResume() {
		Log.d("RaportFragment", "jestesmy w resume");
		refreshList(null);
		Log.d("RaportFragment", "po odnowieniu listy");
		if (zapisane_ustawienia.getBoolean("screen_off", false)){
			Log.e("screen_off_Main","true");
			v.setKeepScreenOn(true);
		}else if (zapisane_ustawienia.getBoolean("screen_off", false)==false) {
			Log.e("screen_off_Main","false");
			v.setKeepScreenOn(false);
		}
		Log.d("RaportFragment", "po zapisaniu zmian w setings");
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
/*	@Override
	public void onDestroy() {

		if (Raportdb != null){
			Raportdb.updateAllOffStatus();
			Raportdb.close();
		}
		super.onDestroy();
	}
*/
	/* Sprawdza czy zewnętrzna pamięć jest gotowa do zapisu */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	public void exportcsv(View view) {

		String baseDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		Log.d("export", baseDir);
		String cvs = "";
		if (isExternalStorageWritable()) {
			File plik = new File(baseDir + "/"
					+ zapisane_ustawienia.getString("katalog", null));
			if (!plik.exists()) {
				if (plik.mkdirs())
					Toast.makeText(v.getContext(),
							"Nowy katalog zostal zalozony", Toast.LENGTH_SHORT)
							.show();
			}

			plik = new File(baseDir + "/"
					+ zapisane_ustawienia.getString("katalog", null) + "/"
					+ zapisane_ustawienia.getString("plik", "dane") + ".csv");
			if (plik.exists()) {
				if (plik.delete()) {

				}
			}
			try {
				final FileOutputStream fos1 = new FileOutputStream(plik, true);
				for (Iterator<RaportBean> i = przykladowe_dane2.iterator(); i
						.hasNext();) {
					cvs = i.next().ExportCvs();
					fos1.write(cvs.getBytes());
					fos1.write(13);
					fos1.write(10);
				}
				fos1.close();
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}
	}

	public void exportAdiShort(View view) {
		SimpleDateFormat dat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat czas = new SimpleDateFormat("HHmmss");
		RaportBean rap;
		String baseDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		Log.d("export", baseDir);
		String adi = "ADIF 2.0 " + "Wygenerowany przez program AkLog v.1.0.1 "
				+ "<EOH> ";

		if (isExternalStorageWritable()) {
			File plik = new File(baseDir + "/"
					+ zapisane_ustawienia.getString("katalog", null));
			if (!plik.exists()) {
				if (plik.mkdirs())
					Toast.makeText(v.getContext(),
							"Nowy katalog zostal zalozony", Toast.LENGTH_SHORT)
							.show();
			}

			plik = new File(baseDir + "/"
					+ zapisane_ustawienia.getString("katalog", null) + "/"
					+ zapisane_ustawienia.getString("plik", "dane") + ".adi");
			if (plik.exists()) {
				if (plik.delete()) {

				}
			}
			try {
				final FileOutputStream fos1 = new FileOutputStream(plik, true);
				fos1.write(adi.getBytes());
				fos1.write(13);
				fos1.write(10);
				for (Iterator<RaportBean> i = przykladowe_dane2.iterator(); i
						.hasNext();) {
					rap = i.next();
					adi = "<QSO_DATE:8>" + dat.format(rap.getData())
							+ " <TIME_ON:6>" + czas.format(rap.getData())
							+ " <BAND:"+new Integer(getBand(rap.getFrequency()).length()).toString()+">"+getBand(rap.getFrequency()) + " " 
							+ " <FREQ:"+new Integer(rap.getFrequency().toString().length()).toString() +">" + rap.getFrequency().toString()
							+ " <MODE:"+new Integer(rap.getMode().length()).toString()+">" + rap.getMode() 
							+ " <CALL:"+new Integer(rap.getCallsign().length()).toString()+">" + rap.getCallsign()
							+ " <RST_RCVD:"+new Integer(rap.getRstoString().length()).toString()+">" + rap.getRstoString()
							+ " <GRIDSQUARE:"+new Integer(zapisane_ustawienia.getString("qth", "logo").length()).toString()+">"+ zapisane_ustawienia.getString("qth", "logo")
							+ " <RST_SENT:"+new Integer(rap.getRttoString().length()).toString()+">" + rap.getRttoString()
							+ " <OPERATOR:"+new Integer(zapisane_ustawienia.getString("callsign", "st").length()).toString()+">"+ zapisane_ustawienia.getString("callsign", "logo")
							+ " <COMMENT:"+ new Integer(rap.getNote().length()).toString()+">"+rap.getNote()+" <EOR>";
					fos1.write(adi.getBytes());
					fos1.write(13);
					fos1.write(10);
				}
				fos1.close();
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}
	}
	public String getBand(float freq){
		String fr="";
		if (freq>1.8 & freq <2){
			fr="160m";	
		}else if (freq>3.5 & freq <42){
			fr="80m";	
		}else if (freq>5.2 & freq <5.5){
			fr="60m";	
		}else if (freq>7 & freq <7.3){
			fr="40m";	
		}else if (freq>10 & freq <10.15){
			fr="30m";	
		}else if (freq>14 & freq <14.35){
			fr="20m";	
		}else if (freq>18 & freq <18.168){
			fr="17m";	
		}else if (freq>21 & freq <21.45){
			fr="15m";	
		}else if (freq>24 & freq <24.99){
			fr="12m";	
		}else if (freq>28 & freq <29.7){
			fr="10m";	
		}else if (freq>50 & freq <54){
			fr="6m";	
		}else if (freq>144 & freq <148){
			fr="2m";	
		}else if (freq>420 & freq <450){
			fr="70cm";	
		}else{fr="bład danych";}
		
		return fr;
	}


}
