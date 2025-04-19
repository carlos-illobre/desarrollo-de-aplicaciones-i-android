package ar.edu.uade.deremate.data.repository.entregas;

public interface EntregaServiceCallback<T> {
    void onSuccess(T response);
    void onError(Throwable error);
}

