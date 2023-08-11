package Ilya.Project.GamesProject;

import android.app.Application;

import Ilya.Project.GamesProject.utils.firebase.Firebase;
import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import timber.log.Timber;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        ContextProvider.setApplicationContext(getApplicationContext());
        Firebase.setConfigSettingsAsync();
    }
}
