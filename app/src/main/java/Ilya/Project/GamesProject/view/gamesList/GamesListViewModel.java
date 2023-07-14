package Ilya.Project.GamesProject.view.gamesList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.repository.UserRepository;

public class GamesListViewModel extends ViewModel {
    public MutableLiveData<String> username = new MutableLiveData<>();


    public void displayUserName(){
        User user = UserRepository.getUser();
        username.setValue(user!=null? user.getUserName(): "");
    }

    public void handleLogOut() {
        UserRepository.clearUser();
    }
}
