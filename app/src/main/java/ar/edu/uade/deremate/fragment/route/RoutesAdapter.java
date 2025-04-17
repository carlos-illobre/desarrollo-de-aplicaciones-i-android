package ar.edu.uade.deremate.fragment.route;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.data.api.model.RouteResponse;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteViewHolder> {

    private List<RouteResponse> routeList;
    private RouteSelectedListener listener;

    public RoutesAdapter(@NonNull List<RouteResponse> routeList, RouteSelectedListener listener) {
        this.routeList = routeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoutesAdapter.RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesAdapter.RouteViewHolder holder, int position) {
        RouteResponse route = routeList.get(position);
        holder.bind(route);
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView descriptionTextView;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_route_name);
            descriptionTextView = itemView.findViewById(R.id.tv_route_description);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRouteSelected(routeList.get(position));
                }
            });
        }

        public void bind(RouteResponse route) {
            nameTextView.setText(route.getName());
            descriptionTextView.setText(route.getDescription());
        }
    }
}
