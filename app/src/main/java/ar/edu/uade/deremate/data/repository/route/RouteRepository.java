package ar.edu.uade.deremate.data.repository.route;

import java.util.List;

import ar.edu.uade.deremate.data.api.model.RouteResponse;
import ar.edu.uade.deremate.data.repository.auth.AuthServiceCallback;
import retrofit2.Callback;

public interface RouteRepository {
    void getRoutes(AuthServiceCallback<List<RouteResponse>> callback);
}
