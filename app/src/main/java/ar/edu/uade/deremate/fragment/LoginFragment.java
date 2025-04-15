package ar.edu.uade.deremate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import ar.edu.uade.deremate.MainActivity;
import ar.edu.uade.deremate.R;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import ar.edu.uade.deremate.data.repository.token.TokenRepository;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerButton;
    private TextView forgotPasswordButton;
    private TextView messageText;
    @Inject
    TokenRepository tokenRepository;

    public static final String BASE_URL = "http://10.0.2.2:3000";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        loginButton = view.findViewById(R.id.loginButton);
        registerButton = view.findViewById(R.id.registerButton);
        forgotPasswordButton = view.findViewById(R.id.forgotPasswordButton);
        messageText = view.findViewById(R.id.messageText);

        loginButton.setEnabled(false);

        setupListeners();
        checkQueryStringMessage();

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

        //loginButton.setOnClickListener(v -> attemptLogin());
        registerButton.setOnClickListener(v -> navigateToRegister());
        forgotPasswordButton.setOnClickListener(v -> navigateToForgotPassword());
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
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                URL url = new URL(BASE_URL + "/auth/signin");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("email", email);
                jsonBody.put("password", password);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(jsonBody.toString());
                writer.flush();
                writer.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String response = new java.util.Scanner(conn.getInputStream()).useDelimiter("\\A").next();
                    JSONObject jsonResponse = new JSONObject(response);
                    String token = jsonResponse.getString("access_token");

                    tokenRepository.saveToken(token);

                    requireActivity().runOnUiThread(() -> {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    });

                } else {
                    String errorResponse = new java.util.Scanner(conn.getErrorStream()).useDelimiter("\\A").next();
                    JSONObject jsonError = new JSONObject(errorResponse);
                    String message = jsonError.has("message") ? jsonError.getJSONArray("message").getString(0) : "Error desconocido";


                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show()
                    );
                }

            } catch (Exception e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getActivity(), "Error de red o servidor", Toast.LENGTH_LONG).show()
                );
            }
        });
    }


    private void navigateToRegister() {
        Toast.makeText(getActivity(), "Registro", Toast.LENGTH_SHORT).show();
    }

    private void navigateToForgotPassword() {
        Toast.makeText(getActivity(), "Olvidaste contraseña", Toast.LENGTH_SHORT).show();
    }


    private void checkQueryStringMessage() {
        if (getArguments() != null) {
            String message = getArguments().getString("message");
            if (message != null) {
                messageText.setText(message);
                messageText.setVisibility(View.VISIBLE);
            }
        }
    }
}