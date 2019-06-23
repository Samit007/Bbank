package Model;

public class Admin {
    private String AdminUsername,AdminPassword;

    public Admin(String adminUsername, String adminPassword) {
        AdminUsername = adminUsername;
        AdminPassword = adminPassword;
    }

    public String getAdminUsername() {
        return AdminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        AdminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return AdminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        AdminPassword = adminPassword;
    }
}
