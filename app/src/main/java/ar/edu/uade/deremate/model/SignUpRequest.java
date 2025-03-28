package ar.edu.uade.deremate.model;

public class SignUpRequest {
    private String email;
    private String password;
    private String passwordConfirmation;

    public SignUpRequest(String email, String password, String passwordConfirmation){
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }
}
