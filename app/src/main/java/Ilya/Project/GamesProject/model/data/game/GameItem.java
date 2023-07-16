package Ilya.Project.GamesProject.model.data.game;

import java.util.UUID;

public class GameItem {
    private UUID id;
    private GameType type;
    private String userFirstName;

    public UUID getGameId() {
        return id;
    }

    public GameType getGameType() {return type;}

    public String getFirstUser() {
        return userFirstName;
    }
}