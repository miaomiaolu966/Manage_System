package com.example.mybackendsystem.util;

import com.example.mybackendsystem.model.SerializableObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    public static final String DB_DIR = "Manage_System/src/main/resources/db";

    public static void saveObjectToFile(Object obj, String fileName ,String DB_DIR) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        //Type listType = new TypeToken<List<Object>>() {}.getType();
        //List<Object> objectList = gson.fromJson(json,listType);
        try (FileWriter writer = new FileWriter(DB_DIR + File.separator + fileName+".json")) {

            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T loadObjectFromFile(String fileName, Class<T> clazz,String DB_DIR) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Reader reader = Files.newBufferedReader(Paths.get(DB_DIR + "/"+ fileName))) {
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> loadListFromFile(String fileName, Class<T> clazz,String DB_DIR) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Reader reader = Files.newBufferedReader(Paths.get(DB_DIR + File.separator + fileName))) {
            return gson.fromJson(reader, new TypeToken<List<T>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveObjectToFile(SerializableObject obj) {//saveObjectToFile方法重载
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        String fileName = obj.getIdentifier() + ".json";
        String dbDir = obj.getDBDir();
        String filePath = dbDir + File.separator + fileName;

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveObjectToFile1(Object obj, String fileName, String DB_DIR) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);

        Path filePath = Paths.get(DB_DIR, fileName + ".json");

        try {
            // 检查文件是否存在
            if (Files.exists(filePath)) {
                // 读取现有文件内容
                String fileContent = new String(Files.readAllBytes(filePath));
                List<Object> objectList = gson.fromJson(fileContent, new TypeToken<List<Object>>() {}.getType());
                if (objectList == null) {
                    objectList = new ArrayList<>();
                }
                objectList.add(obj);
                // 将更新后的列表写回文件
                String updatedJson = gson.toJson(objectList);
                try (FileWriter writer = new FileWriter(filePath.toFile())) {
                    writer.write(updatedJson);
                }
            } else {
                // 文件不存在，创建新文件并写入数据
                List<Object> objectList = new ArrayList<>();
                objectList.add(obj);
                String jsonList = gson.toJson(objectList);
                try (FileWriter writer = new FileWriter(filePath.toFile())) {
                    writer.write(jsonList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
