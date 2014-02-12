package com.arekp.aklog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RaportFragment extends Fragment {
	private static TextView  tekst;
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	


	public RaportFragment() {
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
					
		final View v = inflater
				.inflate(R.layout.fragment_lista_dummy, null);
		
		tekst = (TextView) v.findViewById(R.id.textDane2);
		
    	final File file = new File(container.getContext().getExternalFilesDir(null), "dane.txt");
		StringBuilder text = new StringBuilder();

    	try {
    	    BufferedReader br = new BufferedReader(new FileReader(file));
    	    String line;

    	    while ((line = br.readLine()) != null) {
    	        text.append(line);
    	        text.append('\n');
    	    }
    	}
    	catch (IOException e) {
    	    //You'll need to add proper error handling here
    	}
    	tekst.setText(text);
		// TextView dummyTextView = (TextView)
		// v.findViewById(R.id.section_label);
		// dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
		return v;
	}
	
}
