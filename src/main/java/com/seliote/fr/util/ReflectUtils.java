package com.seliote.fr.util;

import com.seliote.fr.exception.UtilException;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 反射相关工具类
 *
 * @author seliote
 */
@Log4j2
public class ReflectUtils {

    /**
     * 获取指定 Class 对象的泛型类型的全限定类名
     *
     * @param clazz Class 对象
     * @param <T>   Class 对象的泛型类型
     * @return Class 对象的泛型类型的全限定类名
     */
    @NonNull
    public static <T> String getClassName(@NonNull Class<T> clazz) {
        var name = clazz.getCanonicalName();
        log.trace("ReflectUtils.getClassName(Class<T>) for: {}, result: {}", clazz, name);
        return name;
    }

    /**
     * 获取指定对象的全限定类名
     *
     * @param object 对象
     * @param <T>    对象的泛型类型
     * @return 对象的全限定类名
     */
    @NonNull
    public static <T> String getClassName(@NonNull T object) {
        var name = ReflectUtils.getClassName(object.getClass());
        log.trace("ReflectUtils.getClassName(T) for: {}, result: {}", object, name);
        return name;
    }

    /**
     * 获取方法的全限定名称
     *
     * @param method Method 对象
     * @return 方法的全限定名称
     */
    @NonNull
    public static String getMethodName(@NonNull Method method) {
        var name = ReflectUtils.getClassName(method.getDeclaringClass()) + "." + method.getName();
        log.trace("ReflectUtils.getMethodName(Method) for: {}, result: {}", method, name);
        return name;
    }

    /**
     * Bean 属性拷贝
     * 1. 目标数据对象必须为顶层类
     * 2. 目标数据对象必须由默认无参构造函数
     * 3. 原宿属性不要求完全一致，会被忽略或置默认
     *
     * @param source      数据源对象
     * @param targetClass 目标数据 Class 类型
     * @param ignoreProps 数据源中需要忽略的属性
     * @param <T>         目标数据 Class 类型泛型
     * @return 目标数据对象
     */
    @NonNull
    public static <T> T copy(@NonNull Object source, @NonNull Class<T> targetClass, String... ignoreProps) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProps);
            log.trace("BeanUtils.copy(Object, Class<T>, String...) for {}, {}, {}, result {}",
                    source, targetClass, Arrays.toString(ignoreProps), target);
            return target;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException exception) {
            log.warn("Bean copy occur {}, message {}", getClassName(exception), exception.getMessage());
            throw new UtilException(exception);
        }
    }
}
