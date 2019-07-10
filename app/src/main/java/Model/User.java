package Model;

public class User {
    private String userid, firstname, lastname, password, email, phone, address, gender, blood_group, date_of_birth, imagename;

    public User(String userid, String firstname, String lastname, String password, String email, String phone, String address, String gender, String blood_group, String date_of_birth, String imagename) {
        this.userid=userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.blood_group = blood_group;
        this.date_of_birth = date_of_birth;
        this.imagename=imagename;
    }

    public User(String phone, String password){
        this.phone = phone;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }
}

