package Ilya.Project.GamesProject.view.gamesList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.repository.GameRepository;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.model.rest.GameResponse;
import Ilya.Project.GamesProject.utils.Result;

public class GamesListViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<Boolean> joinGameSuccess = new MutableLiveData<>();
    public MutableLiveData<List<GameResponse>> gamesList = new MutableLiveData<>();

    public void displayUsername() {
        User user = UserRepository.getUser();
        username.setValue(user != null ? user.getUsername() : "");
    }

    public void handleGameClick(GameResponse gameResponse){
        GameRepository.joinGame(gameResponse, new Result(){

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

    public void handleGetGamesList() {
        //todo
    }
}
