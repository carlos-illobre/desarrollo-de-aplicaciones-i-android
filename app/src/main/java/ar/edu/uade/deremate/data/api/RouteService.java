package ar.edu.uade.deremate.data.api;

import java.util.List;

import ar.edu.uade.deremate.data.api.model.RouteResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RouteService {
    @GET("routes")
    Call<List<RouteResponse>> getRoutes(@Header("Authorization") String authHeader);
}
