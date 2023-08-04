package Ilya.Project.GamesProject.view.TicTacToe;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.data.game.GameStatus;
import Ilya.Project.GamesProject.model.data.game.Player;
import Ilya.Project.GamesProject.model.repository.GameItemRepository;
import Ilya.Project.GamesProject.model.repository.GameRepository;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;

public class TicTacToeViewModel extends ViewModel {
    public MutableLiveData<Game> gameUpdates = new MutableLiveData<>();

    public MutableLiveData<boolean[][]> enabledCells = new MutableLiveData<>();

    public MutableLiveData<Player[][]> playerCells = new MutableLiveData<>();

    public MutableLiveData<String> showErrorMessageToastLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> leaveGameSuccess = new MutableLiveData<>();
    public MutableLiveData<String> endGameDialogMessage = new MutableLiveData<>();
    public static AtomicBoolean keepUpdating = new AtomicBoolean(false);

    private final boolean[][] _enabledCells = new boolean[3][3];

    private boolean isMyTurn = false;


    public void handleMakeMoveUpdates(UUID gameId, String move) {
        User user = UserRepository.getUser();
        Game game = gameUpdates.getValue();
        if (user==null || game == null || game.getGameStatus() != GameStatus.PLAYING){
            return;
        }

        Player player = game.getCurrentPlayer();
        String username = user.getUsername();
        isMyTurn = player==Player.FIRST && game.getUserFirstName().equals(username) || player==Player.SECOND && game.getUserSecondName().equals(username);
        if(!isMyTurn) {
            return;
        }

        GameRepository.makeMove(gameId, move, new DataResult<Game>() {

            @Override
            public void onSuccess(Game game) {
                gameUpdates.setValue(game);
                processGameUpdate(game);
            }

            @Override
            public void onFailure(String errorMessage) {
                showErrorMessageToastLiveData.setValue(errorMessage);
            }
        });
    }

    public void handleGetGameUpdates(UUID gameId) {
        GameRepository.getGameUpdates(gameId, new DataResult<Game>() {

            @Override
            public void onSuccess(Game game) {
                gameUpdates.setValue(game);
                processGameUpdate(game);
                isMyTurn = false;
            }

            @Override
            public void onFailure(String errorMessage) {
                showErrorMessageToastLiveData.setValue(errorMessage);
            }
        });
    }

    public void processGameUpdate(Game game){
        User user = UserRepository.getUser();
        if (user == null){
            return;
        }
        Player player = game.getCurrentPlayer();
        String username = user.getUsername();
        // Draw Board
        playerCells.setValue(game.getBoard());

        // Set buttons enable/disable
        Player[][] cells = game.getBoard();
        isMyTurn = player==Player.FIRST && game.getUserFirstName().equals(username) || player==Player.SECOND && game.getUserSecondName().equals(username);

        for(int row=0;row<cells.length;row++){
            for(int col=0;col<cells[row].length;col++){
                Player cell = cells[row][col];
                _enabledCells[row][col] = game.getGameStatus() == GameStatus.PLAYING && isMyTurn && cell == null;
            }
        }
        enabledCells.setValue(_enabledCells);

        GameStatus status = game.getGameStatus();

        if (isGameFinished(status)) {
            stopPollingGameUpdates();
            endGameDialogMessage.setValue(getEndGameMessage(status, game, username));
        }
    }

    private boolean isGameFinished(GameStatus status) {
        return status == GameStatus.PLAYER_1_WIN ||
                status == GameStatus.PLAYER_2_WIN ||
                status == GameStatus.PLAYER_1_LEFT ||
                status == GameStatus.PLAYER_2_LEFT ||
                status == GameStatus.DRAW;
    }

    private String getEndGameMessage(GameStatus status, Game game, String username) {
        Context context = ContextProvider.getApplicationContext();
        switch (status) {
            case PLAYER_1_WIN:
                return game.getUserFirstName().equals(username) ? context.getString(R.string.win_message) : context.getString(R.string.lose_message);
            case PLAYER_2_WIN:
                return game.getUserSecondName().equals(username) ? context.getString(R.string.win_message) : context.getString(R.string.lose_message);
            case PLAYER_1_LEFT:
            case PLAYER_2_LEFT:
                return context.getString(R.string.game_over_message);
            case DRAW:
                return context.getString(R.string.draw_message);
            default:
                return context.getString(R.string.unexpected_status_message, status);
        }
    }



    public void leaveGame(UUID gameId) {
        stopPollingGameUpdates();
        leaveGameSuccess.setValue(true);

        GameItemRepository.leaveGame(gameId);
    }

    public void startPollingGameUpdates(UUID gameId) {
        keepUpdating.set(true);

        new Thread(() -> {
            while(keepUpdating.get()){
                handleGetGameUpdates(gameId);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();
    }

    public void stopPollingGameUpdates() {
        keepUpdating.set(false);
    }
}
