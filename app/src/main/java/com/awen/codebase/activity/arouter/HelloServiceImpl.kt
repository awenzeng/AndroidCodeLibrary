package com.awen.codebase.activity.arouter

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.awen.codebase.activity.arouter.HelloService


// 实现接口
@Route(path = "/common/hello", name = "测试服务")
class HelloServiceImpl : HelloService {
    override fun sayHello(name: String): String {
        Log.d("ARouter", "hello, $name")
        return "hello, $name"
    }

    override fun init(context: Context) {}
}