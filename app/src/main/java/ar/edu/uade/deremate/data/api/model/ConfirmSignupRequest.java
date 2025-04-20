package ar.edu.uade.deremate.data.api.model;

public class ConfirmSignupRequest {
    private final String otp;

    public ConfirmSignupRequest(String otp){
        this.otp = otp;
    }
}