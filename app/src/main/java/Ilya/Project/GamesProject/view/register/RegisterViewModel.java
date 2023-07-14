package Ilya.Project.GamesProject.view.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.Result;
import Ilya.Project.GamesProject.utils.UserValidator;

public class RegisterViewModel extends ViewModel {
    public MutableLiveData<Boolean> usernameValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> passwordValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> shouldEnableRegisterBtn = new MutableLiveData<>();

    public MutableLiveData<Boolean> registerLiveData = new MutableLiveData<>();
    public MutableLiveData<String> showErrorMessageToastLiveData = new MutableLiveData<>();
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

    public void handleRegister(User user) {
        if (user == null) {
            registerLiveData.setValue(false);
        } else {
            registerUser(user);
        }
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
            }
        });
    }
}
