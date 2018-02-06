package com.fla.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.type.Alias;
import com.google.common.collect.Maps;

@Alias("ReflectionUtils")
public class ReflectionUtils {

    public static enum AnnotationSign {
        CLASS, METHOD, FIELD;
    }

    public static void setObjectFieldValue2AnotherObject(Object from, Object target) {

        Class<?> fromClass = from.getClass();

        while (fromClass != null && !fromClass.equals(Object.class)) {
            Field[] fields = from.getClass().getDeclaredFields();

            for (Field field : fields) {
                setFieldValue2Object(field, from, target);
            }

            fromClass = fromClass.getSuperclass();
        }

    }

    public static void setFieldValue2Object(Field field, Object from, Object target) {
        try {
            if (field != null) {
                field.setAccessible(true);
                Object value = field.get(from);
                setFieldValue2Object(field.getName(), target, value);
            }
        } catch (IllegalAccessException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static void setFieldValue2Object(String fieldName, Object target, Object value) {
        Class<?> targetClass = target.getClass();

        while (targetClass != null && !targetClass.equals(Object.class)) {
            Field[] fields = targetClass.getDeclaredFields();

            for (Field field : fields) {
                if (field.getName().equalsIgnoreCase(fieldName)) {
                    field.setAccessible(true);
                    try {
                        field.set(target, value);
                    } catch (IllegalAccessException e) {
                        throw Exceptions.unchecked(e);
                    }
                    return;
                }
            }

            targetClass = targetClass.getSuperclass();
        }
    }

    public static Object invokeMethod(Object target, String methodName, Object...params) {
        Object result = null;
        try {
            Class<?> paramClasses[] = null;
            if (ArrayUtils.isNotEmpty(params)) {
                paramClasses = new Class<?>[params.length];

                for (int i = 0; i < params.length; i++)
                    paramClasses[i] = params[i].getClass();
            }

            Class<?> targetClz = target.getClass();
            Method method = targetClz.getDeclaredMethod(methodName, paramClasses);

            if (method == null) {
                method = targetClz.getMethod(methodName, paramClasses);
            }

            method.setAccessible(true);
            result = method.invoke(target, params);
        } catch (NoSuchMethodException e) {
            Exceptions.unchecked(e);
        } catch (InvocationTargetException e) {
            Exceptions.unchecked(e);
        } catch (IllegalAccessException e) {
            Exceptions.unchecked(e);
        }

        return result;
    }

    public static Object getAnnotationValue(Object target, Class<? extends Annotation> annotationClass,
                                        AnnotationSign annotationSign, String...annotationMethod) {
        Object value = null;
        String am = "value";

        if (ArrayUtils.isNotEmpty(annotationMethod)) {
            if (annotationMethod.length == 1)
                am = annotationMethod[1];
            else
                throw new CoreReflectionException("只能获取目标注解中一个方法的值，传入的方法数量大于1");
        }

        try {
            Class<?> targetClass = target.getClass();

            loop:while (targetClass != null && !targetClass.equals(Object.class)) {


                Annotation annotation = null;
                switch (annotationSign) {
                    case CLASS:
                        annotation = targetClass.getAnnotation(annotationClass);
                        break;
                    case FIELD:
                        Field[] fields = targetClass.getDeclaredFields();
                        for (Field field : fields) {
                            annotation = field.getAnnotation(annotationClass);
                            if (annotation != null)
                                break;
                        }
                        break;
                    case METHOD:
                        Method[] methods = targetClass.getDeclaredMethods();
                        for (Method method : methods) {
                            annotation = method.getAnnotation(annotationClass);
                            if (annotation != null)
                                break;
                        }
                        break;

                }


                if (annotation != null) {
                    Method[] methods = annotation.getClass().getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.getName().equals(am)) {
                            value = method.invoke(annotation);
                            break loop;
                        }
                    }

                    throw new CoreReflectionException("未找到该注解中的方法：" + am);
                }

                targetClass = targetClass.getSuperclass();
            }

        } catch (IllegalAccessException e) {
            Exceptions.unchecked(e);
        } catch (InvocationTargetException e) {
            Exceptions.unchecked(e);
        }

        return value;
    }

    public static Field getAnnotatedField(Object target, Class<? extends Annotation> annotationClass) {
        Field value = null;
        Class<?> targetClass = target.getClass();

        loop : while (targetClass != null && !targetClass.equals(Object.class)) {
            Field[] fields = targetClass.getDeclaredFields();

            for (Field field : fields) {
                Annotation annotation = field.getAnnotation(annotationClass);

                if (annotation != null) {
                    field.setAccessible(true);
                    value = field;
                    break loop;
                }
            }

            targetClass = targetClass.getSuperclass();
        }

        return value;
    }

    public static String getAnnotatedFieldName(Object target, Class<? extends Annotation> annotationClass) {
        Field field = getAnnotatedField(target, annotationClass);
        return field.getName();
    }

    public static Object getAnnotatedFieldValue(Object target, Class<? extends Annotation> annotationClass) {
        Object value = null;

        try {
            Field field = getAnnotatedField(target, annotationClass);
            value = field.get(target);
            if (value != null) {
                if ((value instanceof String && StringUtils.isEmpty((String) value))
                        || (value instanceof Number && value.equals(0)))
                    value = null;

            }
        } catch (IllegalAccessException e) {
            Exceptions.unchecked(e);
        }

        return value;
    }

    public static String getAnnotatedFieldURLParam(Object target, Class<? extends Annotation> annotationClass) {
        String param = getAnnotatedFieldName(target, annotationClass) + "=" + getAnnotatedFieldValue(target, annotationClass);
        return param;
    }

    public static Map<String, Object> entity2Map(Object entity) {
        Map<String, Object> map = Maps.newHashMap();
        Class<?> entityClass = entity.getClass();
        try {
            while (entityClass != null && !entityClass.equals(Object.class)) {
                Field[] fields = entityClass.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(entity));
                }

                entityClass = entityClass.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            Exceptions.unchecked(e);
        }

        return map;
    }
}
