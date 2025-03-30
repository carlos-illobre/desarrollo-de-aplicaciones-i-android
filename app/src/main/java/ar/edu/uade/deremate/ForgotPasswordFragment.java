package ar.edu.uade.deremate;

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
public class ForgotPasswordFragment extends Fragment {
    private EditText emailInput;
    private Button resetButton;
    private TextView loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailInput = view.findViewById(R.id.emailInput);
        resetButton = view.findViewById(R.id.resetButton);
        loginButton = view.findViewById(R.id.loginButton);

        resetButton.setEnabled(false);
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        resetButton.setOnClickListener(v -> resetPassword());
        loginButton.setOnClickListener(v -> navigateToLogin());
    }

    private void validateEmail() {
        String email = emailInput.getText().toString().trim();
        boolean isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();

        if (!isValidEmail) {
            emailInput.setError("Formato de email inválido");
        }

        resetButton.setEnabled(isValidEmail);
    }

    private void resetPassword() {
        Toast.makeText(getActivity(), "Enlace de recuperación enviado", Toast.LENGTH_SHORT).show();
    }

    private void navigateToLogin() {
        getParentFragmentManager().popBackStack();
    }
}

