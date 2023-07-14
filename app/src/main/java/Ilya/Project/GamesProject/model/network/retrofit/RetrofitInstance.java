package Ilya.Project.GamesProject.model.network.retrofit;

import Ilya.Project.GamesProject.model.network.game.GameService;
import Ilya.Project.GamesProject.model.network.user.UserService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = "http://192.168.1.22:8080/";
    private static Retrofit retrofit;

    public static UserService getUserService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(UserService.class);
    }
    public static GameService getGameService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(GameService.class);
    }
}