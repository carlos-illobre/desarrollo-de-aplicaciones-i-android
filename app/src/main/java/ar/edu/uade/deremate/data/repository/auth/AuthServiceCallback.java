package ar.edu.uade.deremate.data.repository.auth;

public interface AuthServiceCallback<T> {
    void onSuccess(T response);
    void onError(Throwable error);
}
