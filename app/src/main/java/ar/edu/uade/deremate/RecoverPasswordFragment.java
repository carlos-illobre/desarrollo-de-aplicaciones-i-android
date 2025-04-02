package ar.edu.uade.deremate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecoverPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecoverPasswordFragment extends Fragment {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private Button recoverButton;
    private Button cancelButton;

    public RecoverPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecoverPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecoverPasswordFragment newInstance() {
        RecoverPasswordFragment fragment = new RecoverPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
            Toast.makeText(getActivity(), getString(R.string.recover_password_message), Toast.LENGTH_LONG).show();
            requireActivity().getSupportFragmentManager().popBackStack();
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
}
