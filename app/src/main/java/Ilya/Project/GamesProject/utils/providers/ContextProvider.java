package Ilya.Project.GamesProject.utils.providers;

import android.content.Context;


/**
 * The {@code ContextProvider} class provides access to the application's {@link Context}.
 * It allows setting and retrieving the application's {@link Context} to be accessed from any class.
 */
public class ContextProvider {

    private static Context applicationContext;

    /**
     * Sets the application's {@link Context}.
     * This method should be called to set the {@link Context} before accessing it from other classes.
     *
     * @param context the application's {@link Context} to be set
     */
    public static void setApplicationContext(Context context) {
        applicationContext = context.getApplicationContext();
    }

    /**
     * Retrieves the application's {@link Context}.
     * The {@link Context} should have been previously set using the {@link #setApplicationContext(Context)} method.
     *
     * @return the application's {@link Context}
     */
    public static Context getApplicationContext() {
        return applicationContext;
    }
}


