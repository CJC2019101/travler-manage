package com.cjf.ssm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换
 */
public class DateUtils {
    //日期转换成字符串
    public static String date2String(Date date,String patt){
        SimpleDateFormat format=new SimpleDateFormat(patt);
        return format.format(date);
    }
    //字符串转换成日期
    public static Date String2Date(String str,String patt){
        SimpleDateFormat format=new SimpleDateFormat(patt);
        Date date=null;
        try {
            date= format.parse(str);
        } catch (ParseException e) {
            System.out.println("字符串转换成日期错误。"+e);
        }
        return date;
    }
}
