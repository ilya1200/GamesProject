package Ilya.Project.GamesProject.model.data.game;

import java.util.UUID;

import Ilya.Project.GamesProject.model.data.gameItem.GameType;

public class Game {
    private UUID id;
    private GameType type;
    private GameStatus gameStatus;
    private String userFirstName;
    private String userSecondName;
    private Board board;


    public UUID getId() {
        return id;
    }

    public GameType getType() {return type;}

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

    public Board getBoard() { return board; }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", type=" + type +
                ", gameStatus=" + gameStatus +
                ", userFirstName='" + userFirstName + '\'' +
                ", userSecondName='" + userSecondName + '\'' +
                ", board=" + board +
                '}';
    }
}
