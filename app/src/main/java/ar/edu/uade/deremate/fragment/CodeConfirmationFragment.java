package ar.edu.uade.deremate.fragment;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.data.repository.auth.AuthRepository;
import ar.edu.uade.deremate.data.repository.auth.AuthServiceCallback;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CodeConfirmationFragment extends Fragment {

    private EditText codeDigit1, codeDigit2, codeDigit3, codeDigit4, codeDigit5, codeDigit6;
    private Button acceptButton, cancelButton;

    @Inject
    AuthRepository authRepository;

    public static SignupCodeFragment newInstance() {
        SignupCodeFragment fragment = new SignupCodeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_signup_code_input, container, false);

        // Inicialización de los campos EditText y botones
        codeDigit1 = view.findViewById(R.id.code_digit_1);
        codeDigit2 = view.findViewById(R.id.code_digit_2);
        codeDigit3 = view.findViewById(R.id.code_digit_3);
        codeDigit4 = view.findViewById(R.id.code_digit_4);
        codeDigit5 = view.findViewById(R.id.code_digit_5);
        codeDigit6 = view.findViewById(R.id.code_digit_6);
        acceptButton = view.findViewById(R.id.accept_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        acceptButton.setEnabled(false); // Deshabilitar el botón de aceptación inicialmente

        // Configurar listeners
        setupListeners();

        cancelButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        acceptButton.setOnClickListener(v -> {
            Log.d("SignupCodeFragment", "Accept button clicked");

            // Obtener el código completo
            String signupCode = getSignupCode();
            confirmSignup(signupCode);
        });

        return view;
    }

    // Configurar listeners para los campos de código
    private void setupListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInput();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Añadir el textWatcher a todos los campos EditText
        codeDigit1.addTextChangedListener(textWatcher);
        codeDigit2.addTextChangedListener(textWatcher);
        codeDigit3.addTextChangedListener(textWatcher);
        codeDigit4.addTextChangedListener(textWatcher);
        codeDigit5.addTextChangedListener(textWatcher);
        codeDigit6.addTextChangedListener(textWatcher);
    }

    // Validar si el código es válido (6 dígitos)
    private void validateInput() {
        String signupCode = getSignupCode();
        boolean isValidCode = signupCode.matches("\\d{6}");

        acceptButton.setEnabled(isValidCode); // Habilitar el botón si el código es válido
    }

    // Obtener el código completo a partir de los 6 dígitos
    private String getSignupCode() {
        StringBuilder code = new StringBuilder();
        code.append(codeDigit1.getText().toString());
        code.append(codeDigit2.getText().toString());
        code.append(codeDigit3.getText().toString());
        code.append(codeDigit4.getText().toString());
        code.append(codeDigit5.getText().toString());
        code.append(codeDigit6.getText().toString());

        return code.toString();
    }

    // Confirmar el registro con el código ingresado
    private void confirmSignup(String signupCode) {
        authRepository.confirmSignup(signupCode, new AuthServiceCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                Toast.makeText(getActivity(), "Signup confirmed successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable error) {
                Log.e("SignupCodeFragment", "API call failed", error);
                Toast.makeText(getActivity(), "Failed to confirm signup", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
