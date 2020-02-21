package com.wechat.detal.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	public static int[] splitTwoInt(String str, int firstMax, int secondMax){
		if(StringUtils.isBlank(str)){
			return null;
		}
		str = StringUtils.trim(str);
		String regex = "(\\d+)[_:\\-：]+(\\d+)";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			String first = matcher.group(1);
			String second = matcher.group(2);
			int firstNum = NumberUtils.parseInteger(first);
			int secondNum = NumberUtils.parseInteger(second);
			if(firstNum <= firstMax && secondNum <= secondMax){
				return new int[]{firstNum, secondNum};
			}
		}
		return null;
	}
	
	public static String[] getFinds(String str, String regex){
		
		if(CommonUtils.empty(str) || CommonUtils.empty(regex)){
			return new String[0];
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()){
			String[] finds = new String[matcher.groupCount()];
			for(int i=1; i<=matcher.groupCount(); i++ ){
				finds[i-1] = matcher.group(i);
			}
			return finds;
		}
		return new String[0];
	}
	
	public static List<List<String>> findAll(String str, String regex){
		List<List<String>> allList = Lists.newArrayList();
		if(CommonUtils.empty(str) || CommonUtils.empty(regex)){
			return Lists.newArrayList();
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			List<String> matchList = Lists.newArrayList();
			for(int i=1; i<=matcher.groupCount(); i++ ){
				matchList.add(matcher.group(i));
			}
			allList.add(matchList);
		}
		return allList;
	}
	
	public static String replaceAll(String title, String regex, Map<String, String> dataMap){
		if(StringUtils.isBlank(title) || CommonUtils.empty(dataMap)){
			return title;
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(title);
		boolean result = matcher.find();
		if (result) {
            do {
            	String content = matcher.group(1);
            	String value = dataMap.get(content);
            	if(StringUtils.isNotBlank(value)){
            		matcher.appendReplacement(sb, value);
            	}else{
            		matcher.appendReplacement(sb, content);
            	}
                result = matcher.find();
            } while (result);
            matcher.appendTail(sb);
            title = sb.toString();
        }
		return title;
	}
	
	public static void main(String[] args) {
		String title = "hhj$1ts12y346si1ud";
		Map<String, String> map = Maps.newHashMap();
		String regex = "(\\$1)";
		map.put("$1", "1111111");
		map.put("346", "2222222");
		String string = replaceAll(title, regex, map);
		System.out.println(string);
	}
	
}
