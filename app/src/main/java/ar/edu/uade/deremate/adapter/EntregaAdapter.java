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
import ar.edu.uade.deremate.data.api.model.Entrega;
public class EntregaAdapter extends RecyclerView.Adapter<EntregaAdapter.EntregaViewHolder> {

    private List<Entrega> entregas;

    public EntregaAdapter(List<Entrega> entregas) {
        this.entregas = entregas;
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

    static class EntregaViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCliente;
        private TextView txtFecha;
        private TextView txtEstado;
        private TextView txtDireccion;

        public EntregaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCliente = itemView.findViewById(R.id.txtCliente);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
        }

        public void bind(Entrega entrega) {
            txtCliente.setText(entrega.getCliente());
            txtFecha.setText(entrega.getFechaEntrega());
            txtEstado.setText(entrega.getEstado());
            txtDireccion.setText(entrega.getDireccion());
        }
    }
}