package com.wechat.detal.common.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.wechat.detal.inter.GetId;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.time.DateUtils.setMilliseconds;
import static org.apache.commons.lang3.time.DateUtils.setSeconds;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

/**
 * 基类 Base
 * 
 */
public class CommonUtils {
	
	private final static String[] EMPTY_ARRAY = new String[0];
	
	private final static Log log = LogFactory.getLog(CommonUtils.class);

    /**
     * 获取日期
     * @return
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 获取日期
     * @return
     */
    public static Date getMinuteDate() {
        return setSeconds(setMilliseconds(getDate(), 0), 0);
    }
    
    /**
     * 非空格
     * @param var
     * @return
     */
    public static boolean notBlank(String var) {
        return StringUtils.isNotBlank(var);
    }

    /**
     * 非空格
     * @param var
     * @return
     */
    public static boolean blank(String var) {
    	return StringUtils.isBlank(var);
    }

    /**
     * 非空
     * @param var
     * @return
     */
    public static boolean notEmpty(String var) {
        return isNotBlank(var);
    }

    /**
     * 空
     * @param var
     * @return
     */
    public static boolean empty(String var) {
        return isBlank(var);
    }

    /**
     * 非空
     * @param var
     * @return
     */
    public static boolean notEmpty(long var) {
        return 0 != var;
    }

    /**
     * 空
     * @param var
     * @return
     */
    public static boolean empty(long var) {
        return 0 == var;
    }

    /**
     * 非空
     * @param var
     * @return
     */
    public static boolean notEmpty(int var) {
        return 0 != var;
    }

    /**
     * 空
     * @param var
     * @return
     */
    public static boolean empty(int var) {
        return 0 == var;
    }

    /**
     * 非空
     * @param var
     * @return
     */
    public static boolean notEmpty(Object var) {
        return null != var;
    }
    
    /**
     * 非空或者非空字符串
     * @param obj
     * @return
     */
    public static boolean notEmptyOrBlank(Object obj) {
    	if(obj == null){
    		return false;
    	}
    	if(obj instanceof CharSequence){
    		return StringUtils.isNotBlank((CharSequence)obj);
    	}else if(obj instanceof Collection){
    		return notEmpty((Collection<?>)obj);
    	}else if(obj instanceof Map){
    		return notEmpty((Map<?, ?>)obj);
    	}else if(obj instanceof Object[]){
    		return notEmpty((Object[])obj);
    	}
    	return true;
    }

    /**
     * 空
     * @param var
     * @return
     */
    public static boolean empty(Object var) {
        return null == var;
    }

    /**
     * 非空
     * @param var
     * @return
     */
    public static boolean notEmpty(Collection<?> var) {
        return null != var && !var.isEmpty();
    }

    /**
     * 空
     * @param var
     * @return
     */
    public static boolean empty(Collection<?> var) {
        return null == var || var.isEmpty();
    }

    /**
     * 非空
     * @param var
     * @return
     */
    public static boolean notEmpty(Map<?, ?> var) {
        return null != var && !var.isEmpty();
    }

    /**
     * 空
     * @param var
     * @return
     */
    public static boolean empty(Map<?, ?> var) {
        return null == var || var.isEmpty();
    }

    /**
     * 非空
     * @param file
     * @return
     */
    public static boolean notEmpty(File file) {
        return null != file && file.exists();
    }

    /**
     * 空
     * @param file
     * @return
     */
    public static boolean empty(File file) {
        return null == file || !file.exists();
    }

    /**
     * 非空
     * @param var
     * @return
     */
    public static boolean notEmpty(Object[] var) {
        return null != var && 0 < var.length;
    }

    /**
     * 空
     * @param var
     * @return
     */
    public static boolean empty(Object[] var) {
        return null == var || 0 == var.length;
    }
    
    /**
     * 非空
     * @param var
     * @return
     */
    public static boolean notEmpty(Set<?> var) {
        return null != var && !var.isEmpty();
    }

