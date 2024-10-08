
package com.example.mybackendsystem.view;

import com.example.mybackendsystem.model.Role;
import com.example.mybackendsystem.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static com.example.mybackendsystem.util.DataStore.loadListFromFile;
import static com.example.mybackendsystem.util.DataStore.*;
import static com.example.mybackendsystem.util.MD5Util.encrypt;

public class UserForm1 extends JFrame {
    public JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    public static final String DB_DIR = "Manage_System/src/main/resources/db";

    //Path path = Paths.get(DB_DIR, "user.json");
    //String content = new String(Files.readAllBytes(path));
    //Gson gson = new Gson();
    //List<User> users = gson.fromJson(content, new TypeToken<List<User>>() {}.getType());

    public UserForm1() throws IOException {
        setTitle("用户管理");
        setSize(700, 400);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addUserButton = new JButton("添加用户");
        JButton deleteUserButton = new JButton("删除用户");
        JButton changeUserButton = new JButton("修改用户");
        JButton updateUserButton = new JButton("刷新列表");
        JButton viewUserButton = new JButton("查看用户");
        JButton bindRoleUserButton = new JButton("绑定/解绑角色");

        buttonPanel.add(addUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(changeUserButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(viewUserButton);
        buttonPanel.add(bindRoleUserButton);

        // 给添加用户按钮，添加事件监视器
        bindRoleUserButton.addActionListener(e -> {bindRoleUser();});

        addUserButton.addActionListener(e -> {
            try {
                addUser();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        deleteUserButton.addActionListener(e -> {
                deleteUser();

        });

        changeUserButton.addActionListener(e -> {ChangeUser();});

        updateUserButton.addActionListener(e -> {
                updateFileList();

        });

        viewUserButton.addActionListener(e -> {viewUser();});

        // 创建文件列表面板
        JPanel fileListPanel = new JPanel();
        fileListPanel.setLayout(new BorderLayout());

        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(fileList);
        fileListPanel.add(scrollPane, BorderLayout.CENTER);

        // 将按钮面板和文件列表面板添加到主面板
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(fileListPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
        updateFileList();
    }

    private void bindRoleUser() {
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }


    }

    private void viewUser() {
//        int selectedIndex = fileList.getSelectedIndex();
//
//        if (selectedIndex == -1) {
//            JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }
//
//        String selectedItem = fileListModel.getElementAt(selectedIndex);
//        String userId = selectedItem.substring(selectedItem.indexOf("(") + 1, selectedItem.indexOf(")"));
//
//        Path path = Paths.get(DB_DIR, "user_" + userId + ".json");
//        try {
//            String content = new String(Files.readAllBytes(path));
//            Gson gson = new Gson();
//            User user = gson.fromJson(content, User.class);
//
//            JOptionPane.showMessageDialog(this, "用户名: " + user.getName() + "\n邮箱: " + user.getEmail(), "用户信息", JOptionPane.INFORMATION_MESSAGE);
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "读取文件失败", "错误", JOptionPane.ERROR_MESSAGE);
//        }


    }

    private void ChangeUser() {
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }


    }

    private void addUser() throws IOException {
        // 弹出对话框让用户输入信息
        String Id = JOptionPane.showInputDialog(this,"请输入ID");
        String account = JOptionPane.showInputDialog(this, "请输入账号:");
        String sex = JOptionPane.showInputDialog(this, "请输入性别:");
        String ageStr = JOptionPane.showInputDialog(this, "请输入年龄:");
        String password = JOptionPane.showInputDialog(this, "请输入密码:");
        if(Id==null || account == null ||sex == null || ageStr == null || password == null){
            JOptionPane.showMessageDialog(this,"输入不能为空","错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int age = Integer.parseInt(ageStr);
            User user = new User();
            user.setId(Id);
            user.setAccount(account);
            user.setSex(sex);
            user.setAge(age);
            user.setPassword(encrypt(password));
            user.setCreateTime(new Date());
            user.setModifyTime((Date) new Date());

            //数据写入json文件
            saveObjectToFile1(user,"user",DB_DIR);

            JOptionPane.showMessageDialog(this,"用户添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"年龄必须是数字","错误",JOptionPane.ERROR_MESSAGE);
        }
        updateFileList();
    }

    private void updateFileList(){

        try {
            Path path = Paths.get(DB_DIR, "user.json");
            String content = new String(Files.readAllBytes(path));
            Gson gson = new Gson();
            List<User> users = gson.fromJson(content, new TypeToken<List<User>>() {}.getType());
            fileListModel.clear();
            for (User user : users) {
                fileListModel.addElement(user.getAccount());
            }
        } catch (IOException e) {
            // 处理文件读取异常
            e.printStackTrace();
            // 可以在这里记录日志或显示错误信息
            System.err.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            // 处理其他异常
            e.printStackTrace();
            // 可以在这里记录日志或显示错误信息
            System.err.println("Error updating file list: " + e.getMessage());
        }
    }

    private void deleteUser(){
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {Path path = Paths.get(DB_DIR, "user.json");
            String content = new String(Files.readAllBytes(path));
            Gson gson = new Gson();
            List<User> users = gson.fromJson(content, new TypeToken<List<User>>() {
            }.getType());

            users.remove(selectedIndex);
            FileWriter writer = new FileWriter(Paths.get(DB_DIR, "user.json").toFile());


        //System.out.println(users);



            writer.write(gson.toJson(users));
            JOptionPane.showMessageDialog(this, "用户删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "删除用户失败", "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        updateFileList();


    }

    public static void main(String[] args) throws IOException {
        new UserForm1().setLocation(850, 400);
    }
}