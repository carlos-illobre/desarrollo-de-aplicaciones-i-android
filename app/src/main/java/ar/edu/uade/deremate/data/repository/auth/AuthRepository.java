package ar.edu.uade.deremate.data.repository.auth;

public interface AuthRepository {
    void confirmSignup(String signupCode, AuthServiceCallback callback);
}
