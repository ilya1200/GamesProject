package Ilya.Project.GamesProject.model.network.user;

import Ilya.Project.GamesProject.model.data.user.User;
import Ilya.Project.GamesProject.model.data.user.UserScore;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("users")
    Call<Void> login(@Query("username") String username, @Query("password") String password);

    @POST("users")
    Call<Void> signUp(@Body User user);

    @GET("users/score")
    Call<UserScore> getScore(@Query("username") String username);
}