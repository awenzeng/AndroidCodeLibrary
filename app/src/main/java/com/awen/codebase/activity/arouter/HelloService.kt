package com.awen.codebase.activity.arouter

import com.alibaba.android.arouter.facade.template.IProvider

interface HelloService:IProvider{
    fun sayHello(name:String):String
}