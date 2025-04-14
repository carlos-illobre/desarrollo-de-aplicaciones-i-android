package ar.edu.uade.deremate.data.api;

import ar.edu.uade.deremate.data.api.model.ConfirmSignupRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/auth/signup/confirm")
    Call<Void> confirmSignup(@Body ConfirmSignupRequest request);
}
