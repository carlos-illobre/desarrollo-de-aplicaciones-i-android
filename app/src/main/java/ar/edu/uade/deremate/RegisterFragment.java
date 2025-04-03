package ar.edu.uade.deremate;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ar.edu.uade.deremate.model.SignUpRequest;
import ar.edu.uade.deremate.service.AuthApiService;
import retrofit2.Call;

public class RegisterFragment extends Fragment {

    private Button register;
    private Button cancelar;
    private EditText editEmail;
    private EditText editContraseña;
    private EditText editConfirmContra;
    private EditText editNombre;
    private EditText editApellido;
    private boolean isValid = true;

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

        isValid = false; // Se activan las validaciones
        checkFields(); // Validar los campos

        if (!register.isEnabled()) {
            return;
        }

        // Lógica para enviar el código de verificación con Retrofit
        String fullName = nombreR + " " + apellidoR;
        AuthApiService apiService = ApiClient.getClient().create(AuthApiService.class);
        SignUpRequest request = new SignUpRequest(emailR, fullName, contraseñaR, confirmContraR);
        Call<Void> call = apiService.signUp(request);

        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(requireContext(), "Error en el registro", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_LONG).show();
                t.printStackTrace();
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