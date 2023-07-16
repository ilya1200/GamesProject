package Ilya.Project.GamesProject.model.data.gameItem;

import com.google.gson.annotations.SerializedName;

public class GameRequest {
    @SerializedName("gameType")
    private String gameType;

    public GameRequest(String gameType) {
        this.gameType = gameType;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
