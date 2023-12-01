package main;

import data.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReqresAPI {
    private static final String commonUrl = "https://reqres.in/api/";

    private static String url(String queryUrl){

        return commonUrl + queryUrl;
    }

    public static ResponseData getUser(Object id) throws IOException {
        return ReadHTTP.request(url("users/" + id), "GET", null, User.class);
    }

    public static ResponseData getUsers(Object page) throws IOException {
        return ReadHTTP.request(url("users?page=" + page), "GET", null, Users.class);
    }

    public static ResponseData getResource(Object id) throws IOException {
        return ReadHTTP.request(url("unknown/" + id), "GET", null, Resource.class);
    }

    public static ResponseData getResources(Object page) throws IOException {
        return ReadHTTP.request(url("unknown?page=" + page), "GET", null, Resources.class);
    }

    public static ResponseData createUser(String name, String job) throws IOException {
        Map<String,String> arguments = new HashMap<String,String>();
        if (name!=null) arguments.put("name", name);
        if (job!=null) arguments.put("job", job);
        return ReadHTTP.request(url("users"), "POST", arguments, UserCreated.class);
    }

    public static ResponseData updateUser(Object id, String name, String job) throws IOException {
        Map<String,String> arguments = new HashMap<String,String>();
        if (name!=null) arguments.put("name", name);
        if (job!=null) arguments.put("job", job);
        return ReadHTTP.request(url("users/" + id), "PUT", arguments, UserUpdated.class);
    }

    public static ResponseData deleteUser(Object id) throws IOException {
        return ReadHTTP.request(url("users/" + id), "DELETE", null, null);
    }

    public static ResponseData register( String email, String password) throws IOException {
        Map<String,String> arguments = new HashMap<String,String>();
        if (email!=null) arguments.put("email", email);
        if (password!=null) arguments.put("password", password);
        return ReadHTTP.request(url("register"), "POST", arguments, Registered.class);
    }

    public static ResponseData login( String email, String password) throws IOException {
        Map<String,String> arguments = new HashMap<String,String>();
        if (email!=null) arguments.put("email", email);
        if (password!=null) arguments.put("password", password);
        return ReadHTTP.request(url("login"), "POST", arguments, LoggedIn.class);
    }
}
