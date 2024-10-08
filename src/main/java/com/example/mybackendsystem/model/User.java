package com.example.mybackendsystem.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.example.mybackendsystem.util.MD5Util.encrypt;
import static com.example.mybackendsystem.view.UserForm.DB_DIR;

public class User implements SerializableObject{ // 用户模块
    private String id; // 用户id
    private String account; // 用户账号
    private String sex;
    private int age;
    private String password; // 密码，之后要改成md5储存！
    private Date createTime;
    private Date modifyTime;
    private Set<Role> roles = new HashSet<>(); // 一对多关系

    public String getId() {
        return id;
    }//通过公共方法访问对象中私有的字段

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = encrypt(password);
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public void addRole(Role role){
        this.roles.add(role);
    }

    public void removeRoleByName(String roleName) {
        roles.removeIf(role -> role.getName().equals(roleName));
    }
    @Override
    public String toString() {
        return "User{\n" +
                "id='" + id + '\'' + "\n" +
                "account='" + account + '\'' + "\n" +
                "sex='" + sex + '\'' + "\n" +
                "age=" + age + "\n" +
                "password='" + password + '\'' + "\n" +
                "createTime=" + createTime + "\n" +
                "modifyTime=" + modifyTime + "\n" +
                '}';
    }

    @Override
    public String getIdentifier() {
        return getAccount(); // 假设 getAccount() 返回唯一标识符
    }

    @Override
    public String getDBDir() {
        return DB_DIR; // 用户数据的目录
    }
}
