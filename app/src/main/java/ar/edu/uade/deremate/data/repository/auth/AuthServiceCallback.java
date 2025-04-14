package ar.edu.uade.deremate.data.repository.auth;

public interface AuthServiceCallback {
    void onSuccess();
    void onError(Throwable error);
}
