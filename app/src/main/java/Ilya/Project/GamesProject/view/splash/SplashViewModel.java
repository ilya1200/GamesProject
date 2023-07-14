package Ilya.Project.GamesProject.view.splash;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.Result;


public class SplashViewModel extends ViewModel {

    public MutableLiveData<Boolean> loginLiveData = new MutableLiveData<>();

    public void handleLogin() {
        User user = UserRepository.getUser();
        if (user == null) {
            loginLiveData.setValue(false);
        } else {
            loginUser(user);
        }
    }

    private void loginUser(User user) {
        UserRepository.login(user, new Result() {
            @Override
            public void onSuccess() {
                loginLiveData.setValue(true);
            }

            @Override
            public void onFailure(String errorMessage) {
                loginLiveData.setValue(false);
                UserRepository.clearUser();
            }
        });
    }
}