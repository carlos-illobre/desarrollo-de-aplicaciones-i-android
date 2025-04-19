package ar.edu.uade.deremate.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ar.edu.uade.deremate.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

public class EntregaDetailFragment extends Fragment {

    private MaterialTextView tvCliente, tvFechaEntrega, tvEstado, tvDireccion;
    private FloatingActionButton fabAction;
    private MaterialToolbar toolbar;

    public EntregaDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entrega_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Bind the views
        tvCliente = view.findViewById(R.id.tv_cliente);
        tvFechaEntrega = view.findViewById(R.id.tv_fecha_entrega);
        tvEstado = view.findViewById(R.id.tv_estado);
        tvDireccion = view.findViewById(R.id.tv_direccion);
        toolbar = view.findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        if (getArguments() != null) {
            String cliente = getArguments().getString("cliente", "N/A");
            String fechaEntrega = getArguments().getString("fecha_entrega", "N/A");
            String estado = getArguments().getString("estado", "N/A");
            String direccion = getArguments().getString("direccion", "N/A");

            tvCliente.setText(cliente);
            tvFechaEntrega.setText(fechaEntrega);
            tvEstado.setText(estado);
            tvDireccion.setText(direccion);
        }

    }
}