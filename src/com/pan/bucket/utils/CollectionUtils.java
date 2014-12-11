/**
 * author :  lipan
 * filename :  CollectionUtils.java
 * create_time : 2014年8月29日 上午9:52:37
 */
package com.pan.bucket.utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.util.SparseArray;

/**
 * @author : lipan
 * @create_time : 2014年8月29日 上午9:52:37
 * @desc : 集合工具类
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CollectionUtils
{
    /**
     * Long泛型集合求和
     * 
     * @param c
     * @return
     */
    public static Long getLongListSum(Collection<Long> c)
    {
        Long sum = 0l;
        for (Long i : c)
        {
            sum += i;
        }
        return sum;
    }

    /**
     * Long泛型集合求平均值
     * 
     * @param c
     * @return
     */
    public static Long getLongListAvg(Collection<Long> c)
    {
        return getLongListSum(c) / c.size();
    }
    
    /**
     * int泛型集合求和
     * 
     * @param c
     * @return
     */
    public static int getIntListSum(Collection<Integer> c)
    {
        int sum = 0;
        for (Integer i : c)
        {
            sum += i;
        }
        return sum;
    }

    /**
     * int泛型集合求平均值
     * 
     * @param c
     * @return
     */
    public static int getIntListAvg(Collection<Integer> c)
    {
        return getIntListSum(c) / c.size();
    }

    /**
     * 检查集合是否为空
     * 
     * @param coll
     * @return
     */
    public static boolean isEmpty(Collection<?> coll)
    {
        return (coll == null || coll.isEmpty());
    }

    /**
     * 数组相加
     * 
     * @param i
     * @return
     */
    public static int getArraySum(int... i)
    {
        int sum = 0;
        for (int j = 0; j < i.length; j++)
        {
            sum += i[j];
        }
        return sum;
    }

    /**
     * 数组平均值
     * 
     * @param i
     * @return
     */
    public static int getArrayAvg(int... i)
    {
        return getArraySum(i) / i.length;
    }

    /**
     * SparseArray 转List
     * 
     * @param sparseArray
     * @return
     */
    public static List sparseArray2List(SparseArray sparseArray)
    {
        List list = new LinkedList();
        for (int i = 0; i < sparseArray.size(); i++)
        {
            list.add(sparseArray.get(sparseArray.keyAt(i)));
        }
        return list;
    }
}
