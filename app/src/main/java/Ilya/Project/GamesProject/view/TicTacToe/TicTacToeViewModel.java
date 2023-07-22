package Ilya.Project.GamesProject.view.TicTacToe;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.UUID;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.model.repository.GameRepository;
import Ilya.Project.GamesProject.utils.DataResult;

public class TicTacToeViewModel extends ViewModel {
    public MutableLiveData<Game> gameUpdates = new MutableLiveData<>();
    public MutableLiveData<String> showErrorMessageToastLiveData = new MutableLiveData<>();


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
}
