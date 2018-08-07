package org.inori.app.util;

import edu.emory.mathcs.backport.java.util.Collections;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2018/8/6 19:40
 * @since jdk1.8
 */
public class CollectionUtils {

    /**
     * 比较list中某个属性，然后排序
     * @param list
     * @param propertyName
     * @param <T>
     */
    public static <T> void getNewListBySort(List<T> list, String... propertyName) {
        Collections.sort(list, new Comparator<T>() {

            /**
             * 根据某个属性排序
             * @param t1
             * @param t2
             * @return
             */
            @Override
            public int compare(T t1, T t2) {

                try {
                    int index = 0;
                    PropertyDescriptor pd1 = null;
                    PropertyDescriptor pd2 = null;
                    Object value1 = null;
                    Object value2 = null;
                    for (String pN : propertyName) {
                        if (index == 0) {
                            pd1 = new PropertyDescriptor(pN, t1.getClass());
                            pd2 = new PropertyDescriptor(pN, t2.getClass());
                        } else {
                            pd1 = new PropertyDescriptor(pN, value1.getClass());
                            pd2 = new PropertyDescriptor(pN, value2.getClass());
                        }

                        Method getMethod1 = pd1.getReadMethod();
                        Method getMethod2 = pd2.getReadMethod();

                        if (index == 0) {
                            value1 = getMethod1.invoke(t1);
                            value2 = getMethod2.invoke(t2);
                        } else {
                            value1 = getMethod1.invoke(value1);
                            value2 = getMethod2.invoke(value2);
                        }

                        index ++;
                    }

                    if (value1 instanceof Integer && value2 instanceof Integer) {
                        int i1 = Integer.parseInt(String.valueOf(value1));
                        int i2 = Integer.parseInt(String.valueOf(value2));

                        if (i1 > i2) {
                            return 1;
                        } else if (i1 < i2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    } else if (value1 instanceof Long && value2 instanceof Long) {

                    } else if (value1 instanceof Short && value2 instanceof Short) {

                    } else if (value1 instanceof Character && value2 instanceof Character) {
                        int compare = Character.compare(((Character) value1).charValue(), ((Character) value2).charValue());

                        if (compare > 0) {
                            return 1;
                        } else if (compare < 0) {
                            return -1;
                        } else {
                            return 0;
                        }
                    } else if (value1 instanceof Byte && value2 instanceof Byte) {

                    } else if (value1 instanceof Boolean && value2 instanceof Boolean) {
                        new IllegalArgumentException("布尔类型不提供比较大小");
                    } else if (value1 instanceof Float && value2 instanceof Float) {

                    } else if (value1 instanceof String && value2 instanceof String) {
                        new IllegalArgumentException("暂不支持String类型比较大小");
                    } else {
                        new IllegalArgumentException("所传入参数类型不同，或有一个或两个不属于已匹配类型的实例，无法比较大小");
                    }
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                return 0;
            }
        });
    }
}
