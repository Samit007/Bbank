package Model;

public class UpdateEResponse {
    String message;
    boolean status;

    public UpdateEResponse(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UpdateEResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
