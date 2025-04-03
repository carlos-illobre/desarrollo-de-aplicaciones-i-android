package ar.edu.uade.deremate.service;

import ar.edu.uade.deremate.model.SignUpRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("auth/signup")
    Call<Void> signUp(@Body SignUpRequest request);
}
