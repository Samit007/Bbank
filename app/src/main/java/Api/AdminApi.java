package Api;

import java.util.List;

import Model.Admin;
import Model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AdminApi {

    @POST("api/v1/authadmin")
    Call<LoginResponse> getAdmin(@Body Admin admin);

}
