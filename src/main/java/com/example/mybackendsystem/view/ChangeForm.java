package com.example.mybackendsystem.view;

import com.example.mybackendsystem.model.SerializableObject;
import com.example.mybackendsystem.model.User;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static com.example.mybackendsystem.util.DataStore.saveObjectToFile;
import static com.example.mybackendsystem.view.UserForm.DB_DIR;

public class ChangeForm<T> extends JFrame {
    private T object;

    public ChangeForm(T obj){
        this.object = obj;
        setTitle("修改数据");
        setSize(600, 300);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel StringPanel = new JPanel();
        StringPanel.setLayout(new BorderLayout());

        // 创建文本区域并允许编辑
        JTextArea textArea = new JTextArea();
        textArea.setText(obj.toString());
        textArea.setEditable(false); // 允许编辑
        textArea.setBackground(Color.WHITE); // 设置背景颜色为白色

        // 使用滚动面板包裹文本区域，以便处理过长的文本
        JScrollPane scrollPane = new JScrollPane(textArea);
        StringPanel.add(scrollPane, BorderLayout.CENTER);


        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // 动态生成按钮
        for (Method method : obj.getClass().getMethods()) {
            if (method.getName().startsWith("set")) {
                if (method.getName().equals("setCreateTime")) {
                    continue;  // 跳过此迭代
                }
                JButton button = new JButton("修改" + method.getName().substring(3));
                button.addActionListener(e -> modifyProperty(method));
                buttonPanel.add(button);
            }
        }

        //buttonPanel.add(Cg_ID_Button);
        //buttonPanel.add(Cg_ac_UserButton);
//        buttonPanel.add(Cg_sex_UserButton);
//        buttonPanel.add(Cg_age_UsersButton);
//        buttonPanel.add(Cg_pw_UserButton);

//        Cg_ID_Button.addActionListener(e -> {
//            String id = JOptionPane.showInputDialog(this, "请输入新ID:");
//            if (id == null) {
//                JOptionPane.showMessageDialog(this, "输入不能为空", "错误", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            try {
//                user.setId(id);
//
//                // 将用户信息写入 JSON 文件
//                saveObjectToFile(user,user.getAccount(),DB_DIR);
//                JOptionPane.showMessageDialog(this, "用户数据修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
//            }catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(this, "出错了，请重试", "错误", JOptionPane.ERROR_MESSAGE);
//            }
//        });


        // 将按钮面板和文件列表面板添加到主面板
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(StringPanel, BorderLayout.CENTER);
//        System.out.println(user.toString());
        add(mainPanel);
        setVisible(true);
    }

    private void modifyProperty(Method setter) {
        String input = JOptionPane.showInputDialog(this, "请输入新值:");
        if (input == null || input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "输入不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Method getter = object.getClass().getMethod("get" + setter.getName().substring(3));
            Object currentValue = getter.invoke(object);
            if (currentValue instanceof Integer) {
                int newValue = Integer.parseInt(input);
                setter.invoke(object, newValue);
            } else if (currentValue instanceof String) {
                setter.invoke(object, input);
            } else {
                JOptionPane.showMessageDialog(this, "不支持的数据类型", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // 将用户信息写入 JSON 文件
            saveObjectToFile((SerializableObject) object);

            JOptionPane.showMessageDialog(this, "数据修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "出错了，请重试", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        //new ChangeForm(User);
    }
}
