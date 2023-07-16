package Ilya.Project.GamesProject.model.network.game;

import java.util.UUID;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.network.retrofit.RetrofitInstance;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameAPI {


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
                    getGameUpdatesCallback.onSuccess(response.body());
                } else {
                    getGameUpdatesCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.failed_to_get_game_updates));
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                getGameUpdatesCallback.onFailure(ContextProvider.getApplicationContext().getString(R.string.failed_to_get_game_updates));
            }
        });
    }
}