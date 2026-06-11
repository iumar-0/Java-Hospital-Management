package Hospital;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;

public class FileUtility {
    private static final Gson gson = new GsonBuilder()

            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    // Method to load data from a JSON file, taking a Type parameter
    public static <K, V> HashMap<K, V> loadFromFile(String filename, Type type) {
        try (Reader reader = new FileReader(filename)) {
            return gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {

            return new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    // Method to save a HashMap to a JSON file
    public static <K, V> void saveToFile(String filename, HashMap<K, V> p0) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(p0, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
