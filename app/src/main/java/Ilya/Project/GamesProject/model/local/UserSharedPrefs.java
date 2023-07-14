package Ilya.Project.GamesProject.model.local;

import android.content.SharedPreferences;

import Ilya.Project.GamesProject.utils.providers.SharedPrefProvider;

public class UserSharedPrefs {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final SharedPreferences sharedPrefs = SharedPrefProvider.getSharedPref();

    public static boolean isUserExistsLocally() {
        String username = sharedPrefs.getString(USERNAME, "");
        String password = sharedPrefs.getString(PASSWORD, "");
        return !(username.isEmpty() || password.isEmpty());
    }

    public static String getUsernameLocally() {
        return sharedPrefs.getString(USERNAME, "");
    }

    public static void setUsernameLocally(String username) {
        if (username == null || username.isEmpty()) {
            return;
        }
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public static String getUserPasswordLocally() {
        return sharedPrefs.getString(PASSWORD, "");
    }

    public static void setUserPasswordLocally(String password) {
        if (password == null || password.isEmpty()) {
            return;
        }
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public static void clearUsernameLocally() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(USERNAME, "");
        editor.apply();
    }

    public static void clearUserPasswordLocally() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PASSWORD, "");
        editor.apply();
    }
}