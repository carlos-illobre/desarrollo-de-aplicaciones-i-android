package ar.edu.uade.deremate.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ar.edu.uade.deremate.R;

public class DetalleEntregaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_entrega, container, false);

        TextView tvCliente = view.findViewById(R.id.tvDetalleCliente);
        TextView tvFecha = view.findViewById(R.id.tvDetalleFecha);
        TextView tvEstado = view.findViewById(R.id.tvDetalleEstado);
        TextView tvDireccion = view.findViewById(R.id.tvDetalleDireccion);

        Bundle args = getArguments();
        if (args != null) {
            tvCliente.setText("Cliente: " + args.getString("cliente"));
            tvFecha.setText("Fecha: " + args.getString("fecha"));
            tvEstado.setText("Estado: " + args.getString("estado"));
            tvDireccion.setText("Dirección: " + args.getString("direccion"));
        }

        return view;
    }
}
