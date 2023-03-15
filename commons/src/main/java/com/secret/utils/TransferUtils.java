package com.secret.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TransferUtils {
    public TransferUtils() {
    }

    /**
     *  实体 bean的转换
     * @param t
     * @param k
     * @param <T>
     * @param <K>
     */
    public static <T, K> void transferBean(T t, K k) {
        if (null != t && null != k) {
            BeanCopier bc = BeanCopier.create(t.getClass(), k.getClass(), false);
            bc.copy(t, k, null);
        }
    }

    /**
     * List 之间的转换
     * @param tList
     * @param clazz
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> List<K> transferList(Collection<T> tList, Class<K> clazz) {
        if (!CollectionUtils.isNotEmpty(tList)) {
            return null;
        } else {
            List<K> kList = new ArrayList();
            Iterator var3 = tList.iterator();

            while(var3.hasNext()) {
                T t = (T) var3.next();
                Object tk = null;
                try {
                    tk = clazz.newInstance();
                } catch (Exception var7) {
                    var7.printStackTrace();
                    return null;
                }
                BeanCopier bc = BeanCopier.create(t.getClass(), tk.getClass(), false);
                bc.copy(t, tk, null);
                kList.add((K) tk);
            }
            return kList;
        }
    }
}

