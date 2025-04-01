package ar.edu.uade.deremate.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import ar.edu.uade.deremate.R;
import ar.edu.uade.deremate.model.ConfirmSignupRequest;
import ar.edu.uade.deremate.service.AuthService;
import ar.edu.uade.deremate.service.AuthServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupCodeFragment extends Fragment {

    @Inject
    private AuthServiceClient authServiceClient;
    private TextView signupCodeTitle;
    private EditText signupCodeInput;
    private Button cancelButton;
    private Button acceptButton;

    private AuthService authService;

    private TextView messageText;

    public SignupCodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecoverPasswordFragment.
     */
    public static SignupCodeFragment newInstance() {
        SignupCodeFragment fragment = new SignupCodeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        authService = AuthServiceClient.getClient().create(AuthService.class);
        super.onCreate(savedInstanceState);
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
        //messageText = view.findViewById(R.id.recoverPassword_messageText);

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
                validateInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        signupCodeInput.addTextChangedListener(textWatcher);

        cancelButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        acceptButton.setOnClickListener(v -> {
            Log.d("SignupCodeFragment", "Accept button clicked");
            Toast.makeText(getActivity(), "Accept button clicked", Toast.LENGTH_SHORT).show();

            String signupCode = signupCodeInput.getText().toString();
            ConfirmSignupRequest request = new ConfirmSignupRequest(signupCode);
            Call<Void> call = this.authService.confirmSignup(request);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Validación exitosa! Tu usuario fue creado", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Validation failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("SignupCodeFragment", "API call failed", t);
                    Toast.makeText(getActivity(), "API call failed", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void validateInputs() {
        String signupCode = signupCodeInput.getText().toString();
        boolean isValidCode = signupCode.matches("\\d{6}");

        acceptButton.setEnabled(isValidCode);
    }
}
