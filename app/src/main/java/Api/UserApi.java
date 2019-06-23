package Api;

import java.util.List;

import Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @GET("api/v1/user")
    Call<List<User>> getUsers();

    @POST("api/v1/user")
    Call<Void> addUsers(@Body User user);
}

