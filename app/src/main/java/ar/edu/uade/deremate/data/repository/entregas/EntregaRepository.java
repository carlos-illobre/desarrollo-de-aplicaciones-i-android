package ar.edu.uade.deremate.data.repository.entregas;

import java.util.List;

import ar.edu.uade.deremate.data.api.model.Entrega;

public interface EntregaRepository {

    void getEntregas( EntregaServiceCallback<List<Entrega>> callback);

}

