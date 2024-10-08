package com.example.mybackendsystem.view;

import com.example.mybackendsystem.model.Role;
import com.example.mybackendsystem.model.User;

import static com.example.mybackendsystem.util.DataStore.*;
//import static com.example.mybackendsystem.view.UserForm.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class RoleForm extends JFrame{

    public JList<String> fileList;
    private DefaultListModel<String> fileListModel;

    public static final String DB_DIR = "src/main/resources/db/Role";
    public RoleForm(){
        setTitle("角色管理");
        setSize(700,400);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonPanel =new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addRoleButton = new JButton("添加角色");
        JButton deleteRoleButton = new JButton("删除角色");
        JButton changeRoleButton = new JButton("修改角色");
        JButton updateRoleButton = new JButton("刷新列表");
        JButton viewRoleButton = new JButton("查看角色");
        JButton bindMenuRoleButton =new JButton("绑定/解绑菜单");

        buttonPanel.add(addRoleButton);
        buttonPanel.add(deleteRoleButton);
        buttonPanel.add(changeRoleButton);
        buttonPanel.add(updateRoleButton);
        buttonPanel.add(viewRoleButton);
        buttonPanel.add(bindMenuRoleButton);

        //添加监听器

        bindMenuRoleButton.addActionListener(e -> {
            bindMenuRole();//绑定菜单到角色
        });

        addRoleButton.addActionListener(e -> {
            addRole();

        });

        deleteRoleButton.addActionListener(e -> {
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
        });

        changeRoleButton.addActionListener(e ->{
            changeRole();
        });

        updateRoleButton.addActionListener(e -> {
            updateFileList();
        });

        viewRoleButton.addActionListener(e -> {
            viewRole();
        });
        //创建文件列表面板
        JPanel fileListPanel = new JPanel();
        fileListPanel.setLayout(new BorderLayout());

        fileListModel =new DefaultListModel<>();
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

    private void addRole(){
        String roleId = JOptionPane.showInputDialog("请输入角色ID：");
        String roleName = JOptionPane.showInputDialog("请输入角色名称：");
        String orderStr = JOptionPane.showInputDialog("请输入角色排序：");
        if (roleId == null || roleName == null || orderStr == null){
            JOptionPane.showMessageDialog(this,"输入不能为空","错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int order = Integer.parseInt(orderStr);
            Role role = new Role(){{
                setId(roleId);
                setName(roleName);
                setOrder(order);
                setCreateTime(new Date());
                setModifyTime(new Date());
            }};

            saveObjectToFile(role,role.getName(),DB_DIR);
            JOptionPane.showMessageDialog(this,"添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"排序必须为数字","错误",JOptionPane.ERROR_MESSAGE);
        }
    }
    private void bindMenuRole(){
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String fileName = fileListModel.getElementAt(selectedIndex);
        Path filePath = Paths.get(DB_DIR, fileName);
        File file = filePath.toFile();

        Role role =loadObjectFromFile(fileName, Role.class, DB_DIR);
        //System.out.println(role.toString());

        //弹出对话框让用户输入信息
        //String Id = JOptionPane.showInputDialog(this,"请输入新ID");
        new BindMenuForm(role).setLocation(850,400);
    }
    private void changeRole(){
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String fileName = fileListModel.getElementAt(selectedIndex);
        Path filePath = Paths.get(DB_DIR, fileName);
        File file = filePath.toFile();

        Role role =loadObjectFromFile(fileName, Role.class, DB_DIR);
        //System.out.println(user.toString());

        //弹出对话框让用户输入信息
        //String Id = JOptionPane.showInputDialog(this,"请输入新ID");
        new ChangeForm(role).setLocation(850,400);
    }
    private void viewRole(){
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1){
            JOptionPane.showMessageDialog(this,"请选择一个用户文件","提示",JOptionPane.INFORMATION_MESSAGE);
            return;

        }

        String fileName = fileListModel.getElementAt(selectedIndex);
        Path filePath = Paths.get(DB_DIR, fileName);
        File file = filePath.toFile();

        try{
            String content = "";
            content = new String(Files.readAllBytes(filePath));
            JOptionPane.showMessageDialog(this, content, "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | IOException ex) {
            throw new RuntimeException(ex);
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

    public static void main(String[] args) {

        new RoleForm().setLocation(850,400);
    }
}
