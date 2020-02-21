package com.wechat.detal.common.util;


import com.wechat.detal.inter.GetId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{
	
	public final static DecimalFormat FORMAT = new DecimalFormat("0.##");
	
	public final static DecimalFormat FORMAT_00 = new DecimalFormat("0.00");
	public final static DecimalFormat FORMAT_0 = new DecimalFormat("0.0");
	
	private static Log log = LogFactory.getLog(NumberUtils.class);
	
	public static int primitive(Integer integer){
		return integer == null?0:integer.intValue();
	}

	public static int primitive(Integer integer, int defaultNum){
		return integer == null?defaultNum:integer.intValue();
	}
	
	public static long primitive(Long long1){
		return long1 == null?0:long1.longValue();
	}
	
	public static double primitive(Double double1){
		return double1 == null?0:double1.doubleValue();
	}
	
	public static float primitive(Float float1){
		return float1 == null?0:float1.floatValue();
	}
	
	/**
	 * 字符串解析成数字， 不报错
	 * 解析失败为空
	 * author lijuntao
	 * date 2018年4月16日
	 */
	public static Integer parseInteger(String str, boolean isLog){
		if(CommonUtils.empty(str))
			return null;
		try{
			return Integer.parseInt(str);
		}catch(Exception e){
			if(isLog){
//				log.error(String.format("[%s]字符串解析成Integer失败", str));
			}
		}
		return null;
	}
	
	/**
     * 字符串解析成数字， 不报错
     * 解析失败为空
     * author lijuntao
     * date 2018年4月16日
     */
    public static Integer parseInteger(String str){
    	return parseInteger(str, false);
    }

	/**
	 * 字符串解析成数字， 不报错
	 * 解析失败为空
	 * author lijuntao
	 * date 2018年4月16日
	 */
	public static Float parseFloat(String str){
		if(CommonUtils.empty(str))
			return null;
		try{
			return Float.parseFloat(str);
		}catch(Exception e){
//    		log.error(String.format("[%s]字符串解析成Double失败", str));
		}
		return null;
	}

    /**
     * 字符串解析成数字， 不报错
     * 解析失败为空
     * author lijuntao
     * date 2018年4月16日
     */
    public static Double parseDouble(String str){
    	if(CommonUtils.empty(str))
    		return null;
    	try{
    		return Double.parseDouble(str);
    	}catch(Exception e){
//    		log.error(String.format("[%s]字符串解析成Double失败", str));
    	}
    	return null;
    }
    /**
     * 字符串解析成数字，解析失败警告
     * author lijuntao
     * date 2018年4月16日
     */
    public static Double parseDouble(String str, String message){
    	if(CommonUtils.empty(str))
    		return null;
    	try{
    		return Double.parseDouble(str);
    	}catch(Exception e){
//    		log.warn(String.format("%s：内容[%s]解析成Double失败", message, str));
    	}
    	return null;
    }
    
    /**
     * 字符串解析成数字， 不报错
     * 解析失败为空
     * author lijuntao
     * date 2018年4月16日
     */
    public static Long parseLong(String str){
    	if(CommonUtils.empty(str))
    		return null;
    	try{
    		return Long.parseLong(str);
    	}catch(Exception e){
//    		log.error(String.format("[%s]字符串解析成Long失败", str));
    	}
    	return null;
    }

    /**
    *@Author sugx
    *@Description 对象解析成Long
    *@Date 2019年07月03日 16:00 
    **/     

	public static Long parseLong(Object obj){
    	if(obj==null){return null;}
		try{
			return  Long.parseLong(obj.toString());
		}catch(Exception e){
//			log.error(String.format("[%s]解析成Long失败", obj.toString()));
			return null;
		}
	}
	
    /**
     * 字符串解析成数字， 不报错
     * 解析失败为空
     * author lijuntao
     * date 2018年4月16日
     */
    public static Boolean parseBoolean(String str){
    	if(CommonUtils.empty(str))
    		return null;
    	try{
    		return Boolean.parseBoolean(str);
    	}catch(Exception e){
//    		log.error(String.format("[%s]字符串解析成Boolean失败", str));
    	}
    	return null;
    }

	public static Double add(Double sum, Double fluidSize) {
		if(sum == null){
			return fluidSize;
		}else if(fluidSize == null){
			return sum;
		}
		return sum + fluidSize;
	}
	
	public static Integer add(Integer sum, Integer fluidSize) {
		if(sum == null){
			return fluidSize;
		}else if(fluidSize == null){
			return sum;
		}
		return sum + fluidSize;
	}
	
	/**
	 * author lijuntao
	 * date 2018年12月20日
	 */
    public static int toInt(String value, int defaultValue){
    	
    	Integer integer = NumberUtils.parseInteger(value);
    	
    	return integer == null? defaultValue:integer;
    }
    
    /**
     * 使用默认金额格式化
     */
    public static String format(BigDecimal bigDecimal){
    	String string = FORMAT.format(bigDecimal);
    	return string;
    }
    
    /**
     * 使用默认金额格式化
     */
    public static String format(Double d){
    	String string = FORMAT.format(d);
    	return string;
    }

	public static boolean primitive(Boolean bool) {
		boolean b = Boolean.TRUE.equals(bool);
		return b;
	}

	/***
	*@Author sugx
	*@Description 除浮点数后面无用的0和.
	*@Date 2019年07月03日 16:02
	**/
	public static String removeZeroAndDot(Double figure){
		String s=String.valueOf(figure);
		if(s.indexOf(".") > 0){
			s = s.replaceAll("0+?$", "");//去掉多余的0
			s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
		}
		return s;

	}
	
	public static <T extends Serializable> double sum(List<T> list, GetId<Double, T> getId){
		double sum = 0;
		for(T record : CommonUtils.nullToEmptyForEach(list)){
			Double value = getId.getId(record);
			sum += primitive(value);
		}
		return sum;
	}
	
	public static <T extends Serializable> int sumInt(List<T> list, GetId<Integer, T> getId){
		int sum = 0;
		for(T record : CommonUtils.nullToEmptyForEach(list)){
			Integer value = getId.getId(record);
			sum += primitive(value);
		}
		return sum;
	}
	
	public static double formatAndParse(Double d, DecimalFormat format){
		double temp = primitive(d);
		String tempStr = format.format(temp);
		d = NumberUtils.parseDouble(tempStr);
		return primitive(d);
	}
	
	public static <T extends Serializable> double sum(Double... values ){
		if(values == null){
			return 0d;
		}
		Double sum = 0d;
		for(Double value : values){
			sum += primitive(value);
		}
		return sum;
	}
	
}
