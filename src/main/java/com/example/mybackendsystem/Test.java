package com.example.mybackendsystem;

import com.example.mybackendsystem.model.Role;
import com.example.mybackendsystem.model.SerializableObject;
import com.example.mybackendsystem.model.User;
import com.example.mybackendsystem.view.UserForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.example.mybackendsystem.util.DataStore;

import static com.example.mybackendsystem.util.DataStore.*;
import static com.example.mybackendsystem.util.DataStore.DB_DIR;
import static com.example.mybackendsystem.view.UserForm.*;
public class Test {
    //public static final String DB_DIR = "define_3class/src/main/resources/db";
    private static final String DB_DIR = "define_3class/src/main/resources/db";
    private static final String User_DIR = "define_3class/src/main/resources/db/User";


//    public static void saveObjectToFile(Object obj, String fileName , String DB_DIR) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(obj);
//        if (!Files.exists(Paths.get(DB_DIR))){
//            try {
//                Files.createDirectory(Paths.get(DB_DIR));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        //Type listType = new TypeToken<List<Object>>() {}.getType();
//        //List<Object> objectList = gson.fromJson(json,listType);
//        try (FileWriter writer = new FileWriter(DB_DIR + File.separator + fileName+".json")) {
//
//            writer.write(json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        User user = loadObjectFromFile("李四.json",User.class, UserForm.DB_DIR);
        List<User> user_list =  DataStore.loadListFromFile("user.json",User.class, DB_DIR);
        user_list.add(user);
        Gson gson = new  GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(user_list);

        System.out.println(json);
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();

        }
    }

