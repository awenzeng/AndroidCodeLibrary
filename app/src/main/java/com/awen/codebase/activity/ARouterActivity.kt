/*
 * Tencent is pleased to support the open source community by making vap available.
 *
 * Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.awen.codebase.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.awen.codebase.R
import com.awen.codebase.activity.arouter.HelloService
import com.awen.codebase.activity.arouter.TestBean
import com.awen.codebase.common.base.BaseActivity

/**
 * 简单使用demo
 */

@Route(path = ActivityRouter.AROUTER_ARouterActivity)
class ARouterActivity : BaseActivity() {

    @Autowired
    lateinit var helloService: HelloService

    @Autowired(name = "/common/hello")
    lateinit var helloService1: HelloService

    lateinit var helloService2: HelloService
    lateinit var helloService3: HelloService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setActivityContentView(R.layout.act_arouter)
        val loginBtn = findViewById<View>(R.id.login)
        val showBt = findViewById<TextView>(R.id.loginInfo)
        val ok = findViewById<TextView>(R.id.ok)
        ok.setOnClickListener {
            ARouter.getInstance().build("/share/shareActivity").withString("username", "zhangsan")
                .withObject("testBean", TestBean("lisi", 20))
                .withObject(
                    "listBean",
                    listOf<TestBean>(TestBean("wanger", 20), TestBean("xiaoming", 20))
                )
                .navigation(this, object : NavigationCallback {
                    override fun onLost(postcard: Postcard?) {
                    }

                    override fun onFound(postcard: Postcard?) {
                    }

                    override fun onInterrupt(postcard: Postcard?) {
                        Log.d("ARouter", "还没有登录")
                    }

                    override fun onArrival(postcard: Postcard?) {
                    }

                })
        }
        helloService.sayHello("helloService")
        helloService1.sayHello("helloService1")

        helloService2 = ARouter.getInstance().build("/common/hello").navigation() as HelloService

        helloService3 = ARouter.getInstance().navigation(HelloService::class.java)

        helloService2.sayHello("helloService2")
        helloService3.sayHello("helloService3")

    }
}