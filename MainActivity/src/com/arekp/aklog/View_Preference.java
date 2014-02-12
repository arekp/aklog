package com.arekp.aklog;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class View_Preference extends PreferenceActivity {
	
	private static final String PREFERENCES_NAME = "preferences";

	private EditTextPreference edittextPlik;
	private EditTextPreference edittextKatalog;
	private SharedPreferences preferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	//	preferences = getSharedPreferences(PREFERENCES_NAME,Activity.MODE_PRIVATE);

		edittextPlik = (EditTextPreference) findPreference("pref_plik");
		edittextKatalog = (EditTextPreference) findPreference("pref_katalog");
		initPreferences();
	}

	private void initPreferences() {
		String edittextPlikValue = preferences.getString("pref_plik", "");
		String edittextKatalogValue = preferences.getString("pref_katalog", "");
		edittextPlik.setText(edittextPlikValue);
		edittextKatalog.setText(edittextKatalogValue);
	}

	@Override
	public void onPause() {
		super.onPause();
		savePreferences();
	}

	private void savePreferences() {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("pref_plik", edittextPlik.getText());
		editor.putString("pref_katalog", edittextKatalog.getText());
		editor.commit();
	}

}
