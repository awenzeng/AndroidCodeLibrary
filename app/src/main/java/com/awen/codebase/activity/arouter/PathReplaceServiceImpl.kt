package com.awen.codebase.activity.arouter

import android.content.Context
import android.net.Uri
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.PathReplaceService


/***
 * 实现PathReplaceService接口，并加上一个Path内容任意的注解即可
  */
@Route(path = "/yourservicegroupname/pathReplaceService") // 必须标明注解
class PathReplaceServiceImpl : PathReplaceService {
    /**
     * For normal path.
     *
     * @param path raw path
     */
    override fun forString(path: String): String {
//        if (path == "/login/loginActivity") {
//            return "/share/shareActivity"
//        }
        return path // 按照一定的规则处理之后返回处理后的结果
    }

    /**
     * For uri type.
     *
     * @param uri raw uri
     */
    override fun forUri(uri: Uri?): Uri? {
        return null // 按照一定的规则处理之后返回处理后的结果
    }

    override fun init(context: Context?) {
    }
}