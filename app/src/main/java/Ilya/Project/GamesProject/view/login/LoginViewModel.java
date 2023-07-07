package Ilya.Project.GamesProject.view.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.utils.UserValidator;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;

public class LoginViewModel extends ViewModel{

    public MutableLiveData<Boolean> usernameValid = new MutableLiveData<>();
    public MutableLiveData<Integer> passwordErrorResId = new MutableLiveData<>();

    public MutableLiveData<Boolean> shouldEnableLoginBtn = new MutableLiveData<>();
    private boolean isUsernameValid = false;
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

    public void onUserPasswordChanged(String password) {
        usernameErrorResId.setValue(validatePassword(password));
        checkLoginButton();
    }

    private void checkEnablingLoginButton() {
        shouldEnableLoginBtn.setValue(isUsernameValid && isUserPasswordValid);
    }



}


