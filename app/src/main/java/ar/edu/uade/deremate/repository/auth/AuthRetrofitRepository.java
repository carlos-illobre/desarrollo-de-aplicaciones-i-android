package ar.edu.uade.deremate.repository.auth;

import androidx.annotation.NonNull;

import ar.edu.uade.deremate.data.api.AuthService;
import ar.edu.uade.deremate.data.api.model.ConfirmSignupRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRetrofitRepository implements AuthRepository{

    private final AuthService authService;

    public AuthRetrofitRepository(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void confirmSignup(String signupCode, AuthServiceCallback callback) {
        authService.confirmSignup(new ConfirmSignupRequest(signupCode)).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });

    }
}
