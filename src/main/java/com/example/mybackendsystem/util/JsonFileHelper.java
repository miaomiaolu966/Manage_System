package com.example.mybackendsystem.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFileHelper {

    public static void saveObjectToJsonFile(Object obj, String fileName, String DB_DIR) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        // 检查文件是否存在
        File file = new File(DB_DIR + File.separator + fileName + ".json");
        if (!file.exists()) {
            // 如果文件不存在，创建一个新的空数组
            List<Object> emptyList = new ArrayList<>();
            saveListToFile(emptyList, DB_DIR, fileName);
        }

        // 读取现有文件内容
        List<Object> existingList = readListFromFile(DB_DIR, fileName);

        // 添加新元素
        existingList.add(obj);

        // 保存更新后的数组
        saveListToFile(existingList, DB_DIR, fileName);
    }

    private static void saveListToFile(List<Object> list, String DB_DIR, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(list);

        try (FileWriter writer = new FileWriter(DB_DIR + File.separator + fileName + ".json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Object> readListFromFile(String DB_DIR, String fileName) {
        Gson gson = new Gson();
        File file = new File(DB_DIR + File.separator + fileName + ".json");

        try (FileReader reader = new FileReader(file)) {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.beginArray();
            List<Object> list = new ArrayList<>();

            while (jsonReader.hasNext()) {
                list.add(gson.fromJson(jsonReader, Object.class));
            }

            jsonReader.endArray();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        // 示例用法
        String DB_DIR = "data";
        String fileName = "users";

        // 创建一个示例对象
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1);
        user.put("name", "张三");
        user.put("age", 25);

        saveObjectToJsonFile(user, fileName, DB_DIR);
    }
}
