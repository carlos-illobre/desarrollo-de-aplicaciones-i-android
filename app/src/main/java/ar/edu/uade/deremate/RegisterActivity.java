package ar.edu.uade.deremate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ar.edu.uade.deremate.model.SignUpRequest;
import ar.edu.uade.deremate.service.AuthApiService;
import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private Button cancelar;
    private EditText editEmail;
    private EditText editContraseña;
    private EditText editConfirmContra;
    private boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        editEmail = findViewById(R.id.editTextEmailRegister);
        editContraseña = findViewById(R.id.editTextPasswordRegister);
        editConfirmContra = findViewById(R.id.editTextPasswordRegister2);
        register = findViewById(R.id.buttonRegister);
        cancelar = findViewById(R.id.buttonCancelRegister);
        register.setEnabled(true);
        addTextWatchers();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser() {
        String emailR = editEmail.getText().toString().trim();
        String contraseñaR = editContraseña.getText().toString().trim();
        String confirmContraR = editConfirmContra.getText().toString().trim();

        isValid = true; // Se activan las validaciones
        checkFields(); // Validar los campos

        if (!register.isEnabled()) {
            return;
        }

        //Logica de para enviar el codigo de verificacion con Retrofit
        AuthApiService apiService = ApiClient.getClient().create(AuthApiService.class);
        SignUpRequest request = new SignUpRequest(emailR, contraseñaR, confirmContraR);
        Call<Void> call = apiService.signUp(request);

        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, "Error en el registro", Toast.LENGTH_LONG).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
                });
                t.printStackTrace();
            }
        });
    }

    private void addTextWatchers() {
        editEmail.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void afterTextChanged(android.text.Editable s) {
                if (isValid) checkFields(); // Solo valida si ya se intentó registrar
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editContraseña.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void afterTextChanged(android.text.Editable s) {
                if (isValid) checkFields();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editConfirmContra.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void afterTextChanged(android.text.Editable s) {
                if (isValid) checkFields();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void checkFields() {
        String emailR = editEmail.getText().toString().trim();
        String contraseñaR = editContraseña.getText().toString().trim();
        String confirmContraR = editConfirmContra.getText().toString().trim();

        boolean isEmailValid = true;
        boolean isPasswordValid = true;
        boolean isConfirmPasswordValid = true;

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
        } else {
            editConfirmContra.setError(null);
        }

        // Habilitar o deshabilitar el botón según la validación
        register.setEnabled(isEmailValid && isPasswordValid && isConfirmPasswordValid);
    }
}