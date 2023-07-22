package Ilya.Project.GamesProject.model.network.game;

import java.util.UUID;

import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.data.game.MoveRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GameService {
    @POST("games/{gameId}/board")
    Call<Game> makeMove(@Path("gameId") UUID gameId, @Query("username") String username, @Body MoveRequest moveRequest);

    @GET("games/{gameId}")
    Call<Game> getGameUpdates(@Path("gameId") UUID gameId, @Query("username") String username);
}