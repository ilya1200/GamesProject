package Ilya.Project.GamesProject.utils.firebase;

import android.app.Activity;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import Ilya.Project.GamesProject.R;
import Ilya.Project.GamesProject.utils.Constants;
import Ilya.Project.GamesProject.utils.FetchAndActivateConfigsCallback;
import timber.log.Timber;

public class Firebase {

    public static void setConfigSettingsAsync() {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
    }

    public static void fetchAndActivateConfigs(Activity activity, FetchAndActivateConfigsCallback isFinishedCallback) {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                boolean updated = task.getResult();
                Timber.d("Config params updated: %s", updated);
            } else {
                Timber.e("Config params did not updated");
            }
            isFinishedCallback.onFinished();
        });
    }

    public static long getGameUpdatesIntervalMillis() {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        return mFirebaseRemoteConfig.getLong(Constants.GAME_UPDATES_INTERVAL_MILLIS);
    }

    public static long getTimeoutForLoginToCompleteMillis() {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        return mFirebaseRemoteConfig.getLong(Constants.TIMEOUT_FOR_LOGIN_TO_COMPLETE_MILLIS);
    }

    public static long getTimeoutForRegisterToCompleteMillis() {
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        return mFirebaseRemoteConfig.getLong(Constants.TIMEOUT_FOR_REGISTER_TO_COMPLETE_MILLIS);
    }
}