package Ilya.Project.GamesProject;

import android.app.Application;

import Ilya.Project.GamesProject.utils.providers.ContextProvider;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextProvider.setApplicationContext(this);
    }
}
