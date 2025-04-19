package ar.edu.uade.deremate.data.repository.route;

import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.deremate.RoutesActivity;
import ar.edu.uade.deremate.data.api.AuthService;
import ar.edu.uade.deremate.data.api.RouteService;
import ar.edu.uade.deremate.data.api.model.LoginResponse;
import ar.edu.uade.deremate.data.api.model.RouteResponse;
import ar.edu.uade.deremate.data.repository.auth.AuthServiceCallback;
import ar.edu.uade.deremate.data.repository.token.TokenRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class RouteRetrofitRepository implements RouteRepository {
    @Inject
    TokenRepository tokenRepository;
    @Inject
    RouteService routeService;

    @Inject
    public RouteRetrofitRepository(RouteService routeService, TokenRepository tokenRepository) {
        this.routeService = routeService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void getRoutes(AuthServiceCallback<List<RouteResponse>> callback) {
        String token = tokenRepository.getToken();
        routeService.getRoutes("Bearer " + token).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<RouteResponse>> call, @NonNull Response<List<RouteResponse>> response) {
                if (response.isSuccessful()) {
                    List<RouteResponse> routes = response.body();
                    callback.onSuccess(routes);
                } else {
                    callback.onError(new RuntimeException("GET /routes ERROR: " + response.code() + " " + response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RouteResponse>> call, @NonNull Throwable t) {
                callback.onError(new RuntimeException("GET /routes ERROR: ", t));
            }
        });
    }
}
