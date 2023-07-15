package Ilya.Project.GamesProject.model.network.game;

import java.util.List;
import java.util.UUID;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.game.GamePatchAction;
import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.network.retrofit.RetrofitInstance;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.handlers.ErrorMessageHandler;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameAPI {

    public static void joinGame(Game game, Result joinGameCallback) {
        UUID gameId = game.getId();
        User user = UserRepository.getUser();
        if (user == null) {
            joinGameCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_message_3));
            return;
        }
        String username = user.getUsername();
        String password = user.getPassword();

        Call<Void> callable = RetrofitInstance.getGameService().changeGame(gameId, username, password, GamePatchAction.JOIN);

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

    public static void getGameList(DataResult<List<Game>> getGameListCallback) {
        User user = UserRepository.getUser();
        if (user == null) {
            getGameListCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_message_3));
            return;
        }
        String username = user.getUsername();
        Call<List<Game>> callable = RetrofitInstance.getGameService().getJoinableGames(username);
        callable.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    getGameListCallback.onSuccess(response.body());
                } else {
                    String message = ErrorMessageHandler.getErrorMessageFromResponse(response);
                    getGameListCallback.onFailure(message);
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                getGameListCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_message_3));
            }
        });
    }
}