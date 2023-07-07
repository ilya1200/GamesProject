package Ilya.Project.GamesProject.utils;

public class UserValidator {

    public static boolean isUsernameValid(String username) {
        return username.matches("^[a-zA-Z][a-zA-Z0-9]{3,19}$");
        //return R.string.error_username_is_invalid;
    }

    public static boolean isPasswordValid(String password) {
        return password.matches("^[a-zA-Z][a-zA-Z0-9]{3}$");
        //return R.string.error_password_is_invalid;
    }
}
