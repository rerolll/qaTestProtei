package qa.pages.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class TakeJsonData {
    public static String take_json_data(String data){
        try {
            FileReader reader = new FileReader("config.json");
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            return jsonObject.get(data).getAsString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        } catch (JsonParseException e) {
            throw new RuntimeException("Json parsing error", e);
        }
    }
}