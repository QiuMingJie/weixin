package com.wechat.detal.common.util;


import com.wechat.detal.exception.ValidateException;

import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

public class Validate {
	
	public Validate falseThrow(boolean bool, String message, Object... params){
		if(!bool){
			if(CommonUtils.notEmpty(params)){
				message = String.format(message, params);
			}
			throw new ValidateException(message);
		}
		return this;
	}
	
	public Validate trueThrow(boolean bool, String message, Object... params){
		if(bool) {
			if(CommonUtils.notEmpty(params)){
				message = String.format(message, params);
			}
			throw new ValidateException(message);
		}
		return this;
	}
	
	public Validate isNotBlank(String str, String message, Object... params){
		if(StringUtils.isBlank(str)) {
			if(CommonUtils.notEmpty(params)){
				message = String.format(message, params);
			}
			throw new ValidateException(message);
		}
		return this;
	}
	
	public Validate isNotEmpty(Object str, String message, Object... params){
		if(CommonUtils.empty(str)){ 
			if(CommonUtils.notEmpty(params)){
				message = String.format(message, params);
			}
			throw new ValidateException(message);
		}
		return this;
	}
	
	public Validate isNotEmpty(Collection<?> collec, String message, Object... params){
		if(CommonUtils.empty(collec)){ 
			if(CommonUtils.notEmpty(params)){
				message = String.format(message, params);
			}
			throw new ValidateException(message);
		}
		return this;
	}
	
	/**
	 * 使用isNotBlank方法
	 * @author lijuntao
	 * @date 2019年8月7日
	 */
	public Validate isNotEmpty(String str, String message, Object... params){
		isNotBlank(str, message, params);
		return this;
	}
	
	/**
	 * 非空， 且日期格式有误
	 * author lijuntao
	 * date 2018年5月16日
	 */
	public Validate isDate(String dateStr, String format, String message){
		if(CommonUtils.notEmpty(dateStr) && DateUtils.parse(dateStr, format) == null) 
			throw new ValidateException(message);
		return this;
	}

	public static ValidateException error(String message) {
		throw new ValidateException(message);
	}

	public void error(Exception e){
		if(e instanceof RuntimeException){
			throw (RuntimeException)e;
		}else{
			throw new ValidateException(e);
		}
	}
	
	public void error(String message, Exception e){
		throw new ValidateException(message, e);
	}
	

	public void between(Date minDate, Date maxDate, Date date, String mgs1, String mgs2){
		isNotEmpty(minDate, "minDate日期不能为空");
		isNotEmpty(maxDate, "maxDate日期不能为空");
		isNotEmpty(date, "date日期不能为空");
		if(date.before(minDate)){
			throw new ValidateException(mgs1);
		}
		if(date.after(maxDate)){
			throw new ValidateException(mgs2);
		}
	}
	
	/**
	 * 日期格式必须小于等于12-31
	 * author lijuntao
	 * date 2019年1月3日
	 */
	public Validate isDateWithMMdd(String mmdd, String message, Object... params){
		mmdd = DateUtils.formatWithMMdd(mmdd);
		if(StringUtils.isBlank(mmdd)){ 
			if(CommonUtils.empty(params)){
				message = String.format(message, params);
			}
			throw new ValidateException(message);
		}
		return this;
	}
	
	/**
	 * 检查年份，为空不检查
	 * author lijuntao
	 * date 2018年11月19日
	 */
	public void checkYYYY(String yyyy, String message){
		if(StringUtils.isNotBlank(yyyy)){
			boolean matches = Pattern.matches("\\d{4}", yyyy);
			if(!matches){
				throw new ValidateException(message);
			}
		}
	}
	
	/**
	 * 检查月日，为空不检查
	 * author lijuntao
	 * date 2018年11月19日
	 */
	public void checkMMdd(String MMdd, String message){
		if(StringUtils.isNotBlank(MMdd)){
			String formatWithMMdd = DateUtils.formatWithMMdd(MMdd);
			if(StringUtils.isBlank(formatWithMMdd)){
				throw new ValidateException(message);
			}
		}
	}
	
	/**
	 * 检查时分，为空不检查
	 * author lijuntao
	 * date 2018年11月19日
	 */
	public void checkHHmm(String HHmm, String message){
		if(StringUtils.isNotBlank(HHmm)){
			String formatWithHHmm = DateUtils.formatWithhhmm(HHmm);
			if(StringUtils.isBlank(formatWithHHmm)){
				throw new ValidateException(message);
			}
		}
	}


	
	/**
	 * 
	 * @author 豪
	 * @date 2019年3月20日
	 */
	public Validate isEmpty(Object str, String message, Object... params){
		if(CommonUtils.notEmpty(str)){ 
			if(CommonUtils.notEmpty(params)){
				message = String.format(message, params);
			}
			throw new ValidateException(message);
		}
		return this;
	}
	/**
	 * 
	 * @author 豪
	 * @date 2019年3月20日
	 */
	public Validate isEmpty(Collection<?> collec, String message, Object... params){
		if(CommonUtils.notEmpty(collec)){ 
			if(CommonUtils.empty(params)){
				message = String.format(message, params);
			}
			throw new ValidateException(message);
		}
		return this;
	}


	/**
	 *
	 * @author sugx
	 * @date 2019年7月15日
	 */
	public Validate checkLength(String str,Integer maxLength,String message, Object... params){
		if(str!=null&&str.length()>maxLength){
			if(CommonUtils.notEmpty(params)){
				message = String.format(message, params);
			}
			throw new ValidateException(message);
		}
		return this;
	}
	


}
