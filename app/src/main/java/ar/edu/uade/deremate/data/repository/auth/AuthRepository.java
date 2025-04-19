package ar.edu.uade.deremate.data.repository.auth;

import ar.edu.uade.deremate.data.api.model.LoginResponse;
import ar.edu.uade.deremate.data.api.model.RecoverPasswordRequest;
import ar.edu.uade.deremate.data.api.model.SignUpRequest;

public interface AuthRepository {

    void login(String email, String password, AuthServiceCallback<LoginResponse> callback);
    void confirmSignup(String otp, AuthServiceCallback<Void> callback);
    void recoverPassword(RecoverPasswordRequest request, AuthServiceCallback<Void> callback);
    void confirmPasswordReset(String otp, AuthServiceCallback<Void> callback);
    void register(SignUpRequest request, AuthServiceCallback<Void> callback);

}
