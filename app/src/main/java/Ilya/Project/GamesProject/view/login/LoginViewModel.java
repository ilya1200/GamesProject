package Ilya.Project.GamesProject.view.login;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.UserValidator;
import Ilya.Project.GamesProject.utils.callbacks.LoginCallback;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;

public class LoginViewModel extends ViewModel{

    public MutableLiveData<Boolean> usernameValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> passwordValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> shouldEnableLoginBtn = new MutableLiveData<>();
    public MutableLiveData<Boolean> loginLiveData = new MutableLiveData<>();
    private boolean isUsernameValid = false;
    private boolean isPasswordValid = false;

    public void onUserNameChanged(String username) {
        if(UserValidator.isUsernameValid(username)){
            isUsernameValid = true;
            checkEnablingLoginButton();
        }
        else{
            isUsernameValid = false;
        }
        usernameValid.setValue(isUsernameValid);
    }

    public void onPasswordChanged(String password) {
        if(UserValidator.isPasswordValid(password)){
            isPasswordValid = true;
            checkEnablingLoginButton();
        }
        else{
            isPasswordValid = false;
        }
        passwordValid.setValue(isPasswordValid);
    }

    private void checkEnablingLoginButton() {
        shouldEnableLoginBtn.setValue(isUsernameValid && isPasswordValid);
    }


    public void handleLogin(User user) {
        if (user == null) {
            loginLiveData.setValue(false);
        } else {
            loginUser(user);
        }
    }

    private void loginUser(User user) {
        UserRepository.login(user, new LoginCallback() {
            @Override
            public void onLoginSuccess() {
                loginLiveData.setValue(true);
                UserRepository.setUser(user);
            }

            @Override
            public void onLoginFailure(String errorMessage) {
                loginLiveData.setValue(false);
                UserRepository.clearUser();
                Toast.makeText(ContextProvider.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}