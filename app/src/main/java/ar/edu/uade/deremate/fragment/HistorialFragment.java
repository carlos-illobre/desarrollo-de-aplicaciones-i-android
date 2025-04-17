package ar.edu.uade.deremate.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.adapter.EntregaAdapter;
import ar.edu.uade.deremate.model.Entrega;

public class HistorialFragment extends Fragment {

    private RecyclerView recyclerEntregas;
    private List<Entrega> listaEntregas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        recyclerEntregas = view.findViewById(R.id.recyclerEntregas);
        recyclerEntregas.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarEntregas();

        EntregaAdapter adapter = new EntregaAdapter(listaEntregas);
        recyclerEntregas.setAdapter(adapter);

        return view;
    }

    private void cargarEntregas() {
        listaEntregas.add(new Entrega("Juan Pérez", "2025-04-14 15:00", "Completada", "Av. Corrientes 1234", "ABC123"));
        listaEntregas.add(new Entrega("Lucía Gómez", "2025-04-13 17:20", "Completada", "Calle San Martín 456", "XYZ789"));
    }
}

