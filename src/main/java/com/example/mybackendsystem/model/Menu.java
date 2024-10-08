package com.example.mybackendsystem.model;

import java.util.Date;

import static com.example.mybackendsystem.view.MenuForm.DB_DIR;

public class Menu implements SerializableObject{
    private String id;
    private String name;
    private int order;
    private String path;//(用户点击此菜单加载对应的页面展示)
    private Date createTime;
    private Date modifyTime;
    private Role role;

    // Getters and Setters

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", path='" + path + '\'' +
                ", createTime=" + (createTime != null ? createTime.toString() : "null") +
                ", modifyTime=" + (modifyTime != null ? modifyTime.toString() : "null") +
                ", role=" + role +
                '}';
    }

    @Override
    public String getIdentifier() {
        return getName(); // 假设 getName() 返回唯一标识符
    }

    @Override
    public String getDBDir() {
        return DB_DIR; // 用户数据的目录
    }
}
