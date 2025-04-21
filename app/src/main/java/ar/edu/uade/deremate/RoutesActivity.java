package ar.edu.uade.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import javax.inject.Inject;

import ar.edu.uade.deremate.data.api.model.RouteResponse;
import ar.edu.uade.deremate.data.repository.auth.AuthServiceCallback;
import ar.edu.uade.deremate.data.repository.route.RouteRepository;
import ar.edu.uade.deremate.data.repository.token.TokenRepository;
import ar.edu.uade.deremate.fragment.HistorialFragment;
import ar.edu.uade.deremate.fragment.route.RouteDetailFragment;
import ar.edu.uade.deremate.fragment.route.RouteSelectedListener;
import ar.edu.uade.deremate.fragment.route.RoutesFragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RoutesActivity extends AppCompatActivity implements RouteSelectedListener {

    private BottomNavigationView bottomNavLeft;
    private BottomNavigationView bottomNavRight;
    @Inject
    RouteRepository routeRepository;
    @Inject
    TokenRepository tokenRepository;

    @Override
    protected void onResume() {
        super.onResume();
        loadRoutesAndShowFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        bottomNavLeft = findViewById(R.id.bottom_navigation_left);
        bottomNavRight = findViewById(R.id.bottom_navigation_right);

        bottomNavLeft.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_profile) {
                showProfile();
                return true;
            } else if (item.getItemId() == R.id.menu_routes) {
                loadRoutesAndShowFragment();
                return true;
            }
            return false;
        });

        bottomNavRight.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_logout) {
                logout(false);
                return true;
            } else if (item.getItemId() == R.id.menu_historial) {
                showHistorial();
                return true;
            }
            return false;
        });
    }
    private void loadRoutesAndShowFragment() {
        routeRepository.getRoutes(new AuthServiceCallback<>() {
            @Override
            public void onSuccess(List<RouteResponse> routes) {
                showRoutesFragment(routes);
            }

            @Override
            public void onError(Throwable error) {
                if (tokenRepository.isTokenExpired()) {
                    logout(true);
                } else {
                    Toast.makeText(RoutesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showRoutesFragment(List<RouteResponse> routes) {
        RoutesFragment routesFragment = RoutesFragment.newInstance(routes);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, routesFragment);
        transaction.commit();
    }

    private void showProfile() {
        Toast.makeText(this, "Perfil seleccionado", Toast.LENGTH_SHORT).show();
    }

    private void logout(boolean expired) {
        // Clear session data (e.g., token)
        tokenRepository.clearToken();

        // Navigate to the login screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
        startActivity(intent);

        if (expired) {
            Toast.makeText(this, "Session expired, please login again", Toast.LENGTH_SHORT).show();
        }
        // Finish the current activity
        finish();
    }


    @Override
    public void onRouteSelected(RouteResponse route) {
        showRouteDetail(route);
    }

    private void showRouteDetail(RouteResponse route) {
        RouteDetailFragment detailFragment = RouteDetailFragment.newInstance(route);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack("route_detail") // Permite volver atras con el boton de retroceso
                .commit();
    }

    private void showHistorial() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HistorialFragment())
                .addToBackStack("historial")
                .commit();
    }
}