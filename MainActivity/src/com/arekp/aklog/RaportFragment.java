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
		getActivity().startManagingCursor(mCursor);

		while (mCursor.moveToNext()) {
			RaportBean rap = new RaportBean(mCursor);
			przykladowe_dane2.add(rap);
		}

		RaportArrayAdapter1 = new RaportArrayAdapter(v.getContext());
		RaportArrayAdapter1.setData(przykladowe_dane2);

		RaportArrayAdapter1.notifyDataSetChanged();
		rozbudowana_lista.setAdapter(RaportArrayAdapter1);

		rozbudowana_lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if (Raportdb.updateTodoStatus(pos, true)) {
					Log.d("RapotFragment", "true");
					Toast.makeText(v.getContext(), new Integer(pos).toString(),
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(v.getContext(),
							new Integer(pos).toString() + " NIe udalo sie :( ",
							Toast.LENGTH_SHORT).show();
				}

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
					Toast.makeText(v.getContext(), item.toString(),
							Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(v.getContext(), "Nic nie wybrano",
						Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		return v;
	}

	public void refreshList(Date data) {

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

			rozbudowana_lista.refreshDrawableState();
		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case R.id.menuedytuj:
			Intent intent = new Intent(v.getContext(), EditListActivity.class);
			intent.putExtra("id", przykladowe_dane2.get(info.position).getId());
			startActivity(intent);
			/*
			 * Toast.makeText(v.getContext(), "Opcja item01 na elemencie: " +
			 * przykladowe_dane2.get(info.position).getCallsign() ,
			 * Toast.LENGTH_LONG).show();
			 */
			break;
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

			// Setting Icon to Dialog
			// alertDialog2.setIcon(R.drawable.delete);

			// Setting Positive "Yes" Btn
			alertDialog2.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Write your code here to execute after dialog
							Raportdb.deleteTodo(przykladowe_dane2.get(
									info.position).getId());
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

		if (Raportdb != null)
			Raportdb.updateAllOffStatus();

		Raportdb.close();
		super.onDestroy();
	}

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
							+ " <BAND:2>2M" + " " 
							+ "<FREQ:"+new Integer(rap.getFrequency().toString().length()).toString() +">" + rap.getFrequency().toString()
							+ " <MODE:"+new Integer(rap.getMode().length()).toString()+">" + rap.getMode() 
							+ " <CALL:"+new Integer(rap.getCallsign().length()).toString()+">" + rap.getCallsign()
							+ " <RST_RCVD:"+new Integer(rap.getRstoString().length()).toString()+">" + rap.getRstoString()
							+ " <SRX:9>???L01KO11RD" 
							+ " <GRIDSQUARE:"+new Integer(zapisane_ustawienia.getString("qth", "logo").length()).toString()+"> "+ zapisane_ustawienia.getString("qth", "logo")
							+ " <RST_SENT:"+new Integer(rap.getRttoString().length()).toString()+">" + rap.getRttoString()
							+ " <STX:9>001KO11TH" 
							+ " <DISTANCE:2>22"
							+ " <OPERATOR:6>SQ5NWD"
							+ "<CONTEST_ID:5> 2014 <EOR>";
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
}
