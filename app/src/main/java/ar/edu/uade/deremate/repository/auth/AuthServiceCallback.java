package ar.edu.uade.deremate.repository.auth;

public interface AuthServiceCallback {
    void onSuccess();
    void onError(Throwable error);
}
