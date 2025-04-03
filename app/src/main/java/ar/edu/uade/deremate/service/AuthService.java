package ar.edu.uade.deremate.service;

import ar.edu.uade.deremate.model.ConfirmSignupRequest;
import ar.edu.uade.deremate.model.RecoverPasswordRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/auth/signup/confirm")
    Call<Void> confirmSignup(@Body ConfirmSignupRequest request);

    @POST("/auth/password-reset")
    Call<Void> recoverPassword(@Body RecoverPasswordRequest request);

}
