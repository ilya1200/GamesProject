package Ilya.Project.GamesProject.model.network.user;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.user.User;
import Ilya.Project.GamesProject.model.data.user.UserScore;
import Ilya.Project.GamesProject.model.network.retrofit.RetrofitInstance;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class UserAPI {

    public static void login(User user, Result loginCallback) {
        if(user==null){
            Timber.e("Failed to login, user is null");
            return;
        }
        Call<Void> callable = RetrofitInstance.getUserService().login(user.getUsername(), user.getPassword());

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    Timber.d("Succeed to login with user %s, response code %s", user.getUsername(),code);
                    loginCallback.onSuccess();
                } else {
                    Timber.d("Failed to login with user %s, response code %s", user.getUsername(), code);
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
        if(user==null){
            Timber.e("Failed to register, user is null");
            return;
        }

        Call<Void> callable = RetrofitInstance.getUserService().signUp(user);

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    Timber.d("Succeed to register with user %s, response code %s", user.getUsername(),code);
                    registerCallback.onSuccess();
                } else {
                    Timber.e("Failed to register with user %s, response code %s", user.getUsername(),code);
                    registerCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_register_failed));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Timber.e(t.toString());
                registerCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_register_failed));
            }
        });
    }

    public static void getScore(String username, DataResult<UserScore> userScoreDataResult) {
        if (username == null || username.isEmpty()) {
            Timber.e("Failed to get user score, the user is null or empty");
            userScoreDataResult.onFailure(ContextProvider.getApplicationContext().getString(R.string.internal_error));
            return;
        }

        Call<UserScore> callable = RetrofitInstance.getUserService().getScore(username);

        callable.enqueue(new Callback<UserScore>() {
            @Override
            public void onResponse(Call<UserScore> call, Response<UserScore> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    assert response.body() != null;
                    Timber.d(response.body().toString());
                    userScoreDataResult.onSuccess(response.body());
                } else {
                    Timber.e(Integer.toString(code));
                    userScoreDataResult.onFailure(ContextProvider.getApplicationContext().getString(R.string.failed_to_get_user_score));
                }
            }

            @Override
            public void onFailure(Call<UserScore> call, Throwable t) {
                Timber.e(t.toString());
                userScoreDataResult.onFailure(ContextProvider.getApplicationContext().getString(R.string.failed_to_get_user_score));
            }
        });


    }
}