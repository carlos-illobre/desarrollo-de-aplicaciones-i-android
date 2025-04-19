package ar.edu.uade.deremate.fragment.route;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.data.api.model.RouteResponse;

public class RoutesFragment extends Fragment {

    private static final String ARG_ROUTES = "routes";
    private RouteSelectedListener listener;

    public RoutesFragment() {
        // Required empty public constructor
    }

    public static RoutesFragment newInstance(@NonNull List<RouteResponse> routes) {
        RoutesFragment fragment = new RoutesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ROUTES, new ArrayList<>(routes));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (RouteSelectedListener) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(context.toString() + "must implement RouteSelectedListener", e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            List<RouteResponse> routes = getArguments().getParcelableArrayList(ARG_ROUTES);

            RecyclerView recyclerView = view.findViewById(R.id.rv_routes);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            List<RouteResponse> routeList = routes != null ? routes : new ArrayList<>();

            RoutesAdapter adapter = new RoutesAdapter(routeList, route -> {
                if (listener != null) {
                    listener.onRouteSelected(route);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}