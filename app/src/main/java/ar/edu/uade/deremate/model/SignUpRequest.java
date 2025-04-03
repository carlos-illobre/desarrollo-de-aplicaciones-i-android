package ar.edu.uade.deremate.model;

public class SignUpRequest {
    private String email;
    private String fullName;
    private String password;
    private String passwordConfirmation;

    public SignUpRequest(String email, String fullName, String password, String passwordConfirmation){
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }
}
