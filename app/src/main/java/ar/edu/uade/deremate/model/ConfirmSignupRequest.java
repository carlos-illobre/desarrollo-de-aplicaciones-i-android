package ar.edu.uade.deremate.model;

public class ConfirmSignupRequest {
    private String otp;

    public ConfirmSignupRequest(String otp){
        this.otp = otp;
    }
}