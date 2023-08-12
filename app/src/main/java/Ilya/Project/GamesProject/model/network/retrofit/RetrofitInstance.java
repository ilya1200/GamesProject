package Ilya.Project.GamesProject.model.network.retrofit;

import Ilya.Project.GamesProject.model.network.game.GameService;
import Ilya.Project.GamesProject.model.network.gameItem.GameItemService;
import Ilya.Project.GamesProject.model.network.user.UserService;
import Ilya.Project.GamesProject.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = Constants.BASE_URL;
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

    public static GameItemService getGameItemService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(GameItemService.class);
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