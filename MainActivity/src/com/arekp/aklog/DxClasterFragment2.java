package com.arekp.aklog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.arekp.aklog.database.RaportDbAdapter;
import com.arekp.aklog.web.QrzClient;
import com.arekp.aklog.web.WebAdapter;
import com.arekp.aklog.web.WebAsync;
import com.arekp.aklog.web.WebBean;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class DxClasterFragment2 extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	private static final String DEBUG_TAG = "DxClasterFragment2";

	private WebView web;

	private ProgressBar progres;

	private Spinner spin;

	private ListView listView1;

	private WebAdapter adapter;

	private Document doc = null;

	private List<WebBean> WebBean_data1 = null;

	SharedPreferences zapisane_ustawienia;

	private WebBean bean;

	private View v;

	static HashMap<String, String> codeHash = new HashMap<String, String>();

	public static void init() {
		codeHash.put("Wybierz Czestotliwość", "http://dxcluster.sdr-radio.com/top_250_ALL.html");
		codeHash.put("ALL", "http://dxcluster.sdr-radio.com/top_250_ALL.html");
		codeHash.put("HF", "http://dxcluster.sdr-radio.com/top_250_HF.html");
		codeHash.put("137kHz", "http://dxcluster.sdr-radio.com/top_250_137_kHz.html");
		codeHash.put("1.8MHz", "http://dxcluster.sdr-radio.com/top_250_1.8_MHz.html");
		codeHash.put("3.5MHz", "http://dxcluster.sdr-radio.com/top_250_3.5_MHz.html");
		codeHash.put("5MHz", "http://dxcluster.sdr-radio.com/top_250_5_MHz.html");
		codeHash.put("7MHz", "http://dxcluster.sdr-radio.com/top_250_7_MHz.html");
		codeHash.put("10MHz", "http://dxcluster.sdr-radio.com/top_250_10_MHz.html");
		codeHash.put("14MHz", "http://dxcluster.sdr-radio.com/top_250_14_MHz.html");
		codeHash.put("18MHz", "http://dxcluster.sdr-radio.com/top_250_18_MHz.html");
		codeHash.put("21MHz", "http://dxcluster.sdr-radio.com/top_250_21_MHz.html");
		codeHash.put("24MHz", "http://dxcluster.sdr-radio.com/top_250_24_MHz.html");
		codeHash.put("28MHz", "http://dxcluster.sdr-radio.com/top_250_28_MHz.html");
		codeHash.put("50MHz", "http://dxcluster.sdr-radio.com/top_250_50_MHz.html");
		codeHash.put("70MHz", "http://dxcluster.sdr-radio.com/top_250_70_MHz.html");
		codeHash.put("144MHz", "http://dxcluster.sdr-radio.com/top_250_144_MHz.html");
		codeHash.put("220MHz", "http://dxcluster.sdr-radio.com/top_250_220_MHz.html");
		codeHash.put("430MHz", "http://dxcluster.sdr-radio.com/top_250_430_MHz.html");
	}

	public DxClasterFragment2() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		init();
		v = inflater.inflate(R.layout.fragment_dx_claster_dummy2, null);
		spin = (Spinner) v.findViewById(R.id.web_spinner);

		zapisane_ustawienia = PreferenceManager.getDefaultSharedPreferences(v.getContext());

		progres = (ProgressBar) v.findViewById(R.id.progressBar1);
		progres.setVisibility(View.GONE);

		listView1 = (ListView) v.findViewById(R.id.listViewWeb);

		spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			// **
			// Called when a new item is selected (in the Spinner)
			// *
			// WebBean_data = (List<WebBean>) listView1.getAdapter();

			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				// progres.setVisibility(View.VISIBLE);
				// An spinnerItem was selected. You can retrieve the selected item using
				// parent.getItemAtPosition(pos)
				Log.d(DEBUG_TAG, "Wybrana pozycja listy " + new Integer(pos).toString());
 
				if (pos != 0) {
		 
					new WebAsync(v.getContext(), listView1, progres).execute(codeHash.get(spin.getSelectedItem()
							.toString()));
				 
				}
			 
			}

			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing, just another required interface callback
			}

		});
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

		 
				bean = (WebBean) listView1.getItemAtPosition(pos);
	  
				Log.d(DEBUG_TAG, "mamy beana " + bean.getName());
				add(bean);
			}
		});

		// Podpinamy menu pod liste
		registerForContextMenu(listView1);

		return v;
	};

	@Override
	public void onCreateContextMenu(ContextMenu menuWeb, View v, ContextMenuInfo menuInfoWeb) {
		MenuInflater inflater = getActivity().getMenuInflater();

		inflater.inflate(R.menu.listraport_context_web, menuWeb);

		AdapterContextMenuInfo info1 = (AdapterContextMenuInfo) menuInfoWeb;
		WebBean bean = (WebBean) listView1.getItemAtPosition(info1.position);
		Log.e(DEBUG_TAG, "Wybrana nazwa z web " + bean.getName());
		menuWeb.setHeaderTitle(bean.getName());
  
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		// fragment kodu dzieki ktoremu sprawdzam dla ktorej listy jest menu
		Log.e(DEBUG_TAG, "onContextItemSelected");
		final AdapterView.AdapterContextMenuInfo info1 = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		if (info1.targetView.getParent() != getView().findViewById(R.id.listViewWeb))
			return super.onContextItemSelected(item);

		Log.e(DEBUG_TAG, "Wybrana z menu " + item.getItemId());
	 

		switch (item.getItemId()) {

		case R.id.menuusun1:
			bean = (WebBean) listView1.getItemAtPosition(info1.position);
			add(bean);

			Log.e(DEBUG_TAG, "w pierwszym kroku");
			break;
		case R.id.menuqrz1:
			bean = (WebBean) listView1.getItemAtPosition(info1.position);
			String a = bean.getName().replaceAll("\\s+", "");
			Log.e(DEBUG_TAG, "Wybrana nazwa z web w qrz " + a);

			if (zapisane_ustawienia.getBoolean("qrzsynch", false)) {
				new QrzClient(v.getContext()).execute(zapisane_ustawienia.getString("qrzLogin", null),
						zapisane_ustawienia.getString("qrzPasswd", null), a);
			}
			else {
				String url = "http://www.qrz.com/db/" + a;
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
			}
			break;
		// case R.id.menuusun1:
		/*
		 * AlertDialog.Builder alertDialog2 = new AlertDialog.Builder( v.getContext()); // Setting Dialog Title
		 * alertDialog2.setTitle("Potwierdz usuniecie..."); // Setting Dialog Message alertDialog2
		 * .setMessage("Na pewno chcesz usunac ten wpis " + przykladowe_dane2.get(info.position) .getCallsign() + " " +
		 * przykladowe_dane2.get(info.position) .getFrequency() + "?"); alertDialog2.setPositiveButton("YES", new
		 * DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) { // Write your
		 * code here to execute after dialog Raportdb.open(); Raportdb.deleteTodo(przykladowe_dane2.get(
		 * info.position).getId()); Raportdb.close(); refreshList(null); } }); // Setting Negative "NO" Btn
		 * alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int which) { // Write your code here to execute after dialog dialog.cancel();
		 * } }); // Showing Alert Dialog alertDialog2.show();
		 */

		// break;

		default:
			break;
		}
		return true;
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { getMenuInflater().inflate(R.menu.web_view, menu);
	 * return true; }
	 */

	public void setValue(int progress) {
		this.progres.setProgress(progress);
	}

	public void add(WebBean rap) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (zapisane_ustawienia.getBoolean("utm_checkbox", false)) {
			sdf.setTimeZone(TimeZone.getTimeZone("Zulu"));
		}

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

		band.setText(rap.getFreq().replaceAll("\\s+", ""));
		callSign.setText(rap.getName().replaceAll("\\s+", ""));
		rstR.setText("");
		rstS.setText("");
 
		textCzasDodaj.setText("Data bedzie godzina zapisu");
 
		note.setText("");
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (band.getText().toString().equals("")) {
					Log.e("Dodawanie1", "band" + band.getText().toString());
					Toast.makeText(v.getContext(), "Frequency nie może być puste", Toast.LENGTH_SHORT).show();
				}
				else if (callSign.getText().toString().isEmpty()) {
					Log.e("Dodawanie2", "call" + callSign.getText().toString());
					Toast.makeText(v.getContext(), "Callsign nie może być puste", Toast.LENGTH_SHORT).show();
				}
				else if (rstR.getText().toString().isEmpty()) {
					Log.e("Dodawanie2", "call" + callSign.getText().toString());
					Toast.makeText(v.getContext(), "rstt nie może być puste", Toast.LENGTH_SHORT).show();
				}
				else if (rstS.getText().toString().isEmpty()) {
					Log.e("Dodawanie2", "call" + callSign.getText().toString());
					Toast.makeText(v.getContext(), "rstS nie może być puste", Toast.LENGTH_SHORT).show();
				}
				else {
					String currentDateandTime = sdf.format(new Date());

					RaportBean rap = new RaportBean(0, band.getText().toString(), mode.getSelectedItem().toString(),
							currentDateandTime, callSign.getText().toString(), rstS.getText().toString(), rstR
									.getText().toString(), note.getText().toString());
					RaportDbAdapter Raportdb = new RaportDbAdapter(v.getContext());
					Raportdb.open();
					Raportdb.insertRaport(rap);
					Raportdb.close();

					/*
					 * RaportBean rap = new RaportBean(id_tmp,band.getText().toString(),
					 * mode.getSelectedItem().toString(), textCzasDodaj.getText().toString(),
					 * callSign.getText().toString(), rstS.getText().toString(), rstR.getText().toString(),
					 * note.getText().toString()); Raportdb.open(); Raportdb.updateRaport(rap); Raportdb.close();
					 * refreshList(null);
					 */
					dialog.cancel();
				}
			}
		});

		dialog.show();
	}
}
