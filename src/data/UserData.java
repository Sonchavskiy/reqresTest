package data;

public class UserData {
    public int id;
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;

    public UserData(int id, String email, String first_name, String last_name, String avatar) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", avatar='" + avatar + '\'';
    }
}
