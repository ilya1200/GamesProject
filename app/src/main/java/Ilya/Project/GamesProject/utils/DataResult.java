package Ilya.Project.GamesProject.utils;

public interface DataResult<T> {
    void onSuccess(T data);
    void onFailure(String message);
}
