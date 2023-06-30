package Ilya.Project.GamesProject.model.repository;

import Ilya.Project.GamesProject.model.data.User;
import Ilya.Project.GamesProject.model.local.UserSharedPrefs;
import Ilya.Project.GamesProject.utils.callbacks.LoginCallback;

public class UserRepository {

    public static User getUser(){
        if(!UserSharedPrefs.isUserExistsLocally()){
            return null;
        }
        String username = UserSharedPrefs.getUserNameLocally();
        String password = UserSharedPrefs.getUserPasswordLocally();
        return new User(username, password);
    }

    public static void login(LoginCallback loginCallback) {

    }
}
