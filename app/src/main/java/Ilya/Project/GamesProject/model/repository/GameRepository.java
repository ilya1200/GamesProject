package Ilya.Project.GamesProject.model.repository;

import java.util.UUID;

import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.network.game.GameAPI;
import Ilya.Project.GamesProject.utils.DataResult;

public class GameRepository {

    public static void getGameUpdates(UUID gameId, DataResult<Game> getGameUpdatesCallback) {
        GameAPI.getGameUpdates(gameId, getGameUpdatesCallback);
    }
}
