package com.example.mybackendsystem.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import com.example.mybackendsystem.model.Menu;
import com.example.mybackendsystem.model.User;

import static com.example.mybackendsystem.util.DataStore.*;
import static com.example.mybackendsystem.view.UserForm.*;


public class MenuForm extends JFrame {
    public JList<String> fileList;
    private DefaultListModel<String> fileListModel;
    public static final String DB_DIR = "src/main/resources/db/Menu";

    public MenuForm(){//构造器
        setTitle("菜单管理");
        setSize(600,400);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());//创建主面板，设置布局

        JPanel buttonPanel =new JPanel(new FlowLayout()){{
            JButton addMenuButton = new JButton("添加菜单");
            JButton deleteMenuButton = new JButton("删除菜单");
            JButton changeMenuButton = new JButton("修改菜单");
            JButton updateMenuButton = new JButton("刷新列表");
            JButton viewMenuButton = new JButton("查看菜单");

            add(addMenuButton);
            add(deleteMenuButton);
            add(changeMenuButton);
            add(updateMenuButton);
            add(viewMenuButton);

            //添加监听器
            addMenuButton.addActionListener(e -> {
                String id = JOptionPane.showInputDialog("请输入菜单ID：");
                String name = JOptionPane.showInputDialog("请输入菜单名称：");
                String path = JOptionPane.showInputDialog("请输入菜单路径：");
                String orderStr = JOptionPane.showInputDialog("请输入菜单顺序：");
                if (id == null || name == null || path == null || orderStr == null) {
                    JOptionPane.showMessageDialog(this, "输入不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int order = Integer.parseInt(orderStr);
                    Menu menu = new Menu();
                    menu.setId(id);
                    menu.setName(name);
                    menu.setPath(path);
                    menu.setOrder(order);
                    menu.setCreateTime(new Date());
                    menu.setModifyTime(new Date());

                    saveObjectToFile(menu,menu.getName(),DB_DIR);
                    JOptionPane.showMessageDialog(this,"添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex){}

            });

            deleteMenuButton.addActionListener(e -> {
//                int selectedIndex = fileList.getSelectedIndex();
//
//                if (selectedIndex == -1) {
//                    JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
//                    return;
//                }
//
//                String fileName = fileListModel.getElementAt(selectedIndex);
                Path filePath = Paths.get(DB_DIR, filenamefromindex());
                File file = filePath.toFile();

                if (file.delete()) {
                    JOptionPane.showMessageDialog(this, "用户删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    updateFileList();
                } else {
                    JOptionPane.showMessageDialog(this, "删除用户失败", "错误", JOptionPane.ERROR_MESSAGE);
                }

            });

            changeMenuButton.addActionListener(e -> {
//                int selectedIndex = fileList.getSelectedIndex();
//
//                if (selectedIndex == -1) {
//                    JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
//                    return;
//                }
//
//                String fileName = fileListModel.getElementAt(selectedIndex);
//                Path filePath = Paths.get(DB_DIR, fileName);
//                File file = filePath.toFile();

                Menu menu =loadObjectFromFile(filenamefromindex(), Menu.class, DB_DIR);
                //System.out.println(user.toString());

                //弹出对话框让用户输入信息
                //String Id = JOptionPane.showInputDialog(this,"请输入新ID");
                new ChangeForm(menu).setLocation(850,400);
            });

            updateMenuButton.addActionListener(e -> {
                updateFileList();
            });

            viewMenuButton.addActionListener(e -> {
//                int selectedIndex = fileList.getSelectedIndex();
//
//                if (selectedIndex == -1) {
//                    JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
//                    return;
//                }
//
//                String fileName = fileListModel.getElementAt(selectedIndex);
                Path filePath = Paths.get(DB_DIR, filenamefromindex());
                File file = filePath.toFile();


                try{
                    String content = "";
                    content = new String(Files.readAllBytes(filePath));
                    JOptionPane.showMessageDialog(this, content, "提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (HeadlessException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }};

        //创建文件列表面板
        JPanel fileListPanel = new JPanel(new BorderLayout());
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

    private  String filenamefromindex(){
        int selectedIndex = fileList.getSelectedIndex();

        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            //return;
        }

        String fileName = fileListModel.getElementAt(selectedIndex);
        return fileName;
    }
    public static void main(String[] args) {

        new MenuForm().setLocation(850,400);
    }
}
