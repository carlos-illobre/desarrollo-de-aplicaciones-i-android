package ar.edu.uade.deremate.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;

import ar.edu.uade.deremate.R;

import javax.inject.Inject;

import ar.edu.uade.deremate.data.api.model.LoginResponse;
import ar.edu.uade.deremate.data.repository.auth.AuthRepository;
import ar.edu.uade.deremate.data.repository.auth.AuthServiceCallback;
import ar.edu.uade.deremate.data.repository.token.TokenRepository;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerButton;
    private TextView forgotPasswordButton;
    @Inject
    TokenRepository tokenRepository;
    @Inject
    AuthRepository authRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        loginButton = view.findViewById(R.id.loginButton);
        registerButton = view.findViewById(R.id.registerButton);
        forgotPasswordButton = view.findViewById(R.id.forgotPasswordButton);

        loginButton.setEnabled(false);

        setupListeners();

        return view;
    }

    private void setupListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        emailInput.addTextChangedListener(textWatcher);
        passwordInput.addTextChangedListener(textWatcher);

        loginButton.setOnClickListener(v -> attemptLogin());
        registerButton.setOnClickListener(v ->
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registerFragment));
        forgotPasswordButton.setOnClickListener(v ->
                NavHostFragment.findNavController(LoginFragment.this)
                .navigate(R.id.action_FirstFragment_to_ForgotPassword));
    };

    private void validateInputs() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        boolean isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isValidPassword = !password.isEmpty();

        if (!isValidEmail) {
            emailInput.setError("Formato de email inválido");
        }

        loginButton.setEnabled(isValidEmail && isValidPassword);
    }

    private void attemptLogin() {
        try {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            this.authRepository.login(email, password, new AuthServiceCallback<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse response) {
                    Toast.makeText(getActivity(), "Signup successful!", Toast.LENGTH_SHORT).show();
                    tokenRepository.saveToken(response.getAccessToken());
                    Log.d("LOGIN", "Código de respuesta: " + response.getAccessToken());
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.action_loginFragment_to_historialFragment);
                }

                @Override
                public void onError(Throwable error) {
                    Log.e("RecoverPasswordFragment", "API call failed", error);
                    Toast.makeText(getActivity(), "Failed to recover password", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("LoginFragment", "Error during login", e);
            Toast.makeText(getActivity(), "Error during login, please try again", Toast.LENGTH_SHORT).show();
        }
    }
}