package Api;

import com.example.bloodbankmanagementsystem.ImageResponse;

import java.util.List;

import Model.LoginResponse;
import Model.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApi {
    @GET("api/v1/user")
    Call<List<User>> getUsers();

    @POST("api/v1/authUser")
    Call<LoginResponse> getResponse(@Body User user);

    @POST("api/v1/user")
    Call<Void> addUsers(@Body User user);

    @Multipart
    @POST("api/v1/upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);
}

