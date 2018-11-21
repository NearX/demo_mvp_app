package com.ybkj.demo.bean;

/**
 * - @Author:  Yi Shan Xiang
 * - @Description:  区号实体类
 * - @Time:  2018/8/1
 * - @Emaill:  380948730@qq.com
 */
public class AreaModel {

    private String name;
    private String id;

    public AreaModel(String id, String name) {
        this.name = name;
        this.id = id;
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
}
