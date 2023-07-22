package Ilya.Project.GamesProject.model.repository;

import java.util.List;
import java.util.UUID;

import Ilya.Project.GamesProject.model.data.gameItem.GameItem;
import Ilya.Project.GamesProject.model.data.gameItem.GameType;
import Ilya.Project.GamesProject.model.network.gameItem.GameItemAPI;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;

public class GameItemRepository {

    public static void joinGame(UUID gameId, Result joinGameCallback) {
        GameItemAPI.joinGame(gameId, joinGameCallback);
    }

    public static void getGameList(DataResult<List<GameItem>> getGameListCallback) {
        GameItemAPI.getGameList(getGameListCallback);
    }

    public static void createGame(GameType gameType, DataResult<UUID> createGameCallback) {
        GameItemAPI.createGame(gameType, createGameCallback);
    }
}
