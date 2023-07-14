package Ilya.Project.GamesProject.model.network.user;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.network.retrofit.RetrofitInstance;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.handlers.ErrorMessageHandler;
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
                    String message = ErrorMessageHandler.getErrorMessageFromResponse(response);
                    loginCallback.onFailure(message);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loginCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_message_1));
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
                    String message = ErrorMessageHandler.getErrorMessageFromResponse(response);
                    registerCallback.onFailure(message);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_message_2));
            }
        });
    }
}