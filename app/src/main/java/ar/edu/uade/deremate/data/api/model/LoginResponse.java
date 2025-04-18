package ar.edu.uade.deremate.data.api.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
