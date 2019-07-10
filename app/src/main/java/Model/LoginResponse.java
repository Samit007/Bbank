package Model;

public class LoginResponse {
        boolean status;
        String accessToken;
        String userid;

        public LoginResponse(String userid) {
                this.userid = userid;
        }

        public LoginResponse(boolean status, String accessToken) {
                this.status = status;
                this.accessToken = accessToken;
        }

        public String getUserid() {
                return userid;
        }

        public void setUserid(String userid) {
                this.userid = userid;
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