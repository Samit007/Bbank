package Api;

import java.util.List;

import Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @GET("users")
    Call<List<User>> getUsers();

    @POST("items")
    Call<Void> addUsers(@Body User user);
}

