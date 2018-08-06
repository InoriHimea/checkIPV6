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
    public static <T> void getNewListBySort(List<T> list, String propertyName) {
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
                    PropertyDescriptor pd1 = new PropertyDescriptor(propertyName, t1.getClass());
                    PropertyDescriptor pd2 = new PropertyDescriptor(propertyName, t2.getClass());
                    Method getMethod1 = pd1.getReadMethod();
                    Method getMethod2 = pd2.getReadMethod();
                    Object value1 = getMethod1.invoke(t1);
                    Object value2 = getMethod2.invoke(t2);
                    if (value1 instanceof Integer && value2 instanceof Integer) {
                        int i1 = Integer.parseInt((String) value1);
                        int i2 = Integer.parseInt((String) value2);

                        if (i1 > i2) {
                            return 1;
                        } else if (i1 < i2) {
                            return -1;
                        } else {
                            return 0;
                        }
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