    /**
     * 空
     * @param var
     * @return
     */
    public static boolean empty(Set<?> var) {
        return null == var || var.isEmpty();
    }
    


    
    /**
     * 获取实体集合的所有id
     * author lijuntao
     * date 2017年11月23日
     */
    public static <K extends Serializable, T extends Serializable> Set<K> getIdSet(Collection<T> entityList, GetId<K, T> getId){
    	HashSet<K> idSet = Sets.newLinkedHashSet();
    	if(notEmpty(entityList)){
    		for(T entity : entityList){
    			idSet.add(getId.getId(entity));
    		}
    	}
    	return idSet;
    }
    
    /**
     * 获取实体集合的所有id
     * author lijuntao
     * date 2017年11月23日
     */
    public static <K extends Serializable, T extends Serializable> Set<K> getIdSet(Collection<T> entityList, GetId<K, T> getId, boolean removeNull){
    	HashSet<K> idSet = Sets.newLinkedHashSet();
    	if(notEmpty(entityList)){
    		for(T entity : entityList){
    			K value = getId.getId(entity);
    			if(removeNull && CommonUtils.empty(value)){
    				continue;
    			}
    			idSet.add(getId.getId(entity));
    		}
    	}
    	return idSet;
    }
    
    /**
     * 获取实体集合
     * author lijuntao
     * date 2017年11月23日
     */
    public static <K extends Serializable, T extends Serializable> List<K> getList(Collection<T> entityList, GetId<K, T> getId){
    	List<K> list = Lists.newArrayList();
    	if(notEmpty(entityList)){
    		for(T entity : entityList){
    			list.add(getId.getId(entity));
    		}
    	}
    	return list;
    }
    


    
    /**
     * 将实体集合转为Map，key为实体ID
     * author lijuntao
     * date 2017年11月23日
     */
    public static <K extends Serializable, T> Map<K, T> getIdMap(Collection<T> entityList, GetId<K, T> getBaseId){
    	Map<K, T> idMap = Maps.newLinkedHashMap();
    	if(notEmpty(entityList)){
    		for(T entity : entityList){
    			idMap.put(getBaseId.getId(entity), entity);
    		}
    	}
    	return idMap;
    }
    
    /**
     * 将实体集合转为Map，key为实体ID
     * author lijuntao
     * date 2017年11月23日
     */
    public static <K extends Serializable, V extends Serializable, T extends Serializable> Map<K, V> toMap(Collection<T> entityList, GetId<K, T> key, GetId<V, T> value){
    	Map<K, V> idMap = Maps.newLinkedHashMap();
    	if(notEmpty(entityList)){
    		for(T t : entityList){
    			idMap.put(key.getId(t), value.getId(t));
    		}
    	}
    	return idMap;
    }
    
    /**
     * 将实体集合转为Map，key为实体ID
     * author lijuntao
     * date 2017年11月23日
     */
    public static <K extends Serializable, V extends Serializable, T extends Serializable> Map<K, V> toMapRemoveEmpty(Collection<T> entityList, GetId<K, T> key, GetId<V, T> value){
    	Map<K, V> idMap = Maps.newLinkedHashMap();
    	if(empty(entityList)){
    		return idMap;
    	}
		for(T t : entityList){
			K k = key.getId(t);
			V v = value.getId(t);
			if(CommonUtils.empty(k) || CommonUtils.empty(v)){
				continue;
			}
			idMap.put(k, v);
		}
    	return idMap;
    }
    
    /**
     * 使用java的序列化实现深层复制
     * author lijuntao
     * date 2018年4月10日
     */
    @SuppressWarnings("unchecked")
	public static <T extends Serializable> T deepClone(T a){
    	T t = null;
    	if(a != null){
    		try{
    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ObjectOutputStream oos = new ObjectOutputStream(baos);
    			oos.writeObject(a);
    			oos.flush();
    			oos.close();
    			byte[] arrByte = baos.toByteArray();
    			ByteArrayInputStream bais = new ByteArrayInputStream(arrByte);
    			ObjectInputStream ois = new ObjectInputStream(bais);
    			t = (T)ois.readObject();
    			ois.close();
    		}catch(Exception e){
    		}
    	}
	    return t;
	}
    
    /**
     * 空对象转为空字符串
     * author lijuntao
     * date 2017年12月7日
     */
    public static String nullToEmpty(String str){
    	return str == null?"":str;
    }
    
    /**
     * 空对象转为空字符串
     * author lijuntao
     * date 2017年12月7日
     */
    public static Long nullToEmpty(Long long1){
    	return long1 == null?Long.valueOf(0):long1;
    }
    
    /**
     * 空集合对象转为无元素的空集合
     * author lijuntao
     * date 2017年12月7日
     */
    public static <T> List<T> nullToEmpty(List<T> list){
    	return list == null?Lists.<T>newArrayList():list;
    }
    
    /**
     * Null转为无元素的Map对象
     * author lijuntao
     * date 2017年12月7日
     */
    public static <K, V> Map<K, V> nullToEmpty(Map<K, V> map){
    	return map == null?Maps.<K, V>newHashMap():map;
    } 
    
	public static String[] nullToEmpty(String[] lastIgnores) {
		return lastIgnores == null?new String[0]:lastIgnores;
	}
	
	/**
	 * 为避免空对象循环异常
	 * author lijuntao
	 * date 2017年12月7日
	 */
	public static <T> String[] nullToEmptyForEach(String[] strs){
		return strs == null? EMPTY_ARRAY:strs;
	}
	
	/**
	 * 为避免空对象循环异常
	 * author lijuntao
	 * date 2017年12月7日
	 */
	@SuppressWarnings("unchecked")
	public static <T> Set<T> nullToEmptyForEach(Set<T> set){
		return set == null?Collections.EMPTY_SET:set;
	}
	
	/**
	 * 空集合对象转为无元素的空集合
	 * author lijuntao
	 * date 2017年12月7日
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> nullToEmptyForEach(List<T> collection){
		return collection == null?Collections.EMPTY_LIST:collection;
	} 
	
	/**
	 * 空集合对象转为无元素的空集合
	 * author lijuntao
	 * date 2017年12月7日
	 */
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> nullToEmptyForEach(Collection<T> collection){
		return collection == null?Collections.EMPTY_LIST:collection;
	} 
	
	/**
	 * 空集合对象转为无元素的空集合
	 * author lijuntao
	 * date 2017年12月7日
	 */
	public static <T> Collection<T> nullToEmpty(Collection<T> collection){
		return collection == null?Lists.<T>newArrayList():collection;
	} 
	
    /**
     * 获取字符串最大长度
     * author lijuntao
     * date 2017年12月7日
     */
    public static String subMaxString(String str, int maxLength){
    	return (str == null || str.length() <= maxLength)?str:str.substring(0, maxLength);
    }
    
    /**
     * 获取字符串最大长度，从后往前截取
     * author lijuntao
     * date 2017年12月7日
     */
    public static String subMaxStringFromBack(String str, int maxLength){
    	return (str == null || str.length() <= maxLength)?str:str.substring(str.length()-maxLength, str.length());
    }
    
    /**
     * 空集合对象转为无元素的空集合
     * author lijuntao
     * date 2017年12月7日
     */
    public static <T> Set<T> nullToEmpty(Set<T> set){
    	return set == null?Sets.<T>newHashSet():set;
    }


