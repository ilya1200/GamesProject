package Ilya.Project.GamesProject.model.data.game;

import com.google.gson.annotations.SerializedName;

public class MoveRequest {

    @SerializedName("move")
    private String move;


    public MoveRequest(String move) {
        this.move = move;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
