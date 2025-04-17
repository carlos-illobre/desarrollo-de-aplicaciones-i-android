package ar.edu.uade.deremate.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.model.Entrega;
public class EntregaAdapter extends RecyclerView.Adapter<EntregaAdapter.EntregaViewHolder> {

    private List<Entrega> listaEntregas;

    public EntregaAdapter(List<Entrega> listaEntregas) {
        this.listaEntregas = listaEntregas;
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
        Entrega entrega = listaEntregas.get(position);
        holder.tvCliente.setText("Cliente: " + entrega.getCliente());
        holder.tvFecha.setText("Fecha: " + entrega.getFechaEntrega());
        holder.tvEstado.setText("Estado: " + entrega.getEstado());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("cliente", entrega.getCliente());
            bundle.putString("fechaEntrega", entrega.getFechaEntrega());
            bundle.putString("estado", entrega.getEstado());
            bundle.putString("direccion", entrega.getDireccion());

            Navigation.findNavController(v).navigate(R.id.action_historialFragment_to_detalleEntregaFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listaEntregas.size();
    }

    public static class EntregaViewHolder extends RecyclerView.ViewHolder {
        TextView tvCliente, tvFecha, tvEstado;

        public EntregaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvEstado = itemView.findViewById(R.id.tvEstado);
        }
    }
}
