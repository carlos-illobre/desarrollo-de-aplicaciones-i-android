package ar.edu.uade.deremate.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.adapter.EntregaAdapter;
import ar.edu.uade.deremate.data.api.EntregaService;
import ar.edu.uade.deremate.data.api.model.Entrega;

import ar.edu.uade.deremate.data.repository.entregas.EntregaRetrofitRepository;
import ar.edu.uade.deremate.data.repository.entregas.EntregaServiceCallback;
import dagger.hilt.android.AndroidEntryPoint;
import ar.edu.uade.deremate.data.repository.entregas.EntregaRepository;
@AndroidEntryPoint
public class HistorialFragment extends Fragment {

    private RecyclerView recyclerEntregas;
    private EntregaAdapter adapter;
    private List<Entrega> listaEntregas = new ArrayList<>();
    private ProgressBar progressBar;

    @Inject
    EntregaRepository entregaRepository;  // Inyecta el repositorio

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        // Configurar RecyclerView
        recyclerEntregas = view.findViewById(R.id.recyclerEntregas);
        recyclerEntregas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener referencia al ProgressBar
        progressBar = view.findViewById(R.id.progressBar);

        // Inicializar adapter y asignarlo al RecyclerView
        adapter = new EntregaAdapter(listaEntregas);
        recyclerEntregas.setAdapter(adapter);

        // Llamar al backend para traer las entregas
        cargarEntregas();

        return view;
    }

    private void cargarEntregas() {
        if (entregaRepository == null) {
            Log.e("HistorialFragment", "EntregaRepository is null");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        entregaRepository.getEntregas(new EntregaServiceCallback<List<Entrega>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<Entrega> entregas) {
                progressBar.setVisibility(View.GONE);
                listaEntregas.clear();
                listaEntregas.addAll(entregas);
                adapter.notifyDataSetChanged();

                if (listaEntregas.isEmpty()) {
                    Toast.makeText(getContext(), "No hay entregas disponibles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable error) {
                progressBar.setVisibility(View.GONE);
                Log.e("API", "Error al cargar entregas", error);
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}