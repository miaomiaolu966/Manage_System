
package com.example.mybackendsystem.util;

import com.example.mybackendsystem.model.SerializableObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DataStore1 {

    private static final String DB_DIR = "Manage_System/src/main/resources/db";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void saveObjectToFile(Object obj, String fileName) {
        String json = GSON.toJson(obj);
        try (FileWriter writer = new FileWriter(DB_DIR + File.separator + fileName + ".json")) {
            writer.write(json);
        } catch (IOException e) {
            System.err.println("保存对象到文件失败: " + e.getMessage());
        }
    }

    public static <T> T loadObjectFromFile(String fileName, Class<T> clazz) {
        try (Reader reader = Files.newBufferedReader(Paths.get(DB_DIR + File.separator + fileName + ".json"))) {
            return GSON.fromJson(reader, clazz);
        } catch (IOException e) {
            System.err.println("从文件加载对象失败: " + e.getMessage());
        }
        return null;
    }

    public static <T> List<T> loadListFromFile(String fileName, Class<T> clazz) {
        try (Reader reader = Files.newBufferedReader(Paths.get(DB_DIR + File.separator + fileName + ".json"))) {
            Type listType = new TypeToken<List<T>>(){}.getType();
            return GSON.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("从文件加载列表失败: " + e.getMessage());
        }
        return null;
    }

    public static void saveObjectToFile(SerializableObject obj) {
        String json = GSON.toJson(obj);
        String fileName = obj.getIdentifier() + ".json";
        String dbDir = obj.getDBDir();
        String filePath = dbDir + File.separator + fileName;

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            System.err.println("保存对象到文件失败: " + e.getMessage());
        }
    }

}