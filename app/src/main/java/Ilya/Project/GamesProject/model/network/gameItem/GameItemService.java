package Ilya.Project.GamesProject.model.network.gameItem;

import java.util.List;
import java.util.UUID;

import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.data.gameItem.GameItem;
import Ilya.Project.GamesProject.model.data.gameItem.GamePatchAction;
import Ilya.Project.GamesProject.model.data.gameItem.GameRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GameItemService {

    @GET("game_item")
    Call<List<GameItem>> getJoinableGames(@Query("username") String username);

    @PATCH("game_item/{gameId}")
    Call<Void> changeGame(@Path("gameId") UUID gameId, @Query("username") String username, @Query("password") String password, @Query("action") GamePatchAction action);

    @POST("game_item")
    Call<GameItem> createGame(@Body GameRequest gameRequest, @Query("username") String username);
}