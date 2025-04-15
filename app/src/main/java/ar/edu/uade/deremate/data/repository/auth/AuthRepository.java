package ar.edu.uade.deremate.data.repository.auth;

import ar.edu.uade.deremate.data.api.model.LoginResponse;
import ar.edu.uade.deremate.data.api.model.RecoverPasswordRequest;

public interface AuthRepository {

    void login(String email, String password, AuthServiceCallback<LoginResponse> callback);
    void confirmSignup(String signupCode, AuthServiceCallback<Void> callback);
    void recoverPassword(RecoverPasswordRequest request, AuthServiceCallback<Void> callback);

}
