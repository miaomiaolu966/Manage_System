package com.example.mybackendsystem;

import static com.example.mybackendsystem.view.UserForm.DB_DIR;
import com.example.mybackendsystem.model.User;
import com.example.mybackendsystem.util.DataStore;
import com.example.mybackendsystem.view.LoginFrame;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        //System.out.println("Hello world!");
        //User user = new User();
        //user.setAccount("Robin");
        //user.setAge(30);
        //user.setPassword("123456");
        //user.setId("45697");

        //System.out.println(user.getPassword());

        //DataStore store_zs = new DataStore();

        //store_zs.saveObjectToFile(user,user.getAccount(),DB_DIR);

        new LoginFrame().setLocation(850,400);
    }
}