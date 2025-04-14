package ar.edu.uade.deremate.data.api.model;

public class ConfirmSignupRequest {
    private final String signupCode;

    public ConfirmSignupRequest(String signupCode){
        this.signupCode = signupCode;
    }
}