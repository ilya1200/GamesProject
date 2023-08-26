package Ilya.Project.GamesProject.view.register;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.model.data.user.User;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.UserValidator;
import Ilya.Project.GamesProject.utils.firebase.Firebase;
import timber.log.Timber;

public class RegisterViewModel extends ViewModel {
    public MutableLiveData<Boolean> usernameValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> passwordValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> shouldEnableRegisterBtn = new MutableLiveData<>();
    public MutableLiveData<Boolean> registerLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showErrorMessageToastLiveData = new MutableLiveData<>();

    private final Handler registerBtnTimeoutHandler = new Handler();
    private final long TIMEOUT_FOR_REGISTER_TO_COMPLETE_MILLIS = Firebase.getTimeoutForRegisterToCompleteMillis();
    public MutableLiveData<Boolean> shouldProgressBarBeVisible = new MutableLiveData<>();

    private boolean isUsernameValid = false;
    private boolean isPasswordValid = false;

    public void onUsernameChanged(String username) {
        if (UserValidator.isUsernameValid(username)) {
            isUsernameValid = true;
            checkEnablingRegisterButton();
        } else {
            isUsernameValid = false;
        }
        usernameValid.setValue(isUsernameValid);
    }

    public void onPasswordChanged(String password) {
        if (UserValidator.isPasswordValid(password)) {
            isPasswordValid = true;
            checkEnablingRegisterButton();
        } else {
            isPasswordValid = false;
        }
        passwordValid.setValue(isPasswordValid);
    }

    private void checkEnablingRegisterButton() {
        shouldEnableRegisterBtn.setValue(isUsernameValid && isPasswordValid);
    }

    private boolean isRegisterFormValid() {
        return isUsernameValid && isPasswordValid;
    }

    public void handleRegister(User user) {
        if (user == null) {
            Timber.d("handleRegister: User object is null. Cannot proceed with register.");
            registerLiveData.setValue(false);
        } else {
            Timber.d("handleRegister: Initiating register process for user: %s", user.getUsername());
            shouldProgressBarBeVisible.setValue(true);
            shouldEnableRegisterBtn.setValue(false);

            Timber.d("handleRegister: Disabling register button.");

            allowToEnableRegisterButtonAfterTimeout();
            registerUser(user);
        }
    }

    private void allowToEnableRegisterButtonAfterTimeout() {
        Timber.d("allowToEnableRegisterButtonAfterTimeout: Setting up register button enable timeout.");

        registerBtnTimeoutHandler.postDelayed(() -> {
            shouldEnableRegisterBtn.setValue(isRegisterFormValid());
            shouldProgressBarBeVisible.setValue(false);
            Timber.d("allowToEnableRegisterButtonAfterTimeout: Register button enabled after timeout.");
        }, TIMEOUT_FOR_REGISTER_TO_COMPLETE_MILLIS);
    }

    private void registerUser(User user) {
        UserRepository.register(user, new Result() {
            @Override
            public void onSuccess() {
                registerLiveData.setValue(true);
                UserRepository.setUser(user);
            }

            @Override
            public void onFailure(String errorMessage) {
                registerLiveData.setValue(false);
                UserRepository.clearUser();
                showErrorMessageToastLiveData.setValue(errorMessage);
                shouldProgressBarBeVisible.setValue(false);
                shouldEnableRegisterBtn.setValue(isRegisterFormValid());
            }
        });
    }
}
