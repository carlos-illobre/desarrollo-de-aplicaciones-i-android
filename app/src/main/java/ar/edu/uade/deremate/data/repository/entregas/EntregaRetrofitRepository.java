package ar.edu.uade.deremate.data.repository.entregas;

import android.util.Log;

import androidx.annotation.NonNull;

import ar.edu.uade.deremate.data.repository.token.TokenRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.deremate.data.api.EntregaService;
import ar.edu.uade.deremate.data.api.model.Entrega;

@Singleton
public class EntregaRetrofitRepository implements EntregaRepository {
    @Inject
    EntregaService entregaService;
    @Inject
    TokenRepository tokenRepository;

    @Inject
    public EntregaRetrofitRepository(EntregaService entregaService, TokenRepository tokenRepository) {
        this.entregaService = entregaService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void getEntregas(EntregaServiceCallback<List<Entrega>> callback) {
        String token = tokenRepository.getToken();

        if (token == null || token.isEmpty()) {
            callback.onError(new RuntimeException("Token no disponible"));
            return;
        }

        Log.d("API", "Token de autorización: " + token); // Agrega un log del token

        entregaService.getEntregas("Bearer " + token).enqueue(new Callback<List<Entrega>>() {
            @Override
            public void onResponse(@NonNull Call<List<Entrega>> call, @NonNull Response<List<Entrega>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d("API", "Entregas obtenidas: " + response.body().size()); // Agrega log del tamaño de las entregas
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError(new RuntimeException("Respuesta vacía del servidor"));
                    }
                } else {
                    callback.onError(new RuntimeException("Error en la respuesta: " + response.code() + " - " + response.message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Entrega>> call, @NonNull Throwable t) {
                Log.e("API", "Error en la llamada", t); // Log del error en caso de fallo
                callback.onError(new RuntimeException("GET /entregas ERROR: ", t));
            }
        });
    }
}
