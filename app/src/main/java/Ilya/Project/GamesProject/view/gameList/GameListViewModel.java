package Ilya.Project.GamesProject.view.gameList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.UUID;

import Ilya.Project.GamesProject.model.data.gameItem.GameItem;
import Ilya.Project.GamesProject.model.data.gameItem.GameType;
import Ilya.Project.GamesProject.model.data.user.User;
import Ilya.Project.GamesProject.model.data.user.UserScore;
import Ilya.Project.GamesProject.model.repository.GameItemRepository;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;
import timber.log.Timber;

public class GameListViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<UUID> joinGameSuccess = new MutableLiveData<>();
    public MutableLiveData<List<GameItem>> gameList = new MutableLiveData<>();

    public MutableLiveData<UUID> createGameSuccess = new MutableLiveData<>();

    public MutableLiveData<String> showErrorMessageToastLiveData = new MutableLiveData<>();
    public MutableLiveData<UserScore> userScoreMutableLiveData = new MutableLiveData<>();


    public void displayUsername() {
        User user = UserRepository.getUser();
        username.setValue(user != null ? user.getUsername() : "");
    }

    public void handleGameClick(UUID gameId){
        GameItemRepository.joinGame(gameId, new Result(){

            @Override
            public void onSuccess() {
                joinGameSuccess.setValue(gameId);
            }

            @Override
            public void onFailure(String message) {
                joinGameSuccess.setValue(null);
            }
        });
    }
    public void handleLogOut() {
        UserRepository.clearUser();
    }

    public void handleGetGameList() {
        Timber.d("handleGetGameList was called");
        GameItemRepository.getGameList(new DataResult<List<GameItem>>() {
            @Override
            public void onSuccess(List<GameItem> gameList) {
                Timber.d("handleGetGameList onSuccess: %s", gameList.size());
                GameListViewModel.this.gameList.setValue(gameList);
            }

            @Override
            public void onFailure(String errorMessage) {
                showErrorMessageToastLiveData.setValue(errorMessage);
            }
        });
    }

    public void handleCreateGame() {
        GameItemRepository.createGame(GameType.TIK_TAC_TOE, new DataResult<UUID>(){

            @Override
            public void onSuccess(UUID gameId) {
                createGameSuccess.setValue(gameId);
            }

            @Override
            public void onFailure(String errorMessage) {
                createGameSuccess.setValue(null);
                showErrorMessageToastLiveData.setValue(errorMessage);
            }
        });
    }

    public void handleGetUserScore() {
        User user = UserRepository.getUser();
        if (user == null) {
            return;
        }

        UserRepository.getScore(user.getUsername(), new DataResult<UserScore>() {
            @Override
            public void onSuccess(UserScore data) {
                userScoreMutableLiveData.setValue(data);
            }

            @Override
            public void onFailure(String message) {
            }
        });
    }
}
