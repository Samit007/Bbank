package Api;

import java.util.List;

import Model.Admin;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AdminApi {
    @GET("/api/v1/admin")
    Call<List<Admin>> getAdmin();
}
