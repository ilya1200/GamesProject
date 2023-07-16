package Ilya.Project.GamesProject.model.network.user;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.network.retrofit.RetrofitInstance;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAPI {

    public static void login(User user, Result loginCallback) {
        Call<Void> callable = RetrofitInstance.getUserService().login(user.getUsername(), user.getPassword());

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    loginCallback.onSuccess();
                } else {
                    loginCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_login_failed));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loginCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_login_failed));
            }
        });
    }

    public static void register(User user, Result registerCallback) {
        Call<Void> callable = RetrofitInstance.getUserService().signUp(user);

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    registerCallback.onSuccess();
                } else {
                    registerCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_register_failed));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_register_failed));
            }
        });
    }
}