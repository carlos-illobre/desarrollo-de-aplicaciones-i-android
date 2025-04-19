package ar.edu.uade.deremate.fragment;

import android.os.Bundle;

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
import ar.edu.uade.deremate.data.repository.auth.AuthRepository;
import ar.edu.uade.deremate.data.repository.auth.AuthServiceCallback;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignupCodeFragment extends Fragment {

    private EditText signupCodeInput;
    private Button cancelButton;
    private Button acceptButton;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_code_input, container, false);

        signupCodeInput = view.findViewById(R.id.signUpCodeInput);
        signupCodeInput.requestFocus();
        acceptButton = view.findViewById(R.id.acceptSignUpCodeInput);
        cancelButton = view.findViewById(R.id.cancelSignUpCodeInput);

        acceptButton.setEnabled(false);

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
                validateInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        signupCodeInput.addTextChangedListener(textWatcher);

        cancelButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        acceptButton.setOnClickListener(v -> {
            Log.d("SignupCodeFragment", "Accept button clicked");
            Toast.makeText(getActivity(), "Accept button clicked", Toast.LENGTH_SHORT).show();

            String signupCode = signupCodeInput.getText().toString();
            confirmSignup(signupCode);
        });
    }

    private void confirmSignup(String signupCode) {
        authRepository.confirmSignup(signupCode, new AuthServiceCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                Toast.makeText(getActivity(), "Signup confirmed successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable error) {
                Log.e("SignupCodeFragment", "API call failed",error);
                Toast.makeText(getActivity(), "Failed to confirm signup", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validateInput() {
        String signupCode = signupCodeInput.getText().toString();
        boolean isValidCode = signupCode.matches("\\d{6}");

        acceptButton.setEnabled(isValidCode);
    }
}
