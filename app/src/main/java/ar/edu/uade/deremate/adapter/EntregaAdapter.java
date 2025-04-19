package ar.edu.uade.deremate.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.data.api.model.Entrega;
import ar.edu.uade.deremate.fragment.EntregaSelectedListener;

public class EntregaAdapter extends RecyclerView.Adapter<EntregaAdapter.EntregaViewHolder> {

    private List<Entrega> entregas;
    private EntregaSelectedListener listener;

    public EntregaAdapter(List<Entrega> entregas, EntregaSelectedListener listener) {
        this.entregas = entregas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EntregaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entrega, parent, false);
        return new EntregaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntregaViewHolder holder, int position) {
        Entrega entrega = entregas.get(position);
        holder.bind(entrega);
    }

    @Override
    public int getItemCount() {
        return entregas.size();
    }

    class EntregaViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCliente, txtFecha, txtEstado, txtDireccion;

        public EntregaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCliente = itemView.findViewById(R.id.txtCliente);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onEntregaSelected(entregas.get(position));
                }
            });
        }

        public void bind(Entrega entrega) {
            txtCliente.setText(entrega.getCliente());
            txtFecha.setText(entrega.getFechaEntrega());
            txtEstado.setText(entrega.getEstado());
            txtDireccion.setText(entrega.getDireccion());
        }
    }
}
