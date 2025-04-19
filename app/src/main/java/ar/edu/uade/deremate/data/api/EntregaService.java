package ar.edu.uade.deremate.data.api;

import ar.edu.uade.deremate.data.api.model.Entrega;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import java.util.List;

public interface EntregaService {
    @GET("entregas")
    Call<List<Entrega>> getEntregas(@Header("Authorization") String authHeader);

    @GET("ntregas/{id}")
    Call<Entrega> getEntregaById(@Path("id") String id, @Header("Authorization") String authHeader);
}
