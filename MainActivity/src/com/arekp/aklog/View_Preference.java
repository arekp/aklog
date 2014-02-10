package com.arekp.aklog;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class View_Preference extends PreferenceActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);
    }
    
}
