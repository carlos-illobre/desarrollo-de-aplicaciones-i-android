package ar.edu.uade.deremate.data.repository.auth;

import androidx.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ar.edu.uade.deremate.data.api.AuthService;
import ar.edu.uade.deremate.data.api.model.ConfirmSignupRequest;
import ar.edu.uade.deremate.data.api.model.LoginRequest;
import ar.edu.uade.deremate.data.api.model.LoginResponse;
import ar.edu.uade.deremate.data.api.model.RecoverPasswordRequest;
import ar.edu.uade.deremate.data.api.model.SignUpRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AuthRetrofitRepository implements AuthRepository {

    private final AuthService authService;

    @Inject
    public AuthRetrofitRepository(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void login(String email, String password, AuthServiceCallback<LoginResponse> callback) {
        authService.signin(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(new LoginResponse(response.body().getAccessToken()));
                } else {
                    callback.onError(new Exception("There was an error with your login, please try again"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });

    }

    @Override
    public void confirmSignup(String signupCode, AuthServiceCallback<Void> callback) {
        authService.confirmSignup(new ConfirmSignupRequest(signupCode)).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(new Exception(String.format("API error: %s - %s ",
                            response.code(), response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void recoverPassword(RecoverPasswordRequest request, AuthServiceCallback<Void> callback) {
        authService.recoverPassword(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(new Exception(String.format("API error: %s - %s ",
                            response.code(), response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void register(SignUpRequest request, AuthServiceCallback<Void> callback) {
        authService.signUp(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(new Exception(String.format("API error: %s - %s ",
                            response.code(), response.message())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }
}
