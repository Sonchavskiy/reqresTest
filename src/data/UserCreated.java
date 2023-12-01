package data;

import java.util.Date;

public class UserCreated {
    public String name;
    public String job;
    public String id;
    public Date createdAt;

    @Override
    public String toString() {
        return "UserCreated: "
                + ((name!=null)? "name='" + name + "', " : "")
                + ((job!=null)? "job='" + job + "', " : "")
                + "id='" + id + "', createdAt='" + createdAt + "'";
    }
}
