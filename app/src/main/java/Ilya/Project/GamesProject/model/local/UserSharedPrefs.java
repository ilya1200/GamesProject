package Ilya.Project.GamesProject.model.local;

import android.content.SharedPreferences;

import Ilya.Project.GamesProject.utils.managers.SharedPrefProvider;

public class UserSharedPrefs {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static SharedPreferences sharedPrefs = SharedPrefProvider.getSharedPref();

    public static boolean isUserExistsLocally(){
            String username = sharedPrefs.getString(USERNAME, "");
            String password = sharedPrefs.getString(PASSWORD, "");
            return !(username.isEmpty() || password.isEmpty());
    }

    public static String getUserNameLocally(){
        return sharedPrefs.getString(USERNAME, "");
    }

    public static String getUserPasswordLocally(){
        return sharedPrefs.getString(PASSWORD, "");
    }
}
