package Model;

public class LoginResponse {
    boolean status;
    String accessToken;


public LoginResponse(boolean status, String accessToken) {
        this.status = status;
        this.accessToken = accessToken;
        }


public boolean isStatus() {
        return status;
        }

public void setStatus(boolean status) {
        this.status = status;
        }

public String getAccessToken() {
        return accessToken;
        }

public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        }

        }
