package ar.edu.uade.deremate.data.api;

import ar.edu.uade.deremate.data.api.model.ConfirmPasswordResetRequest;
import ar.edu.uade.deremate.data.api.model.ConfirmSignupRequest;
import ar.edu.uade.deremate.data.api.model.LoginRequest;
import ar.edu.uade.deremate.data.api.model.LoginResponse;
import ar.edu.uade.deremate.data.api.model.RecoverPasswordRequest;
import ar.edu.uade.deremate.data.api.model.SignUpRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/auth/signin")
    Call<LoginResponse> signin(@Body LoginRequest request);

    @POST("/auth/signup/confirm")
    Call<Void> confirmSignup(@Body ConfirmSignupRequest request);

    @POST("/auth/password-reset")
    Call<Void> recoverPassword(@Body RecoverPasswordRequest request);

    @POST("/auth/password-reset/confirm")
    Call<Void> confirmPasswordReset(@Body ConfirmPasswordResetRequest request);

    @POST("auth/signup")
    Call<Void> signUp(@Body SignUpRequest request);

}
