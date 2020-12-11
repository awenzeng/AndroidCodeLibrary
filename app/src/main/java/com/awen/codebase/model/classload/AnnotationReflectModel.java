package com.awen.codebase.model.classload;

import com.awen.codebase.common.annotition.AnnotionProxy;
import com.awen.codebase.common.annotition.UserInterface;
import com.awen.codebase.common.annotition.UserMethod;
import com.awen.codebase.common.annotition.UserParam;
import com.awen.codebase.common.annotition.User;
import com.awen.codebase.common.utils.LogUtil;

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
        //动态代理方式来
        UserInterface userInterface = AnnotionProxy.create(UserInterface.class);
        userInterface.getUser("我之存在，因为有你。");

        //反射方式
        try {
            Class c1 = Class.forName("com.awen.codebase.common.annotition.User");
            Method method = c1.getDeclaredMethod("getName");
            UserMethod userMethod = method.getAnnotation(UserMethod.class);
            if (userMethod != null) {
                LogUtil.androidLog("注解方法：" + userMethod.title() + "\n");
            }

            Method method1 = c1.getDeclaredMethod("print", String.class);//获取单个方法
            Annotation[][] parameterAnnotationsArray = method1.getParameterAnnotations();//拿到参数注解
            if (parameterAnnotationsArray != null) {
                for (int i = 0; i < parameterAnnotationsArray.length; i++) {
                    Annotation[] annotations = parameterAnnotationsArray[i];
                    if (annotations != null && annotations.length != 0) {
                        UserParam userParam = (UserParam) annotations[0];
                        LogUtil.androidLog("注解参数2：" + userParam.name() + "," + userParam.phone() + "\n");
                    }
                }
            }
            Method[] methods = c1.getDeclaredMethods();
            for (Method method2 : methods) {

                UserMethod userMethod1 = method2.getAnnotation(UserMethod.class);
                if (userMethod1 != null) {
                    LogUtil.androidLog(method2.getName() + "的注解方法：" + userMethod1.title() + "\n");
                }

                Annotation[][] temp = method2.getParameterAnnotations();//拿到参数注解
                if (temp != null) {
                    for (int i = 0; i < temp.length; i++) {
                        Annotation[] annotations = temp[i];
                        if (annotations != null && annotations.length != 0) {
                            UserParam userParam = (UserParam) annotations[0];
                            LogUtil.androidLog(method2.getName() + "的注解参数2：" + userParam.name() + "," + userParam.phone() + "\n");
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

    }

    /**
     * 反射调用常用方法总结
     * <p>
     * 1.实例化Class对象，有三种方式，
     * Class.forName(类名全路径); //通过Class的静态方法
     * 对象.getClass() //通过对象.getClass方法
     * int.class //基本数据类型及基本数据类型的封装了，例如Integer
     * <p>
     * 2.获取父类
     * Class<?> clazz  = Class.forName(类名全路径); //通过Class的静态方法
     * Class<?> superclass = clazz.getSuperclass();
     * <p>
     * 3.获取实现接口
     * Class<?> clazz  = Class.forName(类名全路径); //通过Class的静态方法
     * Class<?>[] interfaces = clazz.getInterfaces()
     * <p>
     * 4.获取指定参数构造函数及实例化
     * Class<?> clazz  = Class.forName(类名全路径); //通过Class的静态方法
     * Constructor<?> constructor = clazz.getConstructor(Class<?>  ... class);//获取公共的
     * Constructor<?> constructor = clazz.getDeclaredConstructor()//获取私有的
     * constructor.newInstance(Object args);
     * <p>
     * 5.获取所有构造函数及构造参数的类型
     * Class<?> clazz  = Class.forName(类名全路径); //通过Class的静态方法
     * Constructor<?>[] constructors = clazz.getConstructors();//公共的
     * Constructor<?>[] constructors = clazz.getDeclaredConstructors()//包括私有的
     * <p>
     * for (int i = 0; i < constructors.length; i++) {
     * Class<?> clazzs[] = constructors[i].getParameterTypes();//获取类型
     * System.out.print("constructors[" + i + "] (");
     * for (int j = 0; j < clazzs.length; j++) {
     * if (j == clazzs.length - 1)
     * System.out.print(clazzs[j].getName());
     * else
     * System.out.print(clazzs[j].getName() + ",");
     * }
     * System.out.println(")");
     * }
     * <p>
     * <p>
     * 6.通过无参实例化对象
     * Class<?> clazz  = Class.forName(类名全路径); //通过Class的静态方法
     * class.newInstance();
     * <p>
     * <p>
     * 7.获取字段，修改字段
     * Class<?> clazz  = Class.forName(类名全路径); //通过Class的静态方法
     * <p>
     * Field field = clazz.getField(String name);//获取公共字段
     * Field field = clazz.getDeclaredField(String name);//获取私有公共字段
     * Field[] field = clazz.getFields();//获取所有公共字段
     * Field[] field = clazz.getDeclaredFields();//获取包括私有所有字段
     * <p>
     * Field field = clazz.getDeclaredField("heihei");
     * field.setAccessible(true);//设置java取消访问检查，也就是说如果是私有的也可以访问,
     * field.set(obj, "Java反射机制");
     * <p>
     * 8.获取方法,运行方法
     * Class<?> clazz  = Class.forName(类名全路径); //通过Class的静态方法
     * <p>
     * clazz.getMethod(String name ,Class<?> ... parame);//获取公共指定方法
     * clazz.getDeclaredMethod(String name ,Class<?> ... parame)//获取私有指定方法
     * clazz.getMethods()//获取公共所有方法
     * clazz.getDeclaredMethods();//获取包括私有全部方法
     * <p>
     * Method method = clazz.getMethod("add");
     * method.invoke(clazz.newInstance());
     * <p>
     * method = clazz.getMethod("getInfo", int.class, String.class);
     * method.setAccessible(true)//设置java取消访问检查，也就是说如果是私有的也可以访问,
     * method.invoke(clazz.newInstance(), 20, "张三");
     * <p>
     * <p>
     * 9.获取数组或者list中的类型,如果不是数组或集合返回null
     * Class<?> clazz  = Class.forName(类名全路径); //通过Class的静态方法
     * Class<?> componentType = clazz.getComponentType();
     */
    public static void invokeReflect() {
        try {
            StringBuffer sb = new StringBuffer();

            /********************************反射获取类的三种方法start****************/
            //第一种方式：
            Class c = Class.forName("com.awen.codebase.common.annotition.User");

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
            LogUtil.androidLog(sb.toString());
            /************************类的方法end****************/

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
