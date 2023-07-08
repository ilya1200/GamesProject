package Ilya.Project.GamesProject.utils;

public class UserValidator {

    public static boolean isUsernameValid(String username) {
        return username.matches("^[a-zA-Z][a-zA-Z0-9]{3,19}$");
    }

    public static boolean isPasswordValid(String password) {
        return password.matches("^[a-zA-Z][a-zA-Z0-9]{3}$");
    }
}
