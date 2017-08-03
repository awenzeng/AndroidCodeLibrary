package com.awen.codebase.annotition;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * 动态代理获取注解
 * Created by Administrator on 2017/8/1.
 */

public class AnnotionProxy {
    public static  <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object... args)
                            throws Throwable {
                        // Annotation[]  methodAnnotations = method.getAnnotations();//拿到函数注解数组
                        UserMethod userMethod = method.getAnnotation(UserMethod.class);
                        Log.e("good", "UserMethod---title->" + userMethod.title());
                        Type[] parameterTypes = method.getGenericParameterTypes();
                        Annotation[][] parameterAnnotationsArray = method.getParameterAnnotations();//拿到参数注解
                        for (int i = 0; i < parameterAnnotationsArray.length; i++) {
                            Annotation[] annotations = parameterAnnotationsArray[i];
                            if (annotations != null) {
                                UserParam userParam = (UserParam) annotations[0];
                                Log.e("good", "UserParam---userParam->" + userParam.name()+ ","+userParam.phone()+ "," + args[i]);
                            }
                        }
                        return "";
                    }
                });
    }
}
