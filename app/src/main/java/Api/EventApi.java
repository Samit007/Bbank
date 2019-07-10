package Api;

import java.util.List;

import Model.DeleteResponse;
import Model.Event;
import Model.RegisterResponse;
import Model.UpdateEResponse;
import Model.UpdateResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface EventApi {
    @GET("api/v1/event")
    Call<List<Event>> getEvent();

    @POST("api/v1/event")
    Call<RegisterResponse> addEvent(@Body Event event);

    @PUT("api/v1/event")
    Call<UpdateEResponse> updateEvent(@Body Event event);

    @HTTP(method = "DELETE", path = "api/v1/event", hasBody = true)
    Call<DeleteResponse> deleteEvent(@Body Event event);


}
