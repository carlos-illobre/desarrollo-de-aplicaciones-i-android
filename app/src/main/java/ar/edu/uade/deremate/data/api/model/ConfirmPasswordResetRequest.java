package ar.edu.uade.deremate.data.api.model;

public class ConfirmPasswordResetRequest {
    private final String otp;
    public ConfirmPasswordResetRequest(String otp){
        this.otp = otp;
    }
}