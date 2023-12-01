package data;

public class Support {
    public String url;
    public String text;

    public Support() {
        this.url = "https://reqres.in/#support-heading";
        this.text = "To keep ReqRes free, contributions towards server costs are appreciated!";
    }

    @Override
    public String toString() {
        return "Support: " +
                "url='" + url + '\'' +
                ", text='" + text + '\'';
    }
}
