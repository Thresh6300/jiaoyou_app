package com.qiqi.jiaoyou_app.util;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 类型转换器
 * @Author: cfx
 * @Date: 2020-11-26 18:19
 */
public class Convert extends cn.hutool.core.convert.Convert
{

    public static String toString(Object value)
    {
	  String s = toStr(value);
	  return s.substring(1, s.length() - 1);
    }

    public static List<Long> toLongList(Object value)
    {
	  return Arrays.asList(toLongArray(value));
    }

    public static List<Integer> toIntList(Object value)
    {
	  return Arrays.asList(toIntArray(value));
    }

    public static List<String> toStList(Object value)
    {
	  return Arrays.asList(toStrArray(value));
    }

}
