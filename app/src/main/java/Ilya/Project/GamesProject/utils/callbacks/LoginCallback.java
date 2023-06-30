package Ilya.Project.GamesProject.utils.callbacks;

public interface LoginCallback {
    void onLoginSuccess();

    void onLoginFailure(String errorMessage);
}
