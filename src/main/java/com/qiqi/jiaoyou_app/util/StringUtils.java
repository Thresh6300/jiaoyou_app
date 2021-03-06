package com.qiqi.jiaoyou_app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    /** 驼峰转下划线,效率比上面高 */
    public static String humpToLine(String str) {
	  Matcher matcher = humpPattern.matcher(str);
	  StringBuffer sb = new StringBuffer();
	  while (matcher.find()) {
		matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
	  }
	  matcher.appendTail(sb);
	  return sb.toString();
    }
}
