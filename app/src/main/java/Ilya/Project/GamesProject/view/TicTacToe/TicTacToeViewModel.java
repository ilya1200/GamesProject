package Ilya.Project.GamesProject.view.TicTacToe;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.data.game.GameStatus;
import Ilya.Project.GamesProject.model.data.game.Player;
import Ilya.Project.GamesProject.model.repository.GameItemRepository;
import Ilya.Project.GamesProject.model.repository.GameRepository;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;

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

        switch (game.getGameStatus()){
            case WAITING_TO_START:
                break;
            case PLAYING:
                break;
            case PLAYER_1_WIN:
                endGameDialogMessage.setValue(game.getUserFirstName().equals(username)?"Win": "Lose");
                break;
            case PLAYER_2_WIN:
                endGameDialogMessage.setValue(game.getUserSecondName().equals(username)?"Win": "Lose");
                break;
            case PLAYER_1_LEFT:
            case PLAYER_2_LEFT:
                endGameDialogMessage.setValue("Game over! The other player left...");
                break;
            case DRAW:
                endGameDialogMessage.setValue("Draw");
                break;
        }
    }

    public void leaveGame(UUID gameId) {
        stopPollingGameUpdates();
        leaveGameSuccess.setValue(true);

        GameItemRepository.leaveGame(gameId, new Result() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(String message) {
                //Toast.makeText(ContextProvider.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                //todo make no callback leaveGame
            }
        });
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
