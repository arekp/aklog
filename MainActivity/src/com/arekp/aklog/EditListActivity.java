package com.arekp.aklog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.arekp.aklog.R.array;
import com.arekp.aklog.database.RaportDbAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditListActivity extends Activity {
	private EditText band;
	private EditText mode;
	private EditText start;
	private EditText callSign;
	private EditText rstR;
	private EditText rstS;
	private EditText note;
	private TextView textCzasDodaj;
	private RaportDbAdapter	Raportdb;
	private Long id;
	private Application fa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_list);
		
		band = (EditText) findViewById(R.id.editBand);
		callSign = (EditText) findViewById(R.id.editCallsign);
		rstR = (EditText) findViewById(R.id.editRstR);
		rstS = (EditText) findViewById(R.id.editRstS);
		note = (EditText) findViewById(R.id.editNote);
		Spinner mode = (Spinner) findViewById(R.id.mode1Spin);
		textCzasDodaj = (TextView) findViewById(R.id.textCzasDodaj);
		
		Intent intent = getIntent();
		 id = intent.getLongExtra("id",1);
		
		Raportdb = new RaportDbAdapter(this);
		Raportdb.open();
		RaportBean rap = Raportdb.getReport(id);
		band.setText(rap.getFrequency().toString());
		callSign.setText(rap.getCallsign());
		rstR.setText(new Integer(rap.getRt()).toString());
		rstS.setText(new Integer(rap.getRs()).toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		textCzasDodaj.setText(sdf.format(rap.getData()));
		mode.setSelection(getIndex(mode, rap.getMode()));
		note.setText(rap.getNote());
		
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
	
	public void updateDbKarta(final View view) {
		band = (EditText) findViewById(R.id.editBand);
		callSign = (EditText) findViewById(R.id.editCallsign);
		rstR = (EditText) findViewById(R.id.editRstR);
		rstS = (EditText) findViewById(R.id.editRstS);
		note = (EditText) findViewById(R.id.editNote);
		Spinner mode = (Spinner) findViewById(R.id.mode1Spin);
		textCzasDodaj = (TextView) findViewById(R.id.textCzasDodaj);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		sdf.setTimeZone(TimeZone.getTimeZone("Zulu"));
	

		if (band.getText().toString().equals("")) {
			Log.e("Dodawanie1", "band" + band.getText().toString());
			Toast.makeText(getBaseContext(), "Frequency nie może być puste",
					Toast.LENGTH_SHORT).show();
		} else if (callSign.getText().toString().isEmpty()) {
			Log.e("Dodawanie2", "call" + callSign.getText().toString());
			Toast.makeText(getBaseContext(), "Callsign nie może być puste",
					Toast.LENGTH_SHORT).show();
		} else if (rstR.getText().toString().isEmpty()) {
			Log.e("Dodawanie2", "call" + callSign.getText().toString());
			Toast.makeText(getBaseContext(), "rstt nie może być puste",
					Toast.LENGTH_SHORT).show();
		} else if (rstS.getText().toString().isEmpty()) {
			Log.e("Dodawanie2", "call" + callSign.getText().toString());
			Toast.makeText(getBaseContext(), "rstS nie może być puste",
					Toast.LENGTH_SHORT).show();
		}else {
		
		RaportBean rap = new RaportBean(id,band.getText().toString(), mode.getSelectedItem().toString(), textCzasDodaj.getText().toString(), callSign.getText().toString(), rstS.getText().toString(), rstR.getText().toString(), note.getText().toString());
	    RaportDbAdapter Raportdb = new RaportDbAdapter(view.getContext());
	    Raportdb.open();
	    Raportdb.updateRaport(rap);
	    Raportdb.close();

		Intent intent = new Intent (getApplication(),MainActivity.class);
		
          startActivity(intent);
		}
	}
	public void wroc(View view){
		
		Intent intent = new Intent (getApplication(),MainActivity.class);
	     startActivity(intent);
	}
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_list, menu);
		return true;
	}
*/
    @Override
	public void onDestroy() {
    	
        if(Raportdb != null)
        	Raportdb.updateAllOffStatus();
        
        	Raportdb.close();
        super.onDestroy();
    }
    
}
