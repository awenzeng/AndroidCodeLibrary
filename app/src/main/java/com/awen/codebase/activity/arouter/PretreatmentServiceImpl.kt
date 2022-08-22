package com.awen.codebase.activity.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.PretreatmentService


@Route(path = "/yourservicegroupname/pretreatmentService")
class PretreatmentServiceImpl : PretreatmentService {
    override fun onPretreatment(context: Context?, postcard: Postcard): Boolean {
        if (postcard.path == "/share/shareActivity") {
        }
        return true
    }

    override fun init(context: Context) {}
}