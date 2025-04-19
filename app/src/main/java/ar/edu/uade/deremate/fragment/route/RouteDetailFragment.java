package ar.edu.uade.deremate.fragment.route;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.data.api.model.RouteResponse;

public class RouteDetailFragment extends Fragment {

    private static final String ARG_ROUTE = "route";

    public RouteDetailFragment() {
        // Required empty public constructor
    }

    public static RouteDetailFragment newInstance(RouteResponse route) {
        RouteDetailFragment fragment = new RouteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ROUTE, route);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_route_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        if (getArguments() != null) {
            RouteResponse route = getArguments().getParcelable(ARG_ROUTE);
            if (route != null) {
                updateUI(route);
            }
        }

        FloatingActionButton fab = view.findViewById(R.id.fab_action);
        fab.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Iniciar Navegacion", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateUI(@NonNull RouteResponse route) {
        TextView packTextView = getView().findViewById(R.id.tv_route_package_id);
        TextView wareTextView = getView().findViewById(R.id.tv_route_warehouse);
        TextView destTextView = getView().findViewById(R.id.tv_route_destination_neighborhood);

        packTextView.setText(route.getPackageId());
        wareTextView.setText(route.getWarehouse());
        destTextView.setText(route.getDestinationNeighborhood());
    }
}

