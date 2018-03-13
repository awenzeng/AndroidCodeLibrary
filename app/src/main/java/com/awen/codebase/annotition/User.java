package com.awen.codebase.annotition;

import android.util.Log;

import com.awen.codebase.annotition.UserMethod;
import com.awen.codebase.annotition.UserParam;

/**
 * Created by Administrator on 2017/8/3.
 */

public class User {
    private int id;
    private String name;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @UserMethod(title = "牛B啊！！！")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @UserMethod(title = "中国梦")
    public String getPhone() {
        return phone;
    }

    public void setPhone(@UserParam(name = "李小龙",phone = "999")String phone) {
        this.phone = phone;
    }

    public void print(@UserParam(name = "刘凤",phone = "110") String temp){
        Log.e("good", "User: "+id+","+name+","+phone+","+temp);
    }
}
