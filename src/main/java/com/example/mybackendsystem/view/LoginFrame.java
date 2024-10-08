package com.example.mybackendsystem.view;

import com.example.mybackendsystem.model.User;
import com.example.mybackendsystem.util.DataStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static com.example.mybackendsystem.util.MD5Util.encrypt;
import static com.example.mybackendsystem.util.DataStore.DB_DIR;
import com.example.mybackendsystem.util.MD5Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("登录");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(){{
            setLayout(new GridLayout(3, 2));
            add(new JLabel("用户名:"));
            usernameField = new JTextField();
            add(usernameField);

            add(new JLabel("密码:"));
            passwordField = new JPasswordField();
            add(passwordField);
        }};


        JButton loginButton = new JButton("登录"){{
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    char[] passwordChars = passwordField.getPassword();
                    String password = new String(passwordChars);

                    // 登录逻辑
                    try {
                        if (validateLogin(username, password)) {
                            JOptionPane.showMessageDialog(LoginFrame.this, "登录成功！");

                            // 关闭登录界面
                            dispose();
                            // 显示主界面
                            new MainForm(username).setLocation(850,400);

                        } else {
                            JOptionPane.showMessageDialog(LoginFrame.this, "用户名或密码错误！");
                        }
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }};

        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    private boolean validateLogin(String username, String password) throws FileNotFoundException {
        // 从JSON文件中读取用户数据并验证
        //从路径user.json中读取用户数据，user.json是一个列表数据，里面有多个用户数据
        FileReader reader = new FileReader(DB_DIR + "/user.json");
        Gson gson = new Gson();
        List<User> userList = gson.fromJson(reader, new TypeToken<List<User>>(){}.getType());

        for (User user : userList) {
            //System.out.println(user.toString());
            if (user != null && user.getAccount().equals(username) && user.getPassword().equals(encrypt(password))) {
                return true;
            }
        }
        return false;
    }//密码MD5 检验

    public static void main() {
        new LoginFrame().setLocation(850,400);
    }
}
