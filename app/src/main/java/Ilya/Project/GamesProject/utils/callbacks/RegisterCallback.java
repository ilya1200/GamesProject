package Ilya.Project.GamesProject.utils.callbacks;

public interface RegisterCallback {
    void onRegisterSuccess();

    void onRegisterFailure(String errorMessage);
}
