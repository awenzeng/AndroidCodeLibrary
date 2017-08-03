package com.awen.codebase.model;

import android.util.Log;

import com.awen.codebase.annotition.AnnotionProxy;
import com.awen.codebase.annotition.UserInterface;
import com.awen.codebase.annotition.UserMethod;
import com.awen.codebase.annotition.UserParam;
import com.awen.codebase.bean.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Administrator on 2017/8/3.
 */

public class AnnotationReflectModel {
    private static final String TAG = "AnnotationReflectModel";

    /**
     * 注解调用
     */
    public static void invokeAnnotation() {
        UserInterface userInterface = AnnotionProxy.create(UserInterface.class);
        userInterface.getUser("我之存在，因为有你。");
        try{
            Class c1 = Class.forName("com.awen.codebase.bean.User");
            Method method =  c1.getDeclaredMethod("getName");
            UserMethod userMethod = method.getAnnotation(UserMethod.class);
            if (userMethod != null) {
                Log.e(TAG, "注解方法：" + userMethod.title() + "\n");
            }

            Method method1 =  c1.getDeclaredMethod("print",String.class);//获取单个方法
            Annotation[][] parameterAnnotationsArray = method1.getParameterAnnotations();//拿到参数注解
            if (parameterAnnotationsArray != null) {
                for (int i = 0; i < parameterAnnotationsArray.length; i++) {
                    Annotation[] annotations = parameterAnnotationsArray[i];
                    if (annotations != null && annotations.length != 0) {
                        UserParam userParam = (UserParam) annotations[0];
                        Log.e(TAG, "注解参数2：" + userParam.name() + "," + userParam.phone() + "\n");
                    }
                }
            }
            Method[] methods = c1.getDeclaredMethods();
            for(Method method2:methods){

                UserMethod userMethod1 = method2.getAnnotation(UserMethod.class);
                if (userMethod1 != null) {
                    Log.e(TAG,method2.getName()+ "的注解方法：" + userMethod1.title() + "\n");
                }

                Annotation[][] temp = method2.getParameterAnnotations();//拿到参数注解
                if (temp != null) {
                    for (int i = 0; i < temp.length; i++) {
                        Annotation[] annotations = temp[i];
                        if (annotations != null && annotations.length != 0) {
                            UserParam userParam = (UserParam) annotations[0];
                            Log.e(TAG, method2.getName()+"的注解参数2：" + userParam.name() + "," + userParam.phone() + "\n");
                        }
                    }
                }
            }
        }catch (Exception e){

        }

    }

    /**
     * 反射调用
     */
    public static void invokeReflect() {
        try {

            StringBuffer sb = new StringBuffer();

            /********************************反射获取类的三种方法start****************/
            //第一种方式：
            Class c = Class.forName("com.awen.codebase.bean.User");

            //第二种方式：
            Class c2 = User.class;

            //第三种方式：
            //java语言中任何一个java对象都有getClass 方法
            User user = new User();
            Class c3 = user.getClass(); //c3是运行时类 (e的运行时类是Employee)
            /********************************反射获取类的三种方法end********************************/


            //c.getModifiers:类的权限类型（public或private)   c.getSimpleName():类的名字（User)
            sb.append(Modifier.toString(c.getModifiers()) + " class " + c.getSimpleName() + "{\n");


            Object o = c.newInstance();//对象初始化




            /************************属性变量start****************/
            Field idF = c.getDeclaredField("id");
            idF.setAccessible(true);
            idF.set(o, 1000000);

            Field nameF = c.getDeclaredField("name");
            nameF.setAccessible(true);
            nameF.set(o, "刘德华");

            Field phoneF = c.getDeclaredField("phone");
            phoneF.setAccessible(true);
            phoneF.set(o, "18665859331");

            Field[] fs = c.getDeclaredFields();//获取所有属性变量

            for (Field field : fs) {
                sb.append("\t");//空格
                sb.append(Modifier.toString(field.getModifiers()) + " ");//获得属性的修饰符，例如public，static等等
                sb.append(field.getType().getSimpleName() + " ");//属性的类型的名字
                sb.append(field.getName() + ";\n");//属性的名字+回车
            }
            /************************属性变量end****************/


            /************************类的方法start****************/
            Method print = c.getDeclaredMethod("print", String.class);//获取单个方法
            print.invoke(o, "中国人民解放军");//方法调用


            Method[] md = c.getDeclaredMethods();//获取类的所有方法
            for (Method method : md) {
                sb.append("\t");//空格
                sb.append(Modifier.toString(method.getModifiers()) + " ");//获得属性的修饰符，例如public，static等等
                sb.append(method.getReturnType().getSimpleName() + " ");//属性的类型的名字
                sb.append(method.getName() + "();\n");//属性的名字+回车
            }

            sb.append("}");
            Log.e("good", sb.toString());
            /************************类的方法end****************/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
