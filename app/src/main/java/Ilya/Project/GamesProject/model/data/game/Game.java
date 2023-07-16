package Ilya.Project.GamesProject.model.data.game;

import java.util.UUID;

public class Game {
    private UUID id;
    private String type;

    private GameStatus gameStatus;
    private String userFirstName;
    private String userSecondName;
    private long creationDate;

    private Move lastMove;


    public UUID getId() {
        return id;
    }

    public String getType() {return type;}

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserSecondName() {
        return userSecondName;
    }

    public void setUserSecondName(String userSecondName) {
        this.userSecondName = userSecondName;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public long getCreationDate() {
        return creationDate;
    }
}