    /**
     * 求两个时间的时间差
     * 返回时间单位：分
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     */
    public static long getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long result=0;
        boolean equalResult=false;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
                equalResult=true;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (true==equalResult){
            result =(day * 24 * 60 +hour * 60 +min + sec / 60) * (-1) ;
        }
       else{
             result= day * 24 * 60 +hour * 60 +min + sec / 60;
        }
        return result;
    }

    public  static  String DateToStr(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
         String str=sdf.format(date);
         return str;
    }

    public  static  String DateToStrDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd ");;
        String str=sdf.format(date);
        return str;
    }
    public  static  Date StrToDate(String str) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date= null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public  static  Date StrDateToDate(String str) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date= null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String getStackMessage(Exception e){
		try {
			ByteArrayOutputStream opStream = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(opStream, false, "utf-8"));
			return opStream.toString("utf-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "";
	}
    
    /**
     * 比较实现Comparable接口的类的两个对象
     * 如果其中某个为空，另一个非空， 则根据nullFirst， 来决定空对象和非空对象的大小
     * @Param nullFirst：当为true， 代表空对象排在前面， 空对象要比非空对象小
     * author lijuntao
     * date 2018年4月10日
     */
    public static <T> int compare(Comparable<T> s1, T s2, boolean nullFirst, boolean desc){
    	if(s1 == null){
			if(s2 == null){
				return 0;
			}
			return nullFirst?-1:1;
		}else{
			if(s2 == null){
				return nullFirst?1:-1;
			}
			return desc? 0 - s1.compareTo(s2): s1.compareTo(s2);
		}
    }
    
    /**
     * 比较实现Comparable接口的类的两个对象
     * 如果其中某个为空，另一个非空， 则根据nullFirst， 来决定空对象和非空对象的大小
     * @Param nullFirst：当为true， 代表空对象排在前面， 空对象要比非空对象小
     * author lijuntao
     * date 2018年4月10日
     */
    public static <T> int compare(Comparable<T> s1, T s2, boolean nullFirst){
    	return compare(s1, s2, nullFirst, false);
    }
    
    /**
     * 默认空顺序排在最后
     * author lijuntao
     * date 2018年4月2日
     */
    public static <T> int compare(Comparable<T> s1, T s2){
    	return compare(s1, s2, false, false);
    }
    
    /**
     * 获取最小值，忽略空值
     * author lijuntao
     * date 2018年4月2日
     */
    public static <T> T minIgnoreNull(List<T> list, Comparator<T> compare){
    	if(CommonUtils.size(list) == 0){
    		return null;
    	}else if(CommonUtils.size(list) == 1){
    		return list.get(0);
    	}
    	T min = null;
    	for(int i=0; i< CommonUtils.size(list); i++){
    		T temp = list.get(i);
    		if(min == null){
    			min = temp;
    		}else if(temp != null && compare.compare(min, temp) > 0){
    			min = temp;
    		}
    	}
    	return min;
    }
    
    /**
     * 获取最大值，忽略空值
     * author lijuntao
     * date 2018年4月2日
     */
    public static <T> T maxIgnoreNull(List<T> list, Comparator<T> compare){
    	if(CommonUtils.size(list) == 0){
    		return null;
    	}else if(CommonUtils.size(list) == 1){
    		return list.get(0);
    	}
    	T min = null;
    	for(int i=0; i< CommonUtils.size(list); i++){
    		T temp = list.get(i);
    		if(min == null){
    			min = temp;
    		}else if(temp != null && compare.compare(min, temp) < 0){
    			min = temp;
    		}
    	}
    	return min;
    }
    
    
    /**
     * 默认空顺序排在最后
     * author lijuntao
     * date 2018年4月2日
     */
    public static <T> int compareNull(T s1, T s2){
    	return compareNull(s1, s2, false);
    }
    
    /**
     * 默认空顺序排在最后
     * author lijuntao
     * date 2018年4月2日
     */
    public static <T> int compareNull(T s1, T s2, boolean nullFirst){
    	if(s1 == null && s2 == null){
			return 0;
		}
    	if(s1 != null && s2 != null){
    		return 0;
    	}
    	if(s1 == null){
    		return nullFirst?-1:1;
    	}
    	return nullFirst?1:-1;
    }
    
    public static String subTextString(String str,int len){
        if(str.length()<len/2)
            return str;
        int count = 0;
        StringBuffer sb = new StringBuffer();
        String[] ss = str.split("");
        for(int i=0;i<ss.length;i++){
            count+=ss[i].getBytes().length>1?2:1;
            sb.append(ss[i]);
            if(count>=len)break;
        }
        //不需要显示...的可以直接return sb.toString();(sb.toString().length()<str.length())?sb.append("...").toString():str
        return sb.toString();
    }
    
    /**
     * 字符串解析成数字， 不报错
     * 解析失败为空
     * author lijuntao
     * date 2018年4月16日
     */
    public static Integer parseInteger(String str){
    	if(CommonUtils.empty(str))
    		return null;
    	try{
    		return Integer.parseInt(str);
    	}catch(Exception e){
//    		log.error(String.format("[%s]字符串解析成Integer失败", str));
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
    
    public static <K extends Serializable, V extends Serializable> LinkedHashMap<K, List<V>> group(List<V> list, GetId<K, V> getId){
    	LinkedHashMap<K, List<V>> groupMap = Maps.newLinkedHashMap();
    	if(notEmpty(list)){
    		for(V entity : list){
    			K key = getId.getId(entity);
    			List<V> groupList = groupMap.get(key);
    			if(groupList == null){
    				groupList = Lists.newArrayList();
    				groupMap.put(key, groupList);
    			}
    			groupList.add(entity);
    		}
    	}
    	return groupMap;
    }
    
    public static <T extends Serializable, K extends Serializable, V extends Serializable> Map<K, List<V>> group(List<T> list, GetId<K, T> getKey, GetId<V, T> getValue){
    	Map<K, List<V>> groupMap = Maps.newLinkedHashMap();
    	if(notEmpty(list)){
    		for(T entity : list){
    			K key = getKey.getId(entity);
    			List<V> groupList = groupMap.get(key);
    			if(groupList == null){
    				groupList = Lists.newArrayList();
    				groupMap.put(key, groupList);
    			}
    			V value = getValue.getId(entity);
    			groupList.add(value);
    		}
    	}
    	return groupMap;
    }
    
    /**
     * 查看两个日期的天数差值
     * 日期不能为空
     * author lijuntao
     * date 2018年4月21日
     */
    public static int minusDays(Date date1, Date date2){
    	if(empty(date1) || empty(date2)){
    		throw new RuntimeException("执行两个日期的相差天数：日期不能为空");
    	}
    	//除去时分秒
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(date1);
    	c1.set(Calendar.HOUR_OF_DAY, 0);
    	c1.set(Calendar.MINUTE, 0);
    	c1.set(Calendar.SECOND, 0);
    	c1.set(Calendar.MILLISECOND, 0);
    	//除去时分秒
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(date2);
    	c2.set(Calendar.HOUR_OF_DAY, 0);
    	c2.set(Calendar.MINUTE, 0);
    	c2.set(Calendar.SECOND, 0);
    	c2.set(Calendar.MILLISECOND, 0);
    	long millis1 = c1.getTimeInMillis();
    	long millis2 = c2.getTimeInMillis();
    	long minus = millis1 - millis2;
    	long oneDayMillis = 24 * 60 * 60 * 1000;
    	int days = (int) (minus / oneDayMillis);
    	return days;
    }
    
    /**
     * 分割字符串， Ascii即英文算一个字符
     * @param : notAsciiWorthSize代表非英文算是多少个字符
     * author lijuntao
     * date 2018年4月27日
     */
    public static List<String> subString(String str, int notAsciiWorthSize, int length){
    	if(notAsciiWorthSize < 1)
    		throw new RuntimeException("notAsciiWorthSize 必须大于或者等于1");
    	if(length < 1)
    		throw new RuntimeException("length 必须大于或者等于1");
    	if(CommonUtils.empty(str))
    		return Lists.newArrayList();
    	
    	List<String> list = Lists.newArrayList();
    	char[] cs = str.toCharArray();
    	
    	//积累了多少长度
    	int accumulative = 0;
    	//上次截取完后的下标
    	int lastSubIndex = 0;
    	//每个字符长度的大小
    	int evenyCharWorthSize = 0;
    	
    	for(int i=0; i< cs.length; i++){
    		if(-128 <= cs[i] && cs[i] <= 127){
    			evenyCharWorthSize = 1;
    		}else{
    			evenyCharWorthSize = notAsciiWorthSize;
    		}
    		
    		accumulative += evenyCharWorthSize;
    		
    		if(accumulative == length){
    			//刚好匹配
    			char[] subCs = new char[i - lastSubIndex + 1];
    			System.arraycopy(cs, lastSubIndex, subCs, 0, i - lastSubIndex + 1);
    			list.add(new String(subCs));
    			accumulative = 0;
    			lastSubIndex = i + 1;
    		}else if(accumulative < length){
    			if(i == cs.length - 1){
    				//刚好最后
    				char[] subCs = new char[cs.length - lastSubIndex];
        			System.arraycopy(cs, lastSubIndex, subCs, 0, cs.length - lastSubIndex);
        			list.add(new String(subCs));
    			}
    		}else{
    			if(i > lastSubIndex){
	    			char[] subCs = new char[i - lastSubIndex];
	    			System.arraycopy(cs, lastSubIndex, subCs, 0, i - lastSubIndex);
	    			list.add(new String(subCs));
	    			lastSubIndex = i;
	    			accumulative = evenyCharWorthSize;
    			}
    			
    			if(i == cs.length - 1){
    				//刚好最后
    				list.add(new String(new char[]{cs[i]}));
    			}
    		}
    		
    	}
    	
    	return list;
    }

	public static <T extends Serializable> List<T> deepCloneList(List<T> list) {
		List<T> clones  = Lists.newArrayList();
		for(T t : nullToEmpty(list)){
			T clone = deepClone(t);
			clones.add(clone);
		}
		return clones;
	}

	public static <T> T getOne(List<T> list) {
		return notEmpty(list)? list.get(0): null;
	}
	
	public static <T> T getLast(List<T> list) {
		return notEmpty(list)? list.get(CommonUtils.size(list) - 1): null;
	}



	
	/**
	 * map转bean
	 */
//	public static <T, V> T mapToBean(Map<String, V> data, Class<T> clazz){
//		try {
//			T bean = clazz.newInstance();
//			BeanUtils.populate(bean, data);
//			return bean;
//		} catch (Exception e) {
//			Base.validate.error("实体转为map有问题", e);
//		}
//		return null;
//	}
	
	public static <T> int size(Collection<T> list){
		return list == null ? 0:list.size();
	}
	
	public static <K, V> int size(Map<K, V> map) {
		return map == null ? 0:map.size();
	}
	
	public static <T> int size(T[] arrs){
		return arrs == null ? 0:arrs.length;
	}
	
	/**
	 * 合并
	 * author lijuntao
	 * date 2018年11月30日
	 */
	public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> map2){
		Map<K, V> mergeMap = Maps.newHashMap();
		if(notEmpty(map1)){
			for(Entry<K, V> entry : map1.entrySet()){
				mergeMap.put(entry.getKey(), entry.getValue());
			}
		}
		if(notEmpty(map2)){
			for(Entry<K, V> entry : map2.entrySet()){
				mergeMap.put(entry.getKey(), entry.getValue());
			}
		}
		return mergeMap;
	}
	
	/**
	 * 值相加
	 * author lijuntao
	 * date 2018年11月30日
	 */
	public static Double sum(Map<String, Double> map){
		Double sum = null;
		if(notEmpty(map)){
			for(Entry<String, Double> entry : map.entrySet()){
				Double value = entry.getValue();
				if(value != null){
					sum = NumberUtils.primitive(sum) + value;
				}
			}
		}
		return sum;
	}
	
	public static <K, T> Map<K, Collection<T>> addTo(Map<K, Collection<T>> map, K key, T entity){
		if(map != null && key != null && entity != null){
			Collection<T> list = map.get(key);
			if(list == null){
				synchronized (map) {
					if(list == null){
						list = new ArrayList<T>();
					}
					map.put(key, list);
				}
			}
			list.add(entity);
		}
		return map;
	}

	public static <K, T> Map<K, List<T>> addToList(Map<K, List<T>> map, K key, T entity){
		if(map != null && key != null && entity != null){
			List<T> list = map.get(key);
			if(list == null){
				synchronized (map) {
					if(list == null){
						list = new ArrayList<T>();
					}
					map.put(key, list);
				}
			}
			list.add(entity);
		}
		return map;
	}




	
	/**
	 * 值相加
	 * author lijuntao
	 * date 2018年11月30日
	 */
	public static Map<String, Double> sumToMap(Map<String, Double> map, String key, Double value){
		if(map != null && value != null){
			Double sum = NumberUtils.add(map.get(key), value);
			map.put(key, sum);
		}
		return map;
	}
	
	/**
	 * 获取数字并加一，从1开始
	 */
	public static int getAndAddOne(Map<String, Integer> map, String key){
		Integer integer = map.get(key);
		integer = NumberUtils.primitive(integer) + 1;
		map.put(key, integer);
		return integer;
	}
	
	/*
	 * Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	 */
	public static <T> Map<String, Object> toMap(T obj) {
	    if(obj == null){
	        return Maps.newHashMap();
	    }        
	    Map<String, Object> map =Maps.newHashMap();
	    try {
	        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	        for (PropertyDescriptor property : propertyDescriptors) {
	            String key = property.getName();
	            // 过滤class属性
	            if (!key.equals("class")) {
	                // 得到property对应的getter方法
	                Method getter = property.getReadMethod();
	                Object value = getter.invoke(obj);
	                map.put(key, value);
	            }
	
	        }
	    } catch (Exception e) {
	        log.error("toMap Error ", e);
	    }
	    return map;
	}
	
	/**
	 * 
	 * @author lijuntao
	 * @date 2019年4月16日
	 */
	public static <T> Map<String, String> toStringMap(Map<String, Object> map) {
		Map<String, String> map2 =Maps.newHashMap();
		CommonUtils.nullToEmpty(map).forEach((k, v) ->{
			map2.put(k, StringUtils.valueOf(v));
		});
		return map2;
	}


	
	public static <T extends Comparable<T>> void sort(List<T> list){
		if(CommonUtils.empty(list)){
			return;
		}
		Collections.sort(list);
	}
	
	public static <T> void sort(List<T> list, Comparator<T> c){
		if(CommonUtils.empty(list) || CommonUtils.empty(c)){
			return;
		}
		Collections.sort(list, c);
	}



    /**
     * 功能描述:拼接图片
     * @auther: 豪
     * @date: 2019/8/12 16:24
     */
    public static String getUrlAsset(String url){
        if(CommonUtils.empty(url)) return null;
        int indexOf = url.indexOf("/asset");
        if(indexOf == -1) return url;
        url = url.substring(indexOf+6, url.length());
        return url;
    }


    public static String getUrlLast(String url){
        if(CommonUtils.empty(url)) return null;
        int indexOf = url.lastIndexOf("/");
        if(indexOf == -1) return url;
        url = url.substring(indexOf, url.length());
        return url;
    }


	
	/**
	 * 替换domin
	 * @author lijuntao
	 * @date 2019年7月13日
	 */
	public static String replaceDominForUrls(String urls, String domin){
		String[] urlList = StringUtils.split(urls, ",");
		List<String> result = Lists.newArrayList();
		for(String url : CommonUtils.nullToEmpty(urlList)){
			String replaceUrl = replaceDomin(url, domin);
			result.add(replaceUrl);
		}
		return StringUtils.concatIngoreEmpty(result, ",");
	}
	
	public static String replaceDomin(String url, String domain){
		if(StringUtils.isBlank(url) || StringUtils.isBlank(domain) ){
			return url; 
		}
		int indexOf = url.indexOf("/asset");
		if(indexOf == -1) return url;
		url = url.substring(indexOf, url.length());
		return domain + "/crNursing"+ url;
	}
	
	/**
	 * 获取附件的相对路径
	 * @author lijuntao
	 * @date 2019年11月7日
	 */
	public static String getRelativePathForAttach(String path){
		if(StringUtils.isBlank(path)){
			return path; 
		}
		String str = "/crNursing/asset/";
		int indexOf = path.indexOf(str);
		if(indexOf == -1) return path;
		path = path.substring(indexOf + str.length() - 1, path.length());
		return path;
	}
	


    
    public static <T> boolean contains(List<T> list, T record){
    	if(CommonUtils.empty(list) || record == null){
    		return false;
    	}
    	for(T t : list){
    		if(record.equals(t)){
    			return true;
    		}
    	}
    	return false;
    }
    


    public static <T> List<T> toList(T[] arrs){
    	List<T> list = Lists.newArrayList();
    	if(CommonUtils.notEmpty(arrs)){
    		for(T record : arrs){
    			list.add(record);
    		}
    	}
    	return list;
    }
    
    public static <T> Set<T> toSet(T[] arrs){
    	Set<T> set = Sets.newHashSet();
    	if(CommonUtils.notEmpty(arrs)){
    		for(T record : arrs){
    			set.add(record);
    		}
    	}
    	return set;
    }


    
    /**
     * 空赋予默认值
     * @author lijuntao
     * @date 2019年6月24日
     */
	public static <T> T defaultValue(T integer, T defaultValue){
		return integer != null? integer: defaultValue;
	}
	
	/**
	 * 截取
	 * @author lijuntao
	 * @date 2019年7月1日
	 */
	public static <T> List<T> subList(List<T> list, int beginIndex, int endIndex){
		
		int size = CommonUtils.size(list);
		if(beginIndex >= size){
			return Lists.newArrayList();
		}
		endIndex = size < endIndex? size: endIndex;
		return Lists.newArrayList(list.subList(beginIndex, endIndex));
	}
	
	/**
	 * 截取
	 * @author lijuntao
	 * @date 2019年7月1日
	 */
	public static <T> List<List<T>> splitList(List<T> list, int size){
		
		List<List<T>> splitList = Lists.newArrayList();
		int splitSize = CommonUtils.size(list) / size;
		if(CommonUtils.size(list) % size > 0){
			splitSize += 1;
		}
		for(int i=0; i< splitSize; i++){
			List<T> subList = CommonUtils.subList(list, i * size, (i + 1) * size);
			splitList.add(subList);
		}
		return splitList;
	}
	
	/**
	 * 获取
	 * @author lijuntao
	 * @date 2019年7月1日
	 */
	public static <T> T getOne(List<T> list, int index){
		if(CommonUtils.size(list) > index){
			return list.get(index);
		}
		return null;
	}
	





    /**
     * 对象转map
     * @author lijuntao
     * @date 2019年10月28日
     */
    public static Map<String, Object> objectToMap(Object obj){
        if(obj == null) {
            return Maps.newHashMap();      
        }
        Map<String, Object> map = new HashMap<String, Object>();   
        objectPutToMap(obj, map);  
        return map;  
    }
    
    /**
     * 对象转map
     * @author lijuntao
     * @date 2019年10月28日
     */
    public static void objectPutToMap(Object obj, Map<String, Object> map){
    	if(obj == null) {
    		return;
    	}
    	BeanInfo beanInfo;
    	try {
    		beanInfo = Introspector.getBeanInfo(obj.getClass());
    		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
    		for (PropertyDescriptor property : propertyDescriptors) {    
    			String key = property.getName();    
    			if (key.compareToIgnoreCase("class") == 0) {   
    				continue;  
    			}  
    			Method getter = property.getReadMethod();  
    			Object value = getter!=null ? getter.invoke(obj) : null;  
    			map.put(key, value);  
    		}    
    	} catch (Exception e) {
            System.out.println(e.getMessage());
    	}    
    }
    
    /**
     * 对象转map
     * @author lijuntao
     * @date 2019年10月28日
     */
    public static Map<String, String> objectToStringMap(Object obj) {
    	Map<String, Object> map = objectToMap(obj);
    	Map<String, String>  map2 = Maps.newHashMap();
    	map.forEach((key, value) -> {
    		map2.put(key, StringUtils.valueOf(value));
    	});
    	return map2;
    }
    
    /**
     * 将实体集合转为排序Map
     * author lijuntao
     * date 2017年11月23日
     */
    public static <K extends Serializable, T> Map<K, Integer> getOrderMap(Collection<T> entityList, GetId<K, T> getBaseId){
    	Map<K, Integer> idMap = Maps.newLinkedHashMap();
    	int i = 1;
    	if(notEmpty(entityList)){
    		for(T entity : entityList){
    			idMap.put(getBaseId.getId(entity), i++);
    		}
    	}
    	return idMap;
    }
    
    /**
     * 计算BMI
     * @author lijuntao
     * @date 2019年12月4日
     */
    public static Double calcBmi(Double heightCm, Double weightKg){
    	if(heightCm == null || weightKg == null){
    		return null;
    	}
    	double bmi = weightKg/(double)(heightCm*heightCm/10000);
    	bmi = NumberUtils.formatAndParse(bmi, NumberUtils.FORMAT_00);
    	return bmi;
    }
}
