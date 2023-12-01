package data;

import java.util.Arrays;

public class Users {
    public int page;
    public int per_page;
    public int total;
    public int total_pages;
    public UserData[] data;
    public Support support;

    public Users(int page, int per_page, int total, int total_pages, UserData[] data) {
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
        this.data = data;
        this.support = new Support();
    }

    @Override
    public String toString() {
        StringBuilder data_str = new StringBuilder("Users: " +
                "page=" + page +
                ", per_page=" + per_page +
                ", total=" + total +
                ", total_pages=" + total_pages);
        if (data.length > 0) {
            for (UserData user : data) {
                data_str.append("\n  ").append(user);
            }
        } else {data_str.append("\n  <empty list>"); }
        data_str.append("\n  ").append(support);
        return data_str.toString();
    }
}
