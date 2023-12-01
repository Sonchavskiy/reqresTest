package data;

public class User {

    public UserData data;
    public Support support;

    public User(int id, String email, String first_name, String last_name, String avatar) {
        this.data = new UserData(id, email, first_name, last_name, avatar);
        this.support = new Support();
    }

    @Override
    public String toString() {
        return "User: " +
                data +
                ",\n  " + support;
    }
}

