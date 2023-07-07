package Ilya.Project.GamesProject.model.network.user;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.network.retrofit.RetrofitInstance;
import Ilya.Project.GamesProject.utils.callbacks.LoginCallback;
import Ilya.Project.GamesProject.utils.handlers.ErrorMessageHandler;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAPI {

    public static void login(User user, LoginCallback loginCallback) {
        Call<Void> callable = RetrofitInstance.getUserService().login(user.getUserName(), user.getPassword());

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    loginCallback.onLoginSuccess();
                } else {
                    String message = ErrorMessageHandler.getErrorMessageFromResponse(response);
                    loginCallback.onLoginFailure(message);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loginCallback.onLoginFailure(ContextProvider.getApplicationContext().getString(R.string.error_message_1));
            }
        });
    }
}