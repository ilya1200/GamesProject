package Ilya.Project.GamesProject;

import android.app.Application;

import com.airbnb.lottie.BuildConfig;

import Ilya.Project.GamesProject.utils.providers.ContextProvider;
import timber.log.Timber;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
        ContextProvider.setApplicationContext(getApplicationContext());
    }
}
