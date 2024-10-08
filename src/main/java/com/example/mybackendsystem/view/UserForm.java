package com.example.mybackendsystem.view;

import com.example.mybackendsystem.model.Role;
import com.example.mybackendsystem.model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;


import static com.example.mybackendsystem.util.DataStore.saveObjectToFile;
import static com.example.mybackendsystem.util.DataStore.*;
import static com.example.mybackendsystem.util.MD5Util.encrypt;

public class UserForm extends JFrame {
    public JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    public static final String DB_DIR = "src/main/resources/db/User";
    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JList<String> rolesList;
    private DefaultListModel<String> rolesModel;

    public UserForm(){
        setTitle("用户管理");
        setSize(700,400);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(){{setLayout(new BorderLayout());}};


        JPanel buttonPanel = new JPanel(){{setLayout(new FlowLayout());}};
        //buttonPanel.setLayout(new FlowLayout());

        JButton addUserButton = new JButton("添加用户");
        JButton deleteUserButton = new JButton("删除用户");
        JButton changeUserButton = new JButton("修改用户");
        JButton updateUserButton = new JButton("刷新列表");
        JButton viewUserButton = new JButton("查看用户");
        JButton bindRoleUserButton =new JButton("绑定/解绑角色");

        buttonPanel.add(addUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(changeUserButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(viewUserButton);
        buttonPanel.add(bindRoleUserButton);

        //给添加用户按钮，添加事件监视器
        bindRoleUserButton.addActionListener(e -> {
            bindRoleUser();
        });
        addUserButton.addActionListener(e -> {
            addUser();
        });

        deleteUserButton.addActionListener(e -> {
            deleteUser();
        });

        changeUserButton.addActionListener(e -> {
            ChangeUser();

        });

        updateUserButton.addActionListener(e -> {
            updateFileList();
        });

        viewUserButton.addActionListener(e ->{
            viewUser();
        });

        //创建文件列表面板
        JPanel fileListPanel = new JPanel();
        fileListPanel.setLayout(new BorderLayout());
        //fileListPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5)); // 可选：增加边距

        fileListModel =new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(fileList);
        fileListPanel.add(scrollPane, BorderLayout.CENTER);

        // 将按钮面板和文件列表面板添加到主面板
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(fileListPanel,BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
        updateFileList();

    }

    private void bindRoleUser(){
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String fileName = fileListModel.getElementAt(selectedIndex);
        Path filePath = Paths.get(DB_DIR, fileName);
        File file = filePath.toFile();

        User user =loadObjectFromFile(fileName, User.class, DB_DIR);
        //System.out.println(role.toString());

        //弹出对话框让用户输入信息
        //String Id = JOptionPane.showInputDialog(this,"请输入新ID");
        new BindRoleForm(user).setLocation(850,400);
    }
    private void viewUser(){
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String fileName = fileListModel.getElementAt(selectedIndex);
        Path filePath = Paths.get(DB_DIR, fileName);
        File file = filePath.toFile();


        try{
            String content = "";
            content = new String(Files.readAllBytes(filePath));
            JOptionPane.showMessageDialog(this, content, "提示", JOptionPane.INFORMATION_MESSAGE);
    } catch (HeadlessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ChangeUser(){
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String fileName = fileListModel.getElementAt(selectedIndex);
        Path filePath = Paths.get(DB_DIR, fileName);
        File file = filePath.toFile();

        User user =loadObjectFromFile(fileName, User.class, DB_DIR);
        //System.out.println(user.toString());

        //弹出对话框让用户输入信息
        //String Id = JOptionPane.showInputDialog(this,"请输入新ID");
        new ChangeForm(user).setLocation(850,400);


    }
    private void addUser(){
        //弹出对话框让用户输入信息
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
            saveObjectToFile1(user,user.getAccount(),DB_DIR);

            JOptionPane.showMessageDialog(this,"用户添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"年龄必须是数字","错误",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFileList(){
        // 获取文件夹路径
        Path path = Paths.get(DB_DIR);
        File directory = path.toFile();

        if (!directory.exists() || !directory.isDirectory()){
            JOptionPane.showMessageDialog(this, "目录不存在或不是一个文件夹", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 读取文件夹中的所有文件
        File[] files = directory.listFiles();
        if (files == null || files.length ==0){
            JOptionPane.showMessageDialog(this,"文件夹为空","提示",JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //更新文件列表
        DefaultListModel<String> model = (DefaultListModel<String>) fileList.getModel();
        model.clear();
        for (File file : files) {
            model.addElement(file.getName());
        }
    }

    private void deleteUser(){

        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String fileName = fileListModel.getElementAt(selectedIndex);
        Path filePath = Paths.get(DB_DIR, fileName);
        File file = filePath.toFile();

        if (file.delete()) {
            JOptionPane.showMessageDialog(this, "用户删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            updateFileList();
        } else {
            JOptionPane.showMessageDialog(this, "删除用户失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UserForm().setLocation(850,400);
    }
}
