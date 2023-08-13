package Ilya.Project.GamesProject.view.TicTacToe;

import android.content.Context;
import android.os.Handler;
import android.widget.Button;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.data.game.GameStatus;
import Ilya.Project.GamesProject.model.data.game.Player;
import Ilya.Project.GamesProject.model.data.user.User;
import Ilya.Project.GamesProject.model.repository.GameItemRepository;
import Ilya.Project.GamesProject.model.repository.GameRepository;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.firebase.Firebase;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;

public class TicTacToeViewModel extends ViewModel {
    public static AtomicBoolean keepUpdatingFlag = new AtomicBoolean(false);
    private final Handler gameUpdatesHandler = new Handler();
    public MutableLiveData<Game> gameUpdatesLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showErrorMessageToastLiveData = new MutableLiveData<>();
    private final long gameUpdatesIntervalMillis = Firebase.getGameUpdatesIntervalMillis();

    public void getGameUpdates(UUID gameId) {
        GameRepository.getGameUpdates(gameId, new DataResult<Game>() {

            @Override
            public void onSuccess(Game game) {
                gameUpdatesLiveData.setValue(game);
            }

            @Override
            public void onFailure(String errorMessage) {
                showErrorMessageToastLiveData.setValue(errorMessage);
            }
        });
    }

    public void updateBoard(Game game, List<Button> buttons) {
        User user = UserRepository.getUser();
        if (user == null) {
            return;
        }
        Player player = game.getCurrentPlayer();
        String username = user.getUsername();
        Player[][] cells = game.getBoard();
        Player cell;
        boolean isMyTurn = isMyTurn(game, player, username);
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                int pos = row * cells.length + col;
                cell = cells[row][col];
                if (cell != null) {
                    buttons.get(pos).setBackgroundResource(cell == Player.FIRST ? R.drawable.tictactoe_x : R.drawable.tictactoe_o);
                }
                buttons.get(pos).setEnabled(game.getGameStatus() == GameStatus.PLAYING && isMyTurn && cell == null);
            }
        }
    }

    public void makeMove(UUID gameId, String move) {
        GameRepository.makeMove(gameId, move, new DataResult<Game>() {

            @Override
            public void onSuccess(Game game) {
            }

            @Override
            public void onFailure(String errorMessage) {
                showErrorMessageToastLiveData.setValue(errorMessage);
            }
        });
    }

    private boolean isMyTurn(Game game, Player player, String username) {
        return game.getGameStatus() == GameStatus.PLAYING && (player == Player.FIRST && game.getUserFirstName().equals(username) || player == Player.SECOND && game.getUserSecondName().equals(username));
    }

    public boolean isLegitGameFinish(GameStatus status) {
        return status == GameStatus.PLAYER_1_WIN || status == GameStatus.PLAYER_2_WIN || status == GameStatus.DRAW;
    }

    public boolean isTechnicalGameFinish(GameStatus status) {
        return  status == GameStatus.PLAYER_1_LEFT || status == GameStatus.PLAYER_2_LEFT;
    }


    public String getEndGameMessage(Game game) {
        Context context = ContextProvider.getApplicationContext();
        User user = UserRepository.getUser();
        if (user == null) {
            return context.getString(R.string.unexpected_status_message, game.getGameStatus());
        }
        String username = user.getUsername();
        switch (game.getGameStatus()) {
            case PLAYER_1_WIN:
                return game.hasUserFirst() && game.getUserFirstName().equals(username) ? context.getString(R.string.win_message) : context.getString(R.string.lose_message);
            case PLAYER_2_WIN:
                return game.hasUserSecond() && game.getUserSecondName().equals(username) ? context.getString(R.string.win_message) : context.getString(R.string.lose_message);
            case PLAYER_1_LEFT:
            case PLAYER_2_LEFT:
                return context.getString(R.string.game_over_message);
            case DRAW:
                return context.getString(R.string.draw_message);
            default:
                return context.getString(R.string.unexpected_status_message, game.getGameStatus());
        }
    }

    public void leaveGame(UUID gameId) {
        stopPollingGameUpdates();
        GameItemRepository.leaveGame(gameId);
    }

    public void startPollingGameUpdates(UUID gameId) {
        keepUpdatingFlag.set(true);
        pollGameUpdates(gameId);
    }

    private void pollGameUpdates(UUID gameId) {
        if (!keepUpdatingFlag.get()) {
            return;
        }
        getGameUpdates(gameId);
        gameUpdatesHandler.postDelayed(() -> pollGameUpdates(gameId), gameUpdatesIntervalMillis);
    }

    public void stopPollingGameUpdates() {
        keepUpdatingFlag.set(false);
        gameUpdatesHandler.removeCallbacksAndMessages(null);
    }
}