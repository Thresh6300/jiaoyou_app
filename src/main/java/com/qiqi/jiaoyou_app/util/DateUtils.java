package com.qiqi.jiaoyou_app.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("ALL")
public class DateUtils
{
    public static Date getDateByString(String time)
    {
	  Date date = null;
	  if (time == null)
	  {
		return date;
	  }
	  String date_format = "yyyy-MM-dd HH:mm:ss";
	  SimpleDateFormat format = new SimpleDateFormat(date_format);
	  try
	  {
		date = format.parse(time);
	  }
	  catch (ParseException e)
	  {
		e.printStackTrace();
	  }
	  return date;
    }
    public static String getDate(String format)
    {

	  return new SimpleDateFormat(format).format(new Date());//定义格式，不显示毫秒
    }

    public static String getShortTime(Date date)
    {
	  String shortstring = null;
/*		String time = timestampToStr(dateline);
		Date date = getDateByString(time);*/
	  if (date == null)
	  {
		return shortstring;
	  }

	  long now = Calendar.getInstance().getTimeInMillis();
	  long deltime = (now - date.getTime()) / 1000;
	  // if (deltime > 365 * 24 * 60 * 60)
	  // {
		// shortstring = (int) (deltime / (365 * 24 * 60 * 60)) + "年前";
	  // }
	  // else if (deltime > 24 * 60 * 60)
	  // {
		// shortstring = (int) (deltime / (24 * 60 * 60)) + "天前";
	  // }
	  // else
	  //     if (deltime > 60 * 60)
	      if (deltime > 60 * 60)
	  {
	      if(deltime >= 60*60*12){
		    shortstring = "12小时前";
		}else{
		    shortstring = (int) (deltime / (60 * 60)) + "小时前";
		}

	  }
	  else if (deltime > 60)
	  {
		shortstring = (int) (deltime / (60)) + "分前";
	  }
	  else if (deltime > 1)
	  {
		shortstring = deltime + "秒前";
	  }
	  else
	  {
		shortstring = "1秒前";
	  }
	  return shortstring;
    }

    //Timestamp转化为String:
    public static String timestampToStr(long dateline)
    {
	  Timestamp timestamp = new Timestamp(dateline * 1000);
	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
	  return df.format(timestamp);
    }

    /**
     * 星期几
     *
     * @return
     */
    public static String getWeek(Date date)
    {
	  String[] weekDays1 = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	  String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	  Calendar calendar = Calendar.getInstance();
	  calendar.setTime(date);
	  String week = weekDays1[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	  // System.out.println("今天是" + week);
	  week = "   " + week;
	  return getDate(date) + week;
    }

    /**
     * 星期几
     *
     * @return
     */
    public static String getDate(Date date)
    {
	  return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//定义格式，不显示毫秒
    }

    /**
     * 获取当前时间 之后的时间
     * Date1.after(Date2) , 当Date1大于Date2时，返回TRUE，当小于等于时，返回false；
     * Date1.before(Date2), 当Date1小于Date2时，返回TRUE，当大于等于时，返回false；
     * @return
     */
    public static Date getAfterDate(int amount)
    {
	  Calendar calendar = Calendar.getInstance();
	  calendar.setTime(new Date());
	  calendar.add(Calendar.HOUR, amount);
	  return calendar.getTime();

    }

    public static void main(String[] args)
    {

	 String s= "2020-12-31 10:52:47";
	  SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	  try
	  {
		Date parse = df.parse(s);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parse);
		calendar.add(Calendar.DATE, 1);
		System.out.println(calendar.getTime());
	  }
	  catch (ParseException e)
	  {
		e.printStackTrace();
	  }

    }
    public static Date getTimeMillis(String time)
    {
	  try
	  {
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
		Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time + ":00");
		return curDate;
	  }
	  catch (ParseException e)
	  {
		e.printStackTrace();
	  }

	  return null;

    }
}