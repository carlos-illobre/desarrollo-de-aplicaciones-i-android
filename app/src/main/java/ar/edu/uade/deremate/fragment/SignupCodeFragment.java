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
        View view = inflater.inflate(R.layout.fragment_signup_code_input, container, false);

        codeDigit1 = view.findViewById(R.id.code_digit_1);
        codeDigit2 = view.findViewById(R.id.code_digit_2);
        codeDigit3 = view.findViewById(R.id.code_digit_3);
        codeDigit4 = view.findViewById(R.id.code_digit_4);
        codeDigit5 = view.findViewById(R.id.code_digit_5);
        codeDigit6 = view.findViewById(R.id.code_digit_6);
        acceptButton = view.findViewById(R.id.accept_button);
        cancelButton = view.findViewById(R.id.cancel_button);


        acceptButton.setEnabled(false);

        setupListeners();

        cancelButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        acceptButton.setOnClickListener(v -> {
            String signupCode = getSignupCode();
            confirmSignup(signupCode);
        });

        return view;
    }

    private void confirmSignup(String signupCode) {
        authRepository.confirmSignup(signupCode, new AuthServiceCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                Toast.makeText(getActivity(), "Signup confirmed successfully", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(SignupCodeFragment.this).navigate(R.id.action_signUpCodeFragmentToLoginFragment);
            }

            @Override
            public void onError(Throwable error) {
                Log.e("SignupCodeFragment", "API call failed", error);
                Toast.makeText(getActivity(), "Failed to confirm signup, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

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

        codeDigit1.addTextChangedListener(textWatcher);
        codeDigit2.addTextChangedListener(textWatcher);
        codeDigit3.addTextChangedListener(textWatcher);
        codeDigit4.addTextChangedListener(textWatcher);
        codeDigit5.addTextChangedListener(textWatcher);
        codeDigit6.addTextChangedListener(textWatcher);
    }

    private void validateInput() {
        String signupCode = getSignupCode();
        boolean isValidCode = signupCode.matches("\\d{6}");

        acceptButton.setEnabled(isValidCode);
    }

    private String getSignupCode() {
        return codeDigit1.getText().toString() +
                codeDigit2.getText().toString() +
                codeDigit3.getText().toString() +
                codeDigit4.getText().toString() +
                codeDigit5.getText().toString() +
                codeDigit6.getText().toString();
    }
}
