package Ilya.Project.GamesProject.model.network.gameItem;

import java.util.List;
import java.util.UUID;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.data.gameItem.GameItem;
import Ilya.Project.GamesProject.model.data.gameItem.GamePatchAction;
import Ilya.Project.GamesProject.model.data.gameItem.GameRequest;
import Ilya.Project.GamesProject.model.data.gameItem.GameType;
import Ilya.Project.GamesProject.model.network.retrofit.RetrofitInstance;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class GameItemAPI {

    public static void leaveGame(UUID gameId) {
        User user = UserRepository.getUser();
        if (user == null) {
            Timber.e("Failed to leave game "+gameId+ " user is null");
            return;
        }
        String username = user.getUsername();
        String password = user.getPassword();

        Call<Void> callable = RetrofitInstance.getGameItemService().changeGame(gameId, username, password, GamePatchAction.LEAVE);

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call call, Response response) {
                Timber.d("Leave game response code "+ response.code());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Timber.e(t.toString());
            }
        });
    }

    public static void joinGame(UUID gameId, Result joinGameCallback) {
        User user = UserRepository.getUser();
        if (user == null) {
            Timber.e("Failed to join game "+gameId+ " user is null");
            joinGameCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_failed_to_join_game));
            return;
        }
        String username = user.getUsername();
        String password = user.getPassword();

        Call<Void> callable = RetrofitInstance.getGameItemService().changeGame(gameId, username, password, GamePatchAction.JOIN);

        callable.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    Timber.d("joinGame success game response code");
                    joinGameCallback.onSuccess();
                } else {
                    Timber.e("Failed to join game "+gameId+ " response code "+code);
                    joinGameCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_failed_to_join_game));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Timber.e(t.toString());
                joinGameCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_failed_to_join_game));
            }
        });
    }

    public static void getGameList(DataResult<List<GameItem>> getGameListCallback) {
        User user = UserRepository.getUser();
        if (user == null) {
            Timber.e("Failed to get list of games, user is null");

            getGameListCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_failed_to_get_list_of_game));
            return;
        }
        String username = user.getUsername();
        Call<List<GameItem>> callable = RetrofitInstance.getGameItemService().getJoinableGames(username);
        callable.enqueue(new Callback<List<GameItem>>() {
            @Override
            public void onResponse(Call<List<GameItem>> call, Response<List<GameItem>> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    Timber.d("getGameList success");
                    getGameListCallback.onSuccess(response.body());
                } else {
                    Timber.e("Failed to get list of games");
                    getGameListCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_failed_to_get_list_of_game));
                }
            }

            @Override
            public void onFailure(Call<List<GameItem>> call, Throwable t) {
                Timber.e(t.toString());
                getGameListCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_failed_to_get_list_of_game));
            }
        });
    }

    public static void createGame(GameType gameType, DataResult<UUID> createGameCallback) {
        User user = UserRepository.getUser();
        if (user == null) {
            createGameCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_failed_to_create_game));
            return;
        }
        String username = user.getUsername();
        GameRequest gameRequest = new GameRequest(gameType.toString());
        Call<GameItem> callable = RetrofitInstance.getGameItemService().createGame(gameRequest, username);

        callable.enqueue(new Callback<GameItem>() {
            @Override
            public void onResponse(Call<GameItem> call, Response<GameItem> response) {
                int code = response.code();
                GameItem game = response.body();
                if (200 <= code && code < 300 && game != null) {
                    Timber.d("created a game with id "+game.getGameId());
                    createGameCallback.onSuccess(game.getGameId());
                } else {
                    createGameCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_failed_to_create_game));
                }
            }

            @Override
            public void onFailure(Call<GameItem> call, Throwable t) {
                Timber.e(t.toString());
                createGameCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.error_failed_to_create_game));
            }
        });
    }
}