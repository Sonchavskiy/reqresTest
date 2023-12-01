package main;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.StringReader;
import java.lang.reflect.Type;


public class ResponseData {
    public final int code;
    public String text;
    public Object obj;


    public ResponseData(int code) {
        this.code = code;
        this.text = null;
        this.obj = null;
    }

    public void setData(String text, Type dataClass) {
        this.text = text;
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(text));
        reader.setLenient(true);
        this.obj = gson.fromJson(reader, dataClass);
    }

    public boolean isGood(){
        return this.code == 200 || this.code == 201 || this.code == 204;
    }

    public boolean hasContent(){
        return this.code != 204;
    }

    @Override
    public String toString() {
        return (this.obj != null) ? this.obj.toString() : "<No content>";
    }
}
