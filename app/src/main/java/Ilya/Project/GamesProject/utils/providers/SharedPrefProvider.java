package Ilya.Project.GamesProject.utils.providers;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import Ilya.Project.GamesProject.utils.providers.ContextProvider;

public class SharedPrefProvider {
    public static SharedPreferences getSharedPref() {
        Context context = ContextProvider.getApplicationContext();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}