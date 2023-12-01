package data;

import java.util.Date;

public class UserUpdated {
    public String name;
    public String job;
    public Date updatedAt;

    @Override
    public String toString() {
        return "UserUpdated: "
                + ((name!=null)? "name='" + name + "', " : "")
                + ((job!=null)? "job='" + job + "', " : "")
                + "updatedAt='" + updatedAt + "'";
    }
}
