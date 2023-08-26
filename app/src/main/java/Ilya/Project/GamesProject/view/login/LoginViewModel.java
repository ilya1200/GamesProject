package Ilya.Project.GamesProject.view.login;

import android.os.Handler;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.model.data.user.User;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.UserValidator;
import Ilya.Project.GamesProject.utils.firebase.Firebase;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import timber.log.Timber;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<Boolean> usernameValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> passwordValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> shouldEnableLoginBtn = new MutableLiveData<>();
    public MutableLiveData<Boolean> loginLiveData = new MutableLiveData<>();
    private final Handler loginBtnTimeoutHandler = new Handler();
    private final long TIMEOUT_FOR_LOGIN_TO_COMPLETE_MILLIS = Firebase.getTimeoutForLoginToCompleteMillis();
    public MutableLiveData<Boolean> shouldProgressBarBeVisible = new MutableLiveData<>();
    private boolean isUsernameValid = false;
    private boolean isPasswordValid = false;

    public void onUsernameChanged(String username) {
        if (UserValidator.isUsernameValid(username)) {
            isUsernameValid = true;
            checkEnablingLoginButton();
        } else {
            isUsernameValid = false;
        }
        usernameValid.setValue(isUsernameValid);
    }

    public void onPasswordChanged(String password) {
        if (UserValidator.isPasswordValid(password)) {
            isPasswordValid = true;
            checkEnablingLoginButton();
        } else {
            isPasswordValid = false;
        }
        passwordValid.setValue(isPasswordValid);
    }

    private void checkEnablingLoginButton() {
        shouldEnableLoginBtn.setValue(isLoginFormValid());
    }

    private boolean isLoginFormValid() {
        return isUsernameValid && isPasswordValid;
    }


    public void handleLogin(User user) {
        if (user == null) {
            Timber.d("handleLogin: User object is null. Cannot proceed with login.");
            loginLiveData.setValue(false);
        } else {
            Timber.d("handleLogin: Initiating login process for user: %s", user.getUsername());
            shouldProgressBarBeVisible.setValue(true);
            shouldEnableLoginBtn.setValue(false);

            Timber.d("handleLogin: Disabling login button.");

            allowToEnableLoginButtonAfterTimeout();
            loginUser(user);
        }
    }

    private void allowToEnableLoginButtonAfterTimeout() {
        Timber.d("allowToEnableLoginButtonAfterTimeout: Setting up login button enable timeout.");

        loginBtnTimeoutHandler.postDelayed(() -> {
            shouldEnableLoginBtn.setValue(isLoginFormValid());
            shouldProgressBarBeVisible.setValue(false);
            Timber.d("allowToEnableLoginButtonAfterTimeout: Login button enabled after timeout.");
        }, TIMEOUT_FOR_LOGIN_TO_COMPLETE_MILLIS);
    }

    private void loginUser(User user) {
        UserRepository.login(user, new Result() {
            @Override
            public void onSuccess() {
                loginLiveData.setValue(true);
                UserRepository.setUser(user);
            }

            @Override
            public void onFailure(String errorMessage) {
                loginLiveData.setValue(false);
                UserRepository.clearUser();
                Toast.makeText(ContextProvider.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                shouldProgressBarBeVisible.setValue(false);
                shouldEnableLoginBtn.setValue(isLoginFormValid());
            }
        });
    }
}