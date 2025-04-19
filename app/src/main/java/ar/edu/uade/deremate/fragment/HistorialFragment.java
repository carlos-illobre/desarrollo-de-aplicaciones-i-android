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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.adapter.EntregaAdapter;
import ar.edu.uade.deremate.data.api.model.Entrega;
import ar.edu.uade.deremate.data.repository.entregas.EntregaServiceCallback;
import ar.edu.uade.deremate.data.repository.entregas.EntregaRepository;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistorialFragment extends Fragment implements EntregaSelectedListener {

    private RecyclerView recyclerEntregas;
    private EntregaAdapter adapter;
    private List<Entrega> listaEntregas = new ArrayList<>();
    private ProgressBar progressBar;

    @Inject
    EntregaRepository entregaRepository;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        recyclerEntregas = view.findViewById(R.id.recyclerEntregas);
        recyclerEntregas.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progressBar);

        adapter = new EntregaAdapter(listaEntregas, this);
        recyclerEntregas.setAdapter(adapter);

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

    @Override
    public void onEntregaSelected(Entrega entrega) {
        // Create a new instance of EntregaDetailFragment
        EntregaDetailFragment entregaDetailFragment = new EntregaDetailFragment();

        // Create a Bundle to pass data
        Bundle bundle = new Bundle();
        bundle.putString("cliente", entrega.getCliente());
        bundle.putString("fecha_entrega", entrega.getFechaEntrega());
        bundle.putString("estado", entrega.getEstado());
        bundle.putString("direccion", entrega.getDireccion());

        // Set the arguments for the fragment
        entregaDetailFragment.setArguments(bundle);

        // Replace the current fragment with EntregaDetailFragment
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, entregaDetailFragment) // Use the correct container ID
                .addToBackStack(null) // Optional: Add to back stack to allow navigation back
                .commit();
    }
}
