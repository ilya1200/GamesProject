package Ilya.Project.GamesProject.model.network.user;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    @GET("users")
    Call<Void> login(@Query("username") String username, @Query("password") String password);
}