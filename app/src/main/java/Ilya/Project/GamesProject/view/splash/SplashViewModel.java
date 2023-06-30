package Ilya.Project.GamesProject.view.splash;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.callbacks.LoginCallback;
import Ilya.Project.GamesProject.utils.managers.UserManager;

public class SplashViewModel extends ViewModel {

    public MutableLiveData<Boolean> loginLiveData;

    public void handleLogin() {
        User user = UserRepository.getUser();
        if (user == null) {
            loginLiveData.setValue(false);
        } else {
            loginUser();
        }
    }

    private void loginUser() {
        UserRepository.login(new LoginCallback() {
            @Override
            public void onLoginSuccess() {
                loginLiveData.setValue(true);
            }

            @Override
            public void onLoginFailure(String errorMessage) {
                loginLiveData.setValue(false);
            }
        });
    }
}