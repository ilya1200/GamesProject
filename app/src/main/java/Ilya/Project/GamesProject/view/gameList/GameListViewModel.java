package Ilya.Project.GamesProject.view.gameList;

import static Ilya.Project.GamesProject.utils.Constants.GAMES;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.repository.GameRepository;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.model.data.game.Game;
import Ilya.Project.GamesProject.utils.DataResult;
import Ilya.Project.GamesProject.utils.Result;

public class GameListViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<Boolean> joinGameSuccess = new MutableLiveData<>();
    public MutableLiveData<List<Game>> gameList = new MutableLiveData<>();
    public MutableLiveData<String> showErrorMessageToastLiveData = new MutableLiveData<>();


    public void displayUsername() {
        User user = UserRepository.getUser();
        username.setValue(user != null ? user.getUsername() : "");
    }

    public void handleGameClick(Game game){
        GameRepository.joinGame(game, new Result(){

            @Override
            public void onSuccess() {
                joinGameSuccess.setValue(true);
            }

            @Override
            public void onFailure(String message) {
                joinGameSuccess.setValue(false);
            }
        });
    }
    public void handleLogOut() {
        UserRepository.clearUser();
    }

    public void handleGetGameList() {
        Log.d(GAMES, "handleGetGameList was called");
        GameRepository.getGameList(new DataResult<List<Game>>() {
            @Override
            public void onSuccess(List<Game> gameList) {
                Log.d(GAMES, "handleGetGameList onSuccess: "+ gameList.size());
                GameListViewModel.this.gameList.setValue(gameList);
            }

            @Override
            public void onFailure(String errorMessage) {
                showErrorMessageToastLiveData.setValue(errorMessage);
            }
        });
    }
}
