package Ilya.Project.GamesProject.view.TicTacToe;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.repository.GameItemRepository;
import Ilya.Project.GamesProject.model.repository.GameRepository;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;

public class TicTacToeViewModel extends ViewModel {
    public MutableLiveData<Game> gameUpdates = new MutableLiveData<>();
    public MutableLiveData<String> showErrorMessageToastLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> leaveGameSuccess = new MutableLiveData<>();

    public static AtomicBoolean keepUpdating = new AtomicBoolean(false);

    private boolean lockLeaveGameAction = false;


    public void handleGetGameUpdates(UUID gameId) {
        GameRepository.getGameUpdates(gameId, new DataResult<Game>() {

            @Override
            public void onSuccess(Game game) {
                gameUpdates.setValue(game);
            }

            @Override
            public void onFailure(String errorMessage) {
                showErrorMessageToastLiveData.setValue(errorMessage);
            }
        });
    }

    public void leaveGame(UUID gameId) {
        if(lockLeaveGameAction)
            return;
        lockLeaveGameAction = true;
        GameItemRepository.leaveGame(gameId, new Result() {
            @Override
            public void onSuccess() {
                stopPollingGameUpdates();
                leaveGameSuccess.setValue(true);
                lockLeaveGameAction = false;
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(ContextProvider.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                lockLeaveGameAction = false;
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
