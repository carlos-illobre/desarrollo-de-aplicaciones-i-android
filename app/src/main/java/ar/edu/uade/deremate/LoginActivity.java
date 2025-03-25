package ar.edu.uade.deremate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerButton, forgotPasswordButton, messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        messageText = findViewById(R.id.messageText);

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

        if (password.equals("incorrecta")) { // Simulación de validación
            Toast.makeText(this, "Clave incorrecta", Toast.LENGTH_SHORT).show();
        } else {
            // ✅ Guardar estado de sesión
            SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            // 🔄 Ir a MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Cierra LoginActivity
        }
    }


    private void navigateToRegister() {
        // Navegar a la pantalla de registro
        Toast.makeText(this, "Ir a Registro", Toast.LENGTH_SHORT).show();
    }

    private void navigateToForgotPassword() {
        // Navegar a la pantalla de recuperación de contraseña
        Toast.makeText(this, "Ir a Recuperación de Clave", Toast.LENGTH_SHORT).show();
    }

    private void checkQueryStringMessage() {
        // Simulación de captura de mensaje desde un Intent o query string
        String message = getIntent().getStringExtra("message");
        if (message != null) {
            messageText.setText(message);
            messageText.setVisibility(View.VISIBLE);
        }
    }
}
