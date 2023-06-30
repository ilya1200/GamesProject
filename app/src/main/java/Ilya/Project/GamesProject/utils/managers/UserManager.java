package Ilya.Project.GamesProject.utils.managers;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import Ilya.Project.GamesProject.utils.callbacks.LoginCallback;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManager {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final SharedPreferences sharedPref = SharedPrefProvider.getSharedPref();


    public static void signUp(String userName, String password, final SignUpCallback callback) {
        Context context = ContextProvider.getApplicationContext();
        GameService gameService = GameServiceProvider.getGameService();
        Call<Void> callable = gameService.signUp(new UserRequest(userName, password));

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (200 <=code && code < 300) {
                    saveUserInSharedPrefs(context, userName, password);
                    callback.onSignUpSuccess();
                } else {
                    String message = ErrorMessageHandler.getErrorMessageFromResponse((GameApplication) context, response);
                    callback.onSignUpFailure(message);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onSignUpFailure(context.getString(R.string.error_login_failed));
            }
        });
    }

    public static void login(String userName, String password, final LoginCallback callback) {
        Context context = ContextProvider.getApplicationContext();
        GameService gameService = GameServiceProvider.getGameService();
        Call<Void> callable = gameService.login(userName, password);

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (200 <=code && code < 300) {
                    saveUserInSharedPrefs(context, userName, password);
                    callback.onLoginSuccess();
                } else {
                    String message = ErrorMessageHandler.getErrorMessageFromResponse((GameApplication) context, response);
                    callback.onLoginFailure(message);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onLoginFailure(context.getString(R.string.error_login_failed));
            }
        });
    }

    public static void logOut(){
        Context context = ContextProvider.getApplicationContext();
        saveUserInSharedPrefs(context, "", "");
    }

    private static void saveUserInSharedPrefs(Context context, String userName, String password) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USERNAME, userName);
        editor.putString(PASSWORD, password);
        editor.apply();
    }
}
