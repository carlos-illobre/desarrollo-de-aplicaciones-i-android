package ar.edu.uade.deremate.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.data.api.model.SignUpRequest;
import ar.edu.uade.deremate.data.repository.auth.AuthRepository;
import ar.edu.uade.deremate.data.repository.auth.AuthServiceCallback;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    private Button register;
    private Button cancelar;
    private EditText editEmail;
    private EditText editContraseña;
    private EditText editConfirmContra;
    private EditText editNombre;
    private EditText editApellido;
    private boolean isValid = true;

    @Inject
    AuthRepository authRepository;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editEmail = view.findViewById(R.id.editTextEmailRegister);
        editContraseña = view.findViewById(R.id.editTextPasswordRegister);
        editConfirmContra = view.findViewById(R.id.editTextPasswordRegister2);
        register = view.findViewById(R.id.buttonRegister);
        cancelar = view.findViewById(R.id.buttonCancelRegister);
        editNombre = view.findViewById(R.id.editTextNombreRegister);
        editApellido = view.findViewById(R.id.editTextApellidoRegister);

        register.setEnabled(false);
        addTextWatchers();

        register.setOnClickListener(v -> registerUser());
        cancelar.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void registerUser() {
        String emailR = editEmail.getText().toString().trim();
        String nombreR = editNombre.getText().toString().trim();
        String apellidoR = editApellido.getText().toString().trim();
        String contraseñaR = editContraseña.getText().toString().trim();
        String confirmContraR = editConfirmContra.getText().toString().trim();

        isValid = false;
        checkFields();

        if (!register.isEnabled()) {
            return;
        }

        String fullName = nombreR + " " + apellidoR;
        SignUpRequest request = new SignUpRequest(emailR, fullName, contraseñaR, confirmContraR);

        this.authRepository.register(request, new AuthServiceCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                Toast.makeText(getActivity(), "Check your email", Toast.LENGTH_SHORT).show();
               // NavHostFragment.findNavController(RegisterFragment.this).navigate(R.id.action_registerFragment_to_signUpCodeFragment);
            }

            @Override
            public void onError(Throwable error) {
                Log.e("RegisterFragment", "API call failed",error);
                Toast.makeText(getActivity(), "Failed to register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addTextWatchers() {
        editEmail.addTextChangedListener(createTextWatcher());
        editContraseña.addTextChangedListener(createTextWatcher());
        editConfirmContra.addTextChangedListener(createTextWatcher());
        editNombre.addTextChangedListener(createTextWatcher());
        editApellido.addTextChangedListener(createTextWatcher());
    }

    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (isValid) checkFields();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };
    }

    private void checkFields() {
        String emailR = editEmail.getText().toString().trim();
        String contraseñaR = editContraseña.getText().toString().trim();
        String confirmContraR = editConfirmContra.getText().toString().trim();
        String nombreR = editNombre.getText().toString().trim();
        String apellidoR = editApellido.getText().toString().trim();

        boolean isEmailValid = true;
        boolean isPasswordValid = true;
        boolean isConfirmPasswordValid = true;
        boolean isNombreValid = true;
        boolean isApellidoValid = true;

        if (emailR.isEmpty()) {
            editEmail.setError("El email no puede estar vacío");
            isEmailValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailR).matches()) {
            editEmail.setError("Formato de email inválido");
            isEmailValid = false;
        } else {
            editEmail.setError(null);
        }

        if (contraseñaR.isEmpty()) {
            editContraseña.setError("La contraseña no puede estar vacía");
            isPasswordValid = false;
        } else if (contraseñaR.length() < 6) {
            editContraseña.setError("Debe tener al menos 6 caracteres");
            isPasswordValid = false;
        } else {
            editContraseña.setError(null);
        }

        if (confirmContraR.isEmpty()) {
            editConfirmContra.setError("Confirma tu contraseña");
            isConfirmPasswordValid = false;
        } else if (!confirmContraR.equals(contraseñaR)) {
            editConfirmContra.setError("Las contraseñas no coinciden");
            isConfirmPasswordValid = false;
        } else {
            editConfirmContra.setError(null);
        }

        if (nombreR.isEmpty()) {
            editNombre.setError("El nombre no puede estar vacío");
            isNombreValid = false;
        } else {
            editNombre.setError(null);
        }

        if (apellidoR.isEmpty()) {
            editApellido.setError("El apellido no puede estar vacío");
            isApellidoValid = false;
        } else {
            editApellido.setError(null);
        }

        register.setEnabled(isEmailValid && isPasswordValid && isConfirmPasswordValid && isNombreValid && isApellidoValid);
    }
}