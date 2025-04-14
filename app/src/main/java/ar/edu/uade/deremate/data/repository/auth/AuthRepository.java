package ar.edu.uade.deremate.data.repository.auth;

import ar.edu.uade.deremate.data.api.model.RecoverPasswordRequest;

public interface AuthRepository {

    void confirmSignup(String signupCode, AuthServiceCallback callback);

    void recoverPassword(RecoverPasswordRequest request , AuthServiceCallback callback);
}
