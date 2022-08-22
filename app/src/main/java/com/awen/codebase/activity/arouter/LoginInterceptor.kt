package com.awen.codebase.activity.arouter

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

@Interceptor(priority = 8, name = "登录拦截")
class LoginInterceptor : IInterceptor {
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val path = postcard.path
        if (path == "/share/shareActivity") {
        }else{
            callback.onContinue(postcard)
        }
    }

    override fun init(context: Context?) {
        // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        Log.d("LoginInterceptor", "LoginInterceptor初始化了")
    }

}