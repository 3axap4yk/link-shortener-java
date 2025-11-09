package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Хранилище пользователей и их ссылок — сохраняется в JSON
public class StorageService {
    private static final String FILE = "links.json";

    // Включаем красивое форматирование JSON
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Загрузка данных при старте
    public Map<UUID, User> load() {
        try (FileReader reader = new FileReader(FILE)) {
            Type type = new TypeToken<Map<UUID, User>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            return new HashMap<>(); // Если файла нет — создаём пустое хранилище
        }
    }

    // Сохранение при выходе
    public void save(Map<UUID, User> data) {
        try (FileWriter writer = new FileWriter(FILE)) {
            gson.toJson(data, writer);
        } catch (Exception e) {
            System.out.println("Ошибка сохранения данных.");
        }
    }
}
