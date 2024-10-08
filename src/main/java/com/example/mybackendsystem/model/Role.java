package com.example.mybackendsystem.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.example.mybackendsystem.view.RoleForm.DB_DIR;

public class Role implements SerializableObject{
    private String Id;
    private String name;
    private int order;
    private Date createTime;
    private Date modifyTime;
    private User user;

    private Set<Menu> menus = new HashSet<>();

    // Getters and Setters
    public String getId(){
        return Id;
    }
    public void setId(String Id){

        this.Id = Id;
    }
    public String getName(){

        return name;
    }
    public void setName(String name){

        this.name = name;
    }
    public int getOrder(){

        return order;
    }
    public void setOrder(int order){

        this.order = order;
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

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public void addMenus(Menu menu){
        this.menus.add(menu);
    }

    public void removeMenuByName(String menuName) {
        menus.removeIf(menu -> menu.getName().equals(menuName));
    }

    @Override
    public String toString() {
        return "Role{" +
                "Id='" + Id + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", order=" + order + "\n" +
                ", createTime=" + createTime + "\n"+
                ", modifyTime=" + modifyTime + "\n"+
                ", menu=" + menus + "\n" +
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