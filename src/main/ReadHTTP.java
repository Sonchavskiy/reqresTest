package main;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.nio.charset.*;
import java.util.Map;
import java.util.StringJoiner;
import data.Error;

public class ReadHTTP {
    public static String green = "\033[1;32m";
    public static String red = "\033[1;31m";
    public static String reset = "\033[0m";
    public static ResponseData request(String link, String method, Map<String,String> arguments, Type dataClass) {
        try {
            System.out.println("Sending " + method + " request: " + link + ((arguments != null) ? " " + arguments : ""));
            HttpURLConnection connection = connect(link, method);
            if (!method.equals("GET") && arguments != null) sendArguments(connection, arguments);
            return readResponse(connection, dataClass);
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return null;
        }
    }

    private static HttpURLConnection connect(String link, String method) throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = ((HttpURLConnection)url.openConnection());
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");
        connection.setRequestMethod(method);
        return connection;
    }

    private static void sendArguments(HttpURLConnection connection, Map<String,String> arguments) throws IOException {
        connection.setDoOutput(true);
        byte[] out = encodeArguments(arguments);
        connection.setFixedLengthStreamingMode(out.length);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        connection.connect();
        try (OutputStream os = connection.getOutputStream()) {
            os.write(out);
        }
    }

    private static ResponseData readResponse(HttpURLConnection connection, Type dataClass) throws IOException {
        ResponseData response = new ResponseData(connection.getResponseCode());
        InputStream input;
        String color;
        if (response.isGood()) {
            input = connection.getInputStream();
            color = green;
        } else {
            input = connection.getErrorStream();
            color = red;
            dataClass = Error.class;
        }
        if (response.hasContent()) response.setData(readStream(input), dataClass);
        System.out.println("Response code from server: " + color + response.code + reset);
        System.out.println(response + "\n");
        return response;
    }

    private static String readStream(InputStream input) throws IOException {
        BufferedReader re = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
        StringBuilder str = new StringBuilder(); // To Store Url Data In String.
        int temp;
        do {
            temp = re.read(); // reading character by character.
            str.append((char) temp);
        } while (temp != -1); //  re.read() return -1 when there is end of buffer , data or end of file.
        return str.toString();
    }

    private static byte[] encodeArguments(Map<String,String> arguments) throws UnsupportedEncodingException {
        StringJoiner sj = new StringJoiner("&");
        for(Map.Entry<String,String> entry : arguments.entrySet())
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        return sj.toString().getBytes(StandardCharsets.UTF_8);
    }
}
