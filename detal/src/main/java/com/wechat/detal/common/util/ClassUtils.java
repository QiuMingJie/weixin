package com.wechat.detal.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ClassUtils {
	
	private static Log logger = LogFactory.getLog(ClassUtils.class);

    public final static Validate validate = new Validate();
	
	/**
	 * 反射设值， 必须存在set方法
	 * author lijuntao
	 * date 2018年4月27日
	 */
	public static <T, O> void setValue(T target, String field, O value, Class<O> argClass) {
		setValue(target, field, value, argClass, true);
	}


    /**
     * 自写的，猜类的类型然后进行注入
     * @param target 目标类
     * @param fieldName 成员名
     * @param valueSource 值
     * @param <T>
     * @throws IllegalAccessException
     * @throws ParseException
     */
    public static <T> void setValue(T target, String fieldName, String valueSource) throws IllegalAccessException, ParseException {
        Field field = null;
	    Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals(fieldName)) {
                field = declaredField;
            }
        }
        if (field==null) {
            new  Validate().error("获取不到file"+fieldName);
        }
        //8：获取目标类的属性类型
        String typeTarget = field.getType().toString();
        field.setAccessible(true);
        if ("class java.lang.String".equals(typeTarget) && valueSource != null) {
            field.set(target, String.valueOf(valueSource));
        }
        if ("class java.lang.Integer".equals(typeTarget) && CommonUtils.notBlank(valueSource)) {
            field.set(target, Integer.valueOf(valueSource));
        } else if ("class java.lang.Long".equals(typeTarget) && valueSource != null) {
            field.set(target, Long.valueOf(valueSource));
        } else if ("class java.lang.Double".equals(typeTarget) && valueSource != null) {
            field.set(target, Double.valueOf(valueSource));
        } else if ("class java.math.BigDecimal".equals(typeTarget) && valueSource != null) {
            field.set(target, new BigDecimal(valueSource));
        } else if ("class java.util.Date".equals(typeTarget) && valueSource != null) {
            Date date = null;
            if (!valueSource.contains(":")) {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(valueSource);
                field.set(target, date);
            } else if (valueSource.contains(":") && valueSource.length() < 18) {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(valueSource);
                field.set(target, date);
            } else if (valueSource.contains(":") && valueSource.length() > 18) {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(valueSource);
                field.set(target, date);
            } else {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(valueSource);
                field.set(target, date);
            }
        }

    }
	
	public static <T, O> void setValue(T target, String field, O value, Class<O> argClass, boolean throwException) {
		try {
			Class<? extends Object> targetClass = target.getClass();
			Method method;
			field = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
			method = targetClass.getMethod(field, argClass);
            new Validate().isNotEmpty(method, "");
			method.invoke(target, value);
		} catch (Exception e) {
            new Validate().trueThrow(throwException, String.format("没有找到%s方法", field));
		}
	}
	
	/**
	 * 反射设值， 必须存在get方法
	 * author lijuntao
	 * date 2018年8月21日
	 */
	public static <T, K> K getValue(T t, String field) {
		return getValue(t, field, true);
	}
	
	@SuppressWarnings("unchecked")
	public static <T, K> K getValue(T t, String field, boolean throwException) {
		try {
			Class<? extends Object> class1 = t.getClass();
			Method method;
			field = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
			method = class1.getMethod(field);
			Object object = method.invoke(t);
			return (K) object;
		} catch (Exception e) {
			String errorMessage = String.format("反射获取属性失败：%s, field:%s", JsonUtils.getString(t), JsonUtils.getString(field));
			new Validate().trueThrow(throwException, errorMessage);
			logger.error(errorMessage);
			return null;
		}
	} 
	
	/**
	 * 获取存在某个注解的字段
	 * getSupers： 是否要获取父类的字符
	 * author lijuntao
	 * date 2018年5月17日
	 */
	public static <T extends Annotation> List<Field> getFieldByAnnotation(Class<?> clazz, Class<T> anno, boolean getSupers){
		List<Field> list = Lists.newArrayList();
		
		Field[] fields = clazz.getDeclaredFields();
		if(fields != null){
			for(Field field : fields){
				list.add(field);
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		while(superClazz != Object.class && getSupers){
			Field[] superFields = superClazz.getDeclaredFields();
			if(superFields != null){
				for(Field field : superFields){
					list.add(field);
				}
			}
			superClazz = superClazz.getSuperclass();
		}
		
		Iterator<Field> iterator = list.iterator();
		
		while(iterator.hasNext()){
			Field field = iterator.next();
			if(field.getAnnotation(anno) == null){
				iterator.remove();
			}
		}
		
		return list;
	}
	
	public static String getGenericClassName(Class<?> clazz){
		return getGenericClassName(clazz, 0);
	}
	
	public static Class<?> getGenericClass(Class<?> clazz){
		return getGenericClass(clazz, 0);
	}
	
	public static String getGenericClassName(Class<?> clazz, int index){
		return getGenericClass(clazz, index).getSimpleName();
	}
	
	public static Class<?> getGenericClass(Class<?> clazz, int index){
		Type type = clazz.getGenericSuperclass();
		if( type instanceof ParameterizedType ){
			ParameterizedType pType = (ParameterizedType)type;
			Type claz = pType.getActualTypeArguments()[index];
			if( claz instanceof Class ){
				clazz = (Class<?>) claz;
			}
		}
		new Validate().isNotEmpty(clazz, "获取类泛型");
		return clazz;
	}
	
	public static Type getGenericType(Class<?> clazz, int index){
		Type type = clazz.getGenericSuperclass();
		new Validate().isNotEmpty(type, "获取类泛型失败");
		if(type instanceof ParameterizedType ){
			ParameterizedType pType = (ParameterizedType)type;
			type = pType.getActualTypeArguments()[index];
		}
		return type;
	}
	
	public static Type getGenericType(Class<?> clazz){
		return getGenericType(clazz, 0);
	}
	
	/**
	 * 获取存在某个注解的字段
	 * getSupers： 是否要获取父类的字符
	 * author lijuntao
	 * date 2018年5月17日
	 */
	public static List<Field> getField(Class<?> clazz, boolean getSupers){
		
		List<Field> list = Lists.newArrayList();
		
		Field[] fields = clazz.getDeclaredFields();
		if(fields != null){
			for(Field field : fields){
				list.add(field);
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		while(superClazz != Object.class && getSupers){
			Field[] superFields = superClazz.getDeclaredFields();
			if(superFields != null){
				for(Field field : superFields){
					list.add(field);
				}
			}
			superClazz = superClazz.getSuperclass();
		}
		
		return list;
	}
	
	/**
	 * 获取方法，只获取公共方法
	 * author lijuntao
	 * date 2019年1月25日
	 */
	public static Method getPublicMethod(Class<?> clazz, String methodName, Class<?>... classParams){
		try {
			Method method = null;
			if(classParams == null || classParams.length == 0){
				method = clazz.getMethod(methodName);
			}else{
				method = clazz.getMethod(methodName, classParams);
			}
			return method;
		} catch (Exception e) {
//			String className = clazz == null? "class为空": clazz.getSimpleName();
//			logger.error(String.format("获取方法异常：参数[%s，%s]", className, methodName));
		}
		return null;
	}
	
	/**
	 * 获取字段，可以为空
	 * 私有和父类的字段也可以获取
	 * author lijuntao
	 * date 2019年1月25日
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static Field getField(Class<?> clazz, String fieldName){
		try{
			Field field = clazz.getDeclaredField(fieldName);
			if(field != null){
				return field;
			}
			Class<?> superClazz = clazz.getSuperclass();
			while(superClazz != Object.class){
				field = clazz.getDeclaredField(fieldName);
				if(field != null){
					return field;
				}
				superClazz = superClazz.getSuperclass();
			}
		}catch(Exception e){
//			String className = clazz == null? "class为空": clazz.getSimpleName();
//			logger.error(String.format("获取字段异常：参数[%s，%s]", className, fieldName));
		}
		return null;
	}
	
	/**
	 * 获取字段get方法
	 * author lijuntao
	 * date 2019年1月25日
	 */
	public static Method getGetMethod(Class<?> clazz, String fieldName){
		
		String getMethodName = "get" + StringUtils.firstUpper(fieldName);
		Method method = getPublicMethod(clazz, getMethodName, (Class<?>[])null);
		if(method != null){
			return method;
		}
			
		Field field = getField(clazz, fieldName);
		if(field == null){
			return null;
		}
			
		Class<?> type = field.getType();
		if(type.equals(boolean.class)){
			getMethodName = "is" + StringUtils.firstUpper(fieldName);
			return getPublicMethod(clazz, getMethodName, (Class<?>[])null);
		}
		
		return null;
	}
	
	/**
	 * 获取字段set方法
	 * author lijuntao
	 * date 2019年1月25日
	 */
	public static Method getSetMethod(Class<?> clazz, String fieldName){
		Field field = getField(clazz, fieldName);
		if(field == null){
			return null;
		}
		String getMethodName = "set" + StringUtils.firstUpper(fieldName);
		Method method = getPublicMethod(clazz, getMethodName, field.getType());
		return method;
	}
	
	/**
	 * 获取字段get方法
	 * author lijuntao
	 * date 2019年1月25日
	 */
	public static List<Method> getGetMethod(Class<?> clazz, List<String> fieldNames, boolean nullThrow){
		if(CommonUtils.notEmpty(fieldNames)){
			return getGetMethod(clazz, fieldNames.toArray(new String[fieldNames.size()]), nullThrow);
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 获取字段set方法
	 * author lijuntao
	 * date 2019年1月25日
	 */
	public static List<Method> getSetMethod(Class<?> clazz, List<String> fieldNames, boolean nullThrow){
		if(CommonUtils.notEmpty(fieldNames)){
			return getSetMethod(clazz, fieldNames.toArray(new String[fieldNames.size()]), nullThrow);
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 获取字段get方法
	 * author lijuntao
	 * date 2019年1月25日
	 */
	public static List<Method> getGetMethod(Class<?> clazz, String[] fieldNames, boolean nullThrow){
		List<Method> list = Lists.newArrayList();
		for(int i=0; i< CommonUtils.size(fieldNames); i++){
			Method method = getGetMethod(clazz, fieldNames[i]);
			validate.trueThrow(nullThrow && method == null, "无法找到字段%s的获值方法", fieldNames[i]);
			list.add(method);
		}
		return list;
	}
	
	/**
	 * 获取字段set方法
	 * author lijuntao
	 * date 2019年1月25日
	 */
	public static List<Method> getSetMethod(Class<?> clazz, String[] fieldNames, boolean nullThrow){
		List<Method> list = Lists.newArrayList();
		for(int i=0; i< CommonUtils.size(fieldNames); i++){
			Method method = getSetMethod(clazz, fieldNames[i]);
			validate.trueThrow(nullThrow && method == null, "无法找到字段%s的设值方法", fieldNames[i]);
			list.add(method);
		}
		return list;
	}
	
	/**
	 * 反射设值， 必须存在set方法
	 * author lijuntao
	 * date 2018年4月27日
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> V getValue(T target, Method method, boolean throwException){
		try{
			validate.isNotEmpty(target, "对象不能为空");
			validate.isNotEmpty(method, "方法不能为空");
			Object invoke = method.invoke(target, (Object[])null);
			return (V) invoke;
		}catch(RuntimeException e){
			if(throwException){
				throw e;
			}else{
				logger.warn("执行方法有误", e);
			}
		}catch(Exception e){
			if(throwException){
				throw new RuntimeException(e);
			}else{
				logger.warn("执行方法有误", e);
			}
		}
		return null;
	}
	
	/**
	 * 反射设值， 必须存在set方法
	 * author lijuntao
	 * date 2018年4月27日
	 */
	public static <T, V> boolean setValue(T target, Method method, V value, boolean throwException){
		try{
			validate.isNotEmpty(target, "对象不能为空");
			validate.isNotEmpty(method, "方法不能为空");
			method.invoke(target, value);
			return true;
		}catch(RuntimeException e){
			if(throwException){
				throw e;
			}else{
				logger.warn("执行方法有误", e);
			}
		}catch(Exception e){
			if(throwException){
				throw new RuntimeException(e);
			}else{
				logger.warn("执行方法有误", e);
			}
		}
		return false;
	}
	
	/**
     * 获取当前对象【fieldName】属性的值
     * @param obj 对象
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static Object getClassValue(Object obj, String fieldName) throws Exception{
        if (obj == null || StringUtils.isEmpty(fieldName)) return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i  = 0; i < fields.length; i++){
        	if(fieldName.equals(fields[i].getName())){
        		// 获取对象的属性
                Field field = obj.getClass().getDeclaredField(fieldName);
                // 对象的属性的访问权限设置为可访问
                field.setAccessible(true);
                // 返回此属性的值
                return field.get(obj);
        	}
		}
        return null;
    }
    
    /**
     * 设置当前对象【fieldName】属性的值
     * @param obj 对象
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static Object setClassValue(Object obj, String fieldName,String value) throws Exception{
        if (obj == null || StringUtils.isEmpty(fieldName)) return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        for(int i  = 0; i < fields.length; i++){
        	if(fieldName.equals(fields[i].getName())){
        		// 获取对象的属性
                Field field = obj.getClass().getDeclaredField(fieldName);
                // 对象的属性的访问权限设置为可访问
                field.setAccessible(true);
                // 设置此属性的值
                field.set(obj, value);
                return field.get(obj);
        	}
		}
        return null;
    }
    
    
}
