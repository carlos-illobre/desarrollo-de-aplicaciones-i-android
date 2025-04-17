package ar.edu.uade.deremate.fragment.route;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        if (getArguments() != null) {
            RouteResponse route = getArguments().getParcelable(ARG_ROUTE);

            TextView nameTextView = view.findViewById(R.id.tv_route_name);
            TextView descTextView = view.findViewById(R.id.tv_route_description);

            assert route != null;

            nameTextView.setText(route.getName());
            descTextView.setText(route.getDescription());
        }
    }
}