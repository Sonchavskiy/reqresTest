package data;

public class Error {
    public String error;

    @Override
    public String toString() {
        return ("Error: " + ((error!=null)? error : "<No content>"));
    }
}