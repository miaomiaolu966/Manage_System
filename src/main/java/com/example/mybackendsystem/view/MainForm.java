package com.example.mybackendsystem.view;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {
    public MainForm(String username) {
        setTitle("主页面"+"  当前登录用户："+username);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(){{
            setLayout(new FlowLayout());

            JButton usersButton = new JButton("用户管理");
            JButton menusButton = new JButton("菜单管理");
            JButton rolesButton = new JButton("角色管理");

            add(usersButton);
            add(menusButton);
            add(rolesButton);

            // 添加按钮点击事件
            usersButton.addActionListener(e ->  {

                // 关闭主界面
                //dispose();
                // 显示用户管理界面
                new UserForm().setLocation(850,400);

            });

            menusButton.addActionListener(e -> {
                // 关闭主界面
                //dispose();

                // 显示菜单管理界面
                new MenuForm().setLocation(850,400);
            });

            rolesButton.addActionListener(e -> {
                //关闭主界面
                //dispose();

                //显示角色管理界面
                new RoleForm().setLocation(850,400);
            });
        }};


//        // 添加按钮点击事件
//        usersButton.addActionListener(e ->  {
//
//            // 关闭主界面
//            //dispose();
//            // 显示用户管理界面
//            new UserForm().setLocation(850,400);
//
//        });
//
//        menusButton.addActionListener(e -> {
//            // 关闭主界面
//            //dispose();
//
//            // 显示菜单管理界面
//            new MenuForm().setLocation(850,400);
//        });
//
//        rolesButton.addActionListener(e -> {
//            //关闭主界面
//            //dispose();
//
//            //显示角色管理界面
//            new RoleForm().setLocation(850,400);
//        });

        // 创建说明面板
        JPanel infoPanel = new JPanel(){{
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        }};


        // 创建说明文本
        JTextArea infoTextArea = new JTextArea("这是一个后台管理系统，用于管理用户、菜单和角色。\n\n" +
                "- 用户管理：管理系统的用户信息。在此可以绑定/解绑用户中的角色\n" +
                "- 菜单管理：管理系统的菜单信息。\n" +
                "- 角色管理：管理系统的角色信息。在此可以绑定/解绑角色中的菜单"){{
            setEditable(false);
            setLineWrap(true);
            setWrapStyleWord(true);
            setFont(new Font("Arial", Font.PLAIN, 14));
            setBackground(infoPanel.getBackground());
        }};


        JScrollPane scrollPane = new JScrollPane(infoTextArea){{
            setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        }};
        //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        infoPanel.add(scrollPane);

        mainpanel.add(buttonPanel, BorderLayout.NORTH);
        mainpanel.add(infoPanel, BorderLayout.CENTER);

        add(mainpanel);
        setVisible(true);
    }

    public static void main(String[] args) {

        new MainForm("虚拟用户").setLocation(850,400);
    }
}
