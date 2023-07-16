package Ilya.Project.GamesProject.model.repository;

import java.util.List;
import java.util.UUID;

import Ilya.Project.GamesProject.model.data.game.GameItem;
import Ilya.Project.GamesProject.model.data.game.GameType;
import Ilya.Project.GamesProject.model.network.game.GameAPI;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;

public class GameRepository {

    public static void joinGame(UUID gameId, Result joinGameCallback){
        GameAPI.joinGame(gameId, joinGameCallback);
    }
    public static void getGameList(DataResult<List<GameItem>> getGameListCallback){
        GameAPI.getGameList(getGameListCallback);
    }

    public static void createGame(GameType gameType, DataResult<Game> createGameCallback) {
        GameAPI.createGame(gameType, createGameCallback);
    }
}
