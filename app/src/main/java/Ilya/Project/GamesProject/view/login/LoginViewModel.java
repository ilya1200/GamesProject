package Ilya.Project.GamesProject.view.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.utils.UserValidator;

public class LoginViewModel extends ViewModel{

    public MutableLiveData<Boolean> usernameValid = new MutableLiveData<>();

    public MutableLiveData<Boolean> passwordValid = new MutableLiveData<>();

    public MutableLiveData<Boolean> shouldEnableLoginBtn = new MutableLiveData<>();
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



}


