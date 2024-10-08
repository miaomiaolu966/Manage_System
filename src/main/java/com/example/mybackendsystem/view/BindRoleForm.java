package com.example.mybackendsystem.view;

import com.example.mybackendsystem.model.Menu;
import com.example.mybackendsystem.model.Role;
import com.example.mybackendsystem.model.User;
import com.example.mybackendsystem.model.SerializableObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.example.mybackendsystem.util.DataStore.loadObjectFromFile;
import static com.example.mybackendsystem.util.DataStore.saveObjectToFile;
import static com.example.mybackendsystem.view.RoleForm.DB_DIR;

public class BindRoleForm extends JFrame {

    private User user;

    public JList<String> fileList;
    private DefaultListModel<String> fileListModel;

    public BindRoleForm(User user){
        this.user = user;
        //System.out.println(role.toString());

        setTitle("绑定/解绑角色");
        setSize(600, 300);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel buttonPanel =new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addRoleButton = new JButton("绑定角色");
        JButton deleteRoleButton = new JButton("解绑角色");

        buttonPanel.add(addRoleButton);
        buttonPanel.add(deleteRoleButton);

        addRoleButton.addActionListener(e -> {
            int selectedIndex = fileList.getSelectedIndex();

            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "请选择一个用户文件", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String fileName = fileListModel.getElementAt(selectedIndex);
            Path filePath = Paths.get(DB_DIR, fileName);
            File file = filePath.toFile();

            try {
                Role role = loadObjectFromFile(fileName, Role.class, DB_DIR);

                user.addRole(role);

                saveObjectToFile((SerializableObject) user);

                //System.out.println(role.toString());
                JOptionPane.showMessageDialog(this, "添加角色成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "添加角色失败", "错误", JOptionPane.ERROR_MESSAGE);
            }


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

            try {
                Role role = loadObjectFromFile(fileName, Role.class, DB_DIR);

                user.removeRoleByName(role.getName());
                saveObjectToFile((SerializableObject) user);

                //System.out.println(role.toString());
                JOptionPane.showMessageDialog(this, "解绑角色成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "解绑角色失败", "错误", JOptionPane.ERROR_MESSAGE);
            }


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
        //new BindMenuForm<>;
    }
}
