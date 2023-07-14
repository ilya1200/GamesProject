package Ilya.Project.GamesProject.view.register;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.repository.UserRepository;
import Ilya.Project.GamesProject.utils.UserValidator;
import Ilya.Project.GamesProject.utils.callbacks.RegisterCallback;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;

public class RegisterViewModel extends ViewModel {
    public MutableLiveData<Boolean> usernameValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> passwordValid = new MutableLiveData<>();
    public MutableLiveData<Boolean> shouldEnableRegisterBtn = new MutableLiveData<>();

    public MutableLiveData<Boolean> registerLiveData = new MutableLiveData<>();
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
        UserRepository.register(user, new RegisterCallback() {
            @Override
            public void onRegisterSuccess() {
                registerLiveData.setValue(true);
                UserRepository.setUser(user);
            }

            @Override
            public void onRegisterFailure(String errorMessage) {
                registerLiveData.setValue(false);
                UserRepository.clearUser();
                Toast.makeText(ContextProvider.getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
