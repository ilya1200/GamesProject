package Ilya.Project.GamesProject.model.repository;

import java.util.List;

import Ilya.Project.GamesProject.model.network.game.GameAPI;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;

public class GameRepository {

    public static void joinGame(Game game, Result joinGameCallback){
        GameAPI.joinGame(game, joinGameCallback);
    }
    public static void getGameList(DataResult<List<Game>> getGameListCallback){
        GameAPI.getGameList(getGameListCallback);
    }
}
