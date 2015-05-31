package boyd.bueno.taghunt.Fragments;

import android.os.Bundle;

import boyd.bueno.taghunt.R;

public class PreferenceFragment extends android.preference.PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_notification);
    }

}
