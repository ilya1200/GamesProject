package Ilya.Project.GamesProject.model.network.game;

import android.util.Log;

import java.util.UUID;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.data.game.MoveRequest;
import Ilya.Project.GamesProject.model.network.retrofit.RetrofitInstance;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameAPI {

    public static void makeMove(UUID gameId, String move, DataResult<Game> makeMoveCallback){
        User user = UserRepository.getUser();
        if (user == null) {
            makeMoveCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.internal_error));
            return;
        }
        Call<Game> callable = RetrofitInstance.getGameService().makeMove(gameId, user.getUsername(), new MoveRequest(move));

        callable.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    assert response.body() != null;
                    Log.d("Game", response.body().toString());
                    makeMoveCallback.onSuccess(response.body());
                } else {
                    Log.d("Game", Integer.toString(code));
                    makeMoveCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.failed_to_make_a_move));
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.d("Game", t.toString());
                makeMoveCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.failed_to_make_a_move));
            }
        });
    }

    public static void getGameUpdates(UUID gameId, DataResult<Game> getGameUpdatesCallback) {
        User user = UserRepository.getUser();
        if (user == null) {
            getGameUpdatesCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.internal_error));
            return;
        }

        Call<Game> callable = RetrofitInstance.getGameService().getGameUpdates(gameId, user.getUsername());

        callable.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                int code = response.code();
                if (200 <= code && code < 300) {
                    assert response.body() != null;
                    Log.d("Game", response.body().toString());
                    getGameUpdatesCallback.onSuccess(response.body());
                } else {
                    Log.d("Game", Integer.toString(code));
                    getGameUpdatesCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.failed_to_get_game_updates));
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.d("Game", t.toString());
                getGameUpdatesCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.failed_to_get_game_updates));
            }
        });
    }
}