package ar.edu.uade.deremate.repository.auth;

public interface AuthRepository {
    void confirmSignup(String signupCode, AuthServiceCallback callback);
}
