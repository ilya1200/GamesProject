package Ilya.Project.GamesProject.model.repository;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.local.UserSharedPrefs;
import Ilya.Project.GamesProject.model.network.user.UserAPI;
import Ilya.Project.GamesProject.utils.Result;

public class UserRepository {

    public static User getUser() {
        if (!UserSharedPrefs.isUserExistsLocally()) {
            return null;
        }
        String username = UserSharedPrefs.getUsernameLocally();
        String password = UserSharedPrefs.getUserPasswordLocally();
        return new User(username, password);
    }

    public static void setUser(User user) {
        if (user == null) {
            return;
        }
        UserSharedPrefs.setUsernameLocally(user.getUsername());
        UserSharedPrefs.setUserPasswordLocally(user.getPassword());
    }

    public static void clearUser() {
        UserSharedPrefs.clearUsernameLocally();
        UserSharedPrefs.clearUserPasswordLocally();
    }

    public static void login(User user, Result loginCallback) {
        UserAPI.login(user, loginCallback);
    }

    public static void register(User user, Result registerCallback) {
        UserAPI.register(user, registerCallback);
    }
}