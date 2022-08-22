package com.awen.codebase.activity.arouter

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService

@Route(path = "/yourservicegroupname/DegradeServiceImpl")
class DegradeServiceImpl : DegradeService {
    override fun onLost(context: Context, postcard: Postcard) {
        Log.d("DegradeServiceImpl", "没有找到该路由地址:${postcard.path}")
    }

    override fun init(context: Context?) {
    }

}