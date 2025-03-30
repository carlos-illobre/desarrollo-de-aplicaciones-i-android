package ar.edu.uade.deremate;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerButton, forgotPasswordButton, messageText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        loginButton = view.findViewById(R.id.loginButton);
        registerButton = view.findViewById(R.id.registerButton);
        forgotPasswordButton = view.findViewById(R.id.forgotPasswordButton);
        messageText = view.findViewById(R.id.messageText);

        loginButton.setEnabled(false);
        emailInput.addTextChangedListener(textWatcher);
        passwordInput.addTextChangedListener(textWatcher);

        loginButton.setOnClickListener(v -> attemptLogin());
        registerButton.setOnClickListener(v -> navigateToRegister());
        forgotPasswordButton.setOnClickListener(v -> navigateToForgotPassword());

        checkQueryStringMessage();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validateInputs();
        }

        @Override
        public void afterTextChanged(Editable s) {}
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

        if (password.equals("incorrecta")) {
            Toast.makeText(getActivity(), "Clave incorrecta", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", getActivity().MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }

    private void navigateToRegister() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new RegisterFragment()); // Asegúrate de que R.id.fragment_container es el contenedor de los fragments en tu actividad
        transaction.addToBackStack(null); // Permite volver atrás
        transaction.commit();
    }

    private void navigateToForgotPassword() {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ForgotPasswordFragment());
        transaction.addToBackStack(null);
        transaction.commit();
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