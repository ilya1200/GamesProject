package Ilya.Project.GamesProject.model.data.game;

public enum GameStatus {
    WAITING_TO_START,
    PLAYING,
    PLAYER_1_WIN,
    PLAYER_2_WIN,
    PLAYER_1_LEFT,
    PLAYER_2_LEFT,
    DRAW;

    public boolean isFinished() {
        return this==PLAYER_1_WIN || this==PLAYER_2_WIN || this == PLAYER_1_LEFT || this==PLAYER_2_LEFT || this == DRAW;
    }
}
