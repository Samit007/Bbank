package Api;

import com.example.bloodbankmanagementsystem.ImageResponse;

import java.util.List;

import Model.DeleteResponse;
import Model.LoginResponse;
import Model.RegisterResponse;
import Model.UpdateResponse;
import Model.User;
import Model.User2;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UserApi {

    @GET("api/v1/user")
    Call<List<User>> getUsers();

    @POST("api/v1/authUser")
    Call<LoginResponse> getResponse(@Body User user);

    @POST("api/v1/user")
    Call<RegisterResponse> addUsers(@Body User user);

    @Multipart
    @POST("api/v1/upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @PUT("api/v1/user")
    Call<UpdateResponse> updateUsers(@Body User user);

    @HTTP(method = "DELETE", path = "api/v1/user", hasBody = true)
    Call<DeleteResponse> deleteUsers(@Body User2 user2);

//    @HTTP(method = "DELETE", path = "api/v1/user/:userid", hasBody = true)
//    Call<DeleteResponse> delUsers(@Body User2 user2);
}