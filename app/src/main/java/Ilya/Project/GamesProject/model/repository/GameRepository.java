package Ilya.Project.GamesProject.model.repository;

import Ilya.Project.GamesProject.model.network.game.GameAPI;
import Ilya.Project.GamesProject.model.rest.GameResponse;
import Ilya.Project.GamesProject.utils.Result;

public class GameRepository {

    public static void joinGame(GameResponse gameResponse, Result joinGameCallback){
        GameAPI.joinGame(gameResponse, joinGameCallback);
    }
}
