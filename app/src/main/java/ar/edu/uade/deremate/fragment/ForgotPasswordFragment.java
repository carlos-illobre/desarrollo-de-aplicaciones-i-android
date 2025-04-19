package ar.edu.uade.deremate.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.data.repository.auth.AuthRepository;
import ar.edu.uade.deremate.data.api.model.RecoverPasswordRequest;
import ar.edu.uade.deremate.data.repository.auth.AuthServiceCallback;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgotPasswordFragment extends Fragment {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button recoverButton;
    private Button cancelButton;
    @Inject
    AuthRepository authRepository;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recover_password, container, false);

        emailInput = view.findViewById(R.id.recoverPassword_emailInput);
        passwordInput = view.findViewById(R.id.recoverPassword_passwordInput);
        confirmPasswordInput = view.findViewById(R.id.recoverPassword_confirmPasswordInput);
        recoverButton = view.findViewById(R.id.recoverPassword_recoverButton);
        cancelButton = view.findViewById(R.id.recoverPassword_cancelButton);

        recoverButton.setEnabled(false);

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
        confirmPasswordInput.addTextChangedListener(textWatcher);

        recoverButton.setOnClickListener(v -> {
                    Log.d("RecoverPasswordFragment", "Recover button clicked");
                    recoverPassword(
                            emailInput.getText().toString().trim(),
                            passwordInput.getText().toString(),
                            confirmPasswordInput.getText().toString()
                    );
                });
        cancelButton.setOnClickListener(v->requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void validateInputs() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        boolean isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordMatch = password.equals(confirmPassword);

        emailInput.setError(isValidEmail ? null : getString(R.string.recover_password_email_format_error));
        confirmPasswordInput.setError(isPasswordMatch ? null : getString(R.string.recover_password_match_error));

        recoverButton.setEnabled(isValidEmail && isPasswordMatch && !email.isEmpty() && !password.isEmpty());
    }

    private void recoverPassword(String email, String password, String confirmPassword) {
        RecoverPasswordRequest request = new RecoverPasswordRequest()
                .setEmail(email)
                .setPassword(password)
                .setPasswordConfirmation(confirmPassword);

        this.authRepository.recoverPassword(request, new AuthServiceCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                Toast.makeText(getActivity(), "Password reset requested successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable error) {
                Log.e("RecoverPasswordFragment", "API call failed",error);
                Toast.makeText(getActivity(), "Failed to recover password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
