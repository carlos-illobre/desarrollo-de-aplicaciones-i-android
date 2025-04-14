package ar.edu.uade.deremate.data.api.model;

public class RecoverPasswordRequest {
    private String email;
    private String password;
    private String passwordConfirmation;

    public String getEmail() {
        return email;
    }

    public RecoverPasswordRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RecoverPasswordRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public RecoverPasswordRequest setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
        return this;
    }
}
