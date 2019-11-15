package com.cjf.ssm.converter;

import org.springframework.core.convert.converter.Converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义类型转换器，将字符串转换为日期
 */
public class StringToDateConverter implements Converter<String , Date> {

    @Override
    public Date convert(String source) {
        System.out.println(source);
        try {
            DateFormat format=null;
            //传入的参数为空，无法转换
            if (source==null)
            {
                throw new NullPointerException("请传入正确的数据。");
            }
            format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return format.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException("请输入正确的日期格式。正确的格式是： yyyy-MM-dd");
        }

    }
}
