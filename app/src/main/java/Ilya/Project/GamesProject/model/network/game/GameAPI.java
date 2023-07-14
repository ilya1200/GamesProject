package Ilya.Project.GamesProject.model.network.game;

import java.util.UUID;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.network.retrofit.RetrofitInstance;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.model.rest.GameResponse;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.handlers.ErrorMessageHandler;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameAPI {

    public static void joinGame(GameResponse gameResponse, Result joinGameCallback) {
        UUID gameId = gameResponse.getId();
        User user = UserRepository.getUser();
        if(user == null){
            joinGameCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_message_3));
            return;
        }
        String username = user.getUsername();
        String password = user.getPassword();

        Call<Void> callable = RetrofitInstance.getGameService().changeGame(gameId,username,password,GamePatchAction.JOIN);

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    joinGameCallback.onSuccess();
                } else {
                    String message = ErrorMessageHandler.getErrorMessageFromResponse(response);
                    joinGameCallback.onFailure(message);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                joinGameCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_message_3));
            }
        });
    }
}