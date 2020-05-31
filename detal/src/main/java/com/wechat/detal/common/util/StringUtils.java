package com.wechat.detal.common.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String trim(String str) {
        return str == null ? str : str.trim();
    }

    public static String valueOf(Object str) {
        return str == null ? "" : String.valueOf(str);
    }

    public static String valueOfAndTrim(Object str) {
        return str == null ? "" : String.valueOf(str).trim();
    }

    public static <T> String concat(Collection<String> co, String str) {
        if (CommonUtils.empty(co)) {
            return "";
        }
        str = CommonUtils.nullToEmpty(str);
        StringBuffer buffer = new StringBuffer();
        boolean hasSetFirst = false;
        for (String string : co) {
            if (hasSetFirst) {
                buffer.append(str);
            } else {
                hasSetFirst = true;
            }
            buffer.append(CommonUtils.nullToEmpty(string));
        }
        return buffer.toString();
    }

    public static <T> String concat(String[] arrs, String str) {
        if (CommonUtils.empty(arrs)) {
            return "";
        }
        str = CommonUtils.nullToEmpty(str);
        StringBuffer buffer = new StringBuffer();
        boolean hasSetFirst = false;
        for (String string : arrs) {
            if (hasSetFirst) {
                buffer.append(str);
            } else {
                hasSetFirst = true;
            }
            buffer.append(CommonUtils.nullToEmpty(string));
        }
        return buffer.toString();
    }

    public static <T> String concatObject(Collection<T> co, String str) {
        List<String> list = Lists.newArrayList();
        for (T t : CommonUtils.nullToEmptyForEach(co)) {
            list.add(StringUtils.valueOf(t));
        }
        return concat(list, str);
    }

    /**
     * 拼接字符串， 会忽视其中为空的字符串
     * author lijuntao
     * date 2018年5月17日
     */
    public static String concatIngoreEmpty(String[] strs, String str) {
        if (CommonUtils.empty(strs)) {
            return "";
        }
        str = CommonUtils.nullToEmpty(str);
        StringBuffer buffer = new StringBuffer();
        boolean hasSetFirst = false;
        for (String string : strs) {
            if (CommonUtils.notEmpty(string)) {
                if (hasSetFirst) {
                    buffer.append(str);
                } else {
                    hasSetFirst = true;
                }
                buffer.append(string);
            }
        }
        return buffer.toString();
    }


    /**
     * 拼接字符串
     * 全为空， 返回空字符串
     * 否则 返回正常拼接字符串
     * author lijuntao
     * date 2018年5月17日
     */
    public static String concatIngoreAllEmpty(List<String> arrs, String str) {
        if (CommonUtils.empty(arrs)) {
            return "";
        }
        str = CommonUtils.nullToEmpty(str);
        StringBuffer buffer = new StringBuffer();
        boolean hasSetFirst = false;
        boolean allEmpty = true;
        for (String string : arrs) {
            if (hasSetFirst) {
                buffer.append(str);
            } else {
                hasSetFirst = true;
            }
            buffer.append(CommonUtils.nullToEmpty(string));
            if (CommonUtils.notEmpty(string)) {
                allEmpty = false;
            }
        }
        return allEmpty ? "" : buffer.toString();
    }

    /**
     * 拼接字符串， 全为空， 返回空字符串
     * author lijuntao
     * date 2018年5月17日
     */
    public static String concatIngoreAllEmpty(String[] arrs, String str) {
        if (CommonUtils.empty(arrs)) {
            return "";
        }
        str = CommonUtils.nullToEmpty(str);
        StringBuffer buffer = new StringBuffer();
        boolean hasSetFirst = false;
        boolean allEmpty = true;
        for (String string : arrs) {
            if (hasSetFirst) {
                buffer.append(str);
            } else {
                hasSetFirst = true;
            }
            buffer.append(CommonUtils.nullToEmpty(string));
            if (CommonUtils.notEmpty(string)) {
                allEmpty = false;
            }
        }
        return allEmpty ? "" : buffer.toString();
    }

    /**
     * 拼装字符串
     */
    public static String concatUnique(String... strs) {
        if (CommonUtils.empty(strs)) {
            return "";
        }
        StringBuffer buf = new StringBuffer();
        for (String str : strs) {
            if (str != null) {
                str = str.replaceAll("_", "._");
                buf.append("_");
                buf.append(str);
            } else {
                buf.append("_");
            }
        }
        return buf.toString();
    }

//	/**
//	 * 拼装字符串
//	 */
//	public static String concatUnique(String[] strs){
//		if(CommonUtils.empty(strs)){
//			return "";
//		}
//		StringBuffer buf = new StringBuffer();
//		for(String str : strs){
//			if(str != null){
//				str = str.replaceAll("_", "._");
//				buf.append("_");
//				buf.append(str);
//			}else{
//				buf.append("_");
//			}
//		}
//		return buf.toString();
//	}


    /**
     * 拼接字符串， 会忽视其中为空的字符串
     * author lijuntao
     * date 2018年5月17日
     */
    public static String concatIngoreEmpty(Collection<String> list, String str) {
        if (CommonUtils.empty(list)) {
            return "";
        }
        str = CommonUtils.nullToEmpty(str);
        StringBuffer buffer = new StringBuffer();
        boolean hasSetFirst = false;
        for (String string : list) {
            if (CommonUtils.notEmpty(string)) {
                if (hasSetFirst) {
                    buffer.append(str);
                } else {
                    hasSetFirst = true;
                }
                buffer.append(string);
            }
        }
        return buffer.toString();
    }

    /**
     * 分割字符串， Ascii即英文算一个字符
     *
     * @param : notAsciiWorthSize代表非英文算是多少个字符
     *          author lijuntao
     *          date 2018年4月27日
     */
    public static List<String> subString(String str, int notAsciiWorthSize, int length) {
        if (notAsciiWorthSize < 1)
            throw new RuntimeException("notAsciiWorthSize 必须大于或者等于1");
        if (length < 1)
            throw new RuntimeException("length 必须大于或者等于1");
        if (CommonUtils.empty(str))
            return Lists.newArrayList();

        List<String> list = Lists.newArrayList();
        char[] cs = str.toCharArray();

        //积累了多少长度
        int accumulative = 0;
        //上次截取完后的下标
        int lastSubIndex = 0;
        //每个字符长度的大小
        int evenyCharWorthSize = 0;

        for (int i = 0; i < cs.length; i++) {
            if (-128 <= cs[i] && cs[i] <= 127) {
                evenyCharWorthSize = 1;
            } else {
                evenyCharWorthSize = notAsciiWorthSize;
            }

            accumulative += evenyCharWorthSize;

            if (accumulative == length) {
                //刚好匹配
                char[] subCs = new char[i - lastSubIndex + 1];
                System.arraycopy(cs, lastSubIndex, subCs, 0, i - lastSubIndex + 1);
                list.add(new String(subCs));
                accumulative = 0;
                lastSubIndex = i + 1;
            } else if (accumulative < length) {
                if (i == cs.length - 1) {
                    //刚好最后
                    char[] subCs = new char[cs.length - lastSubIndex];
                    System.arraycopy(cs, lastSubIndex, subCs, 0, cs.length - lastSubIndex);
                    list.add(new String(subCs));
                }
            } else {
                if (i > lastSubIndex) {
                    char[] subCs = new char[i - lastSubIndex];
                    System.arraycopy(cs, lastSubIndex, subCs, 0, i - lastSubIndex);
                    list.add(new String(subCs));
                    lastSubIndex = i;
                    accumulative = evenyCharWorthSize;
                }

                if (i == cs.length - 1) {
                    //刚好最后
                    list.add(new String(new char[]{cs[i]}));
                }
            }

        }

        return list;
    }

    /**
     * lastIgnores中的字符如果出现在结尾， 忽略一次
     * 分割字符串， Ascii即英文算一个字符
     *
     * @param : notAsciiWorthSize代表非英文算是多少个字符
     *          author lijuntao
     *          date 2018年4月27日
     */
    public static List<String> subString(String str, int notAsciiWorthSize, int length, String lastIgnores) {
        if (notAsciiWorthSize < 1)
            throw new RuntimeException("notAsciiWorthSize 必须大于或者等于1");
        if (length < 1)
            throw new RuntimeException("length 必须大于或者等于1");
        if (CommonUtils.empty(str))
            return Lists.newArrayList();

        List<String> list = Lists.newArrayList();
        char[] cs = str.toCharArray();

        //积累了多少长度
        int accumulative = 0;
        //上次截取完后的下标
        int lastSubIndex = 0;
        //每个字符长度的大小
        int evenyCharWorthSize = 0;

        Set<Character> lastIgnoreSet = Sets.newHashSet();
        for (char c : CommonUtils.nullToEmpty(lastIgnores).toCharArray()) {
            lastIgnoreSet.add(c);
        }
        ;

        for (int i = 0; i < cs.length; i++) {
            if (-128 <= cs[i] && cs[i] <= 127) {
                evenyCharWorthSize = 1;
            } else {
                evenyCharWorthSize = notAsciiWorthSize;
            }

            accumulative += evenyCharWorthSize;

            if (accumulative == length) {
                if (i + 1 < cs.length && lastIgnoreSet.contains(cs[i + 1])) {
                    i++;
                }
                //刚好匹配
                char[] subCs = new char[i - lastSubIndex + 1];
                System.arraycopy(cs, lastSubIndex, subCs, 0, i - lastSubIndex + 1);
                list.add(new String(subCs));
                accumulative = 0;
                lastSubIndex = i + 1;
            } else if (accumulative < length) {
                if (i == cs.length - 1) {
                    //刚好最后
                    char[] subCs = new char[cs.length - lastSubIndex];
                    System.arraycopy(cs, lastSubIndex, subCs, 0, cs.length - lastSubIndex);
                    list.add(new String(subCs));
                }
            } else {
                if (i > lastSubIndex) {
                    accumulative = evenyCharWorthSize;
                    if (lastIgnoreSet.contains(cs[i])) {
                        accumulative = 0;
                        i++;
                    }
                    char[] subCs = new char[i - lastSubIndex];
                    System.arraycopy(cs, lastSubIndex, subCs, 0, i - lastSubIndex);
                    list.add(new String(subCs));
                    lastSubIndex = i;
                }

                if (i == cs.length - 1) {
                    //刚好最后
                    list.add(new String(new char[]{cs[i]}));
                }
            }

        }

        return list;
    }

    /**
     * 英文字母转为大写的
     * author lijuntao
     * date 2018年9月20日
     */
    public static char toUpperCase(char c) {
        if (c >= 'a' && c <= 'z') {
            c = (char) (c - 'a' + 'A');
        }
        return c;
    }


    public static String uuid() {

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String uuid16() {

        return UUID.randomUUID().toString().replaceAll("-", "").substring(8, 24);
    }

    /**
     * 获取字符串最大长度
     * author lijuntao
     * date 2017年12月7日
     */
    public static String subMaxString(String str, int maxLength) {
        return (str == null || str.length() <= maxLength) ? str : str.substring(0, maxLength);
    }

    /**
     * 分割，返回一个List
     * 默认移除空字符创建
     * 默认去两端空格
     * author lijuntao
     * date 2018年12月25日
     */
    public static List<String> splitToList(String str, String separatorChar) {
        return splitToList(str, separatorChar, true);
    }

    /**
     * 分割，返回一个List
     * 默认移除空字符创建
     * 默认去两端空格
     * author lijuntao
     * date 2018年12月25日
     */
    public static List<String> splitToList(String str, String separatorChar, boolean trim) {

        String[] strings = split(str, separatorChar);

        return toListRemoveBlank(strings, trim);
    }

    /**
     * 分割，返回一个Long类型的List
     * 默认移除空字符创建
     * 默认去两端空格
     * author lijuntao
     * date 2018年12月25日
     */
    public static List<Long> splitToLongList(String str, String separatorChar) {
        return splitToLongList(str, separatorChar, true);
    }

    /**
     * 分割，返回一个Long类型的List
     * author lijuntao
     * date 2018年12月25日
     */
    public static List<Long> splitToLongList(String str, String separatorChar, boolean trim) {

        String[] strings = split(str, separatorChar);

        List<String> list = toListRemoveBlank(strings, trim);

        return toLong(list);
    }

    /**
     * 分割，返回一个set
     * author lijuntao
     * date 2018年12月25日
     */
    public static Set<String> splitToSet(String str, String separatorChar) {
        return splitToSet(str, separatorChar, true);
    }

    /**
     * 分割，返回一个set
     * author lijuntao
     * date 2018年12月25日
     */
    public static Set<String> splitToSet(String str, String separatorChar, boolean trim) {

        String[] strings = split(str, separatorChar);

        return toSetRemoveBlank(strings, trim);
    }

    public static Set<String> toSetRemoveBlank(String[] strs, boolean trim) {
        Set<String> set = Sets.newHashSet();
        if (CommonUtils.empty(strs)) {
            return set;
        }
        for (String str : strs) {
            if (StringUtils.isNotBlank(str)) {
                set.add(trim ? str.trim() : str);
            }
        }
        return set;
    }

    /**
     * @Description:
     * @Author: lijuntao
     * @Date: 2020/1/8
     */
    public static List<String> toListRemoveBlank(String[] strs, boolean trim) {
        List<String> list = Lists.newArrayList();
        if (CommonUtils.empty(strs)) {
            return list;
        }
        for (String str : strs) {
            if (StringUtils.isNotBlank(str)) {
                list.add(trim ? str.trim() : str);
            }
        }
        return list;
    }


    public static List<Long> toLong(List<String> strList) {
        List<Long> list = Lists.newArrayList();
        if (CommonUtils.empty(strList)) {
            return list;
        }
        for (String str : strList) {
            Long lValue = Long.parseLong(str);
            list.add(lValue);
        }

        return list;
    }

    /**
     * 首字母大写
     * author lijuntao
     * date 2019年1月28日
     */
    public static String firstUpper(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        return str;
    }

    /**
     * 获取数组的某个值，不返回异常
     *
     * @author lijuntao
     * @date 2019年5月14日
     */
    public static String get(String[] arrs, int index) {
        if (CommonUtils.size(arrs) > index) {
            return arrs[index];
        }
        return null;
    }

    /**
     * 将首尾同步字符创简缩一起
     * "左上肢肌力（级）", "右上肢肌力（级）", "左下肢肌力（级）", "右下肢肌力（级）"  --> 左上/右上/左下/右下肢肌力（级）
     *
     * @author lijuntao
     * @date 2019年6月13日
     */
    public static String combineAbbreviated(List<String> list, String concatStr) {
        if (CommonUtils.empty(list)) {
            return null;
        } else if (CommonUtils.size(list) == 1) {
            return list.get(0);
        } else {
            int i = getSimpleLength(list);
            int j = getSimpleLenthFormLast(list);
            String prefix = list.get(0).substring(0, i);
            String string = list.get(list.size() - 1);
            String suffix = string.substring(string.length() - j, string.length());
            List<String> splitList = Lists.newArrayList();
            for (String str : list) {
                if (i + j >= str.length()) {
                    splitList.add("");
                } else {
                    splitList.add(str.substring(i, str.length() - j));
                }
            }
            return prefix + StringUtils.concat(splitList, concatStr) + suffix;
        }
    }

    /**
     * 获取相同子串的长度，从头开始
     *
     * @author lijuntao
     * @date 2019年6月13日
     */
    public static int getSimpleLength(List<String> list) {
        if (CommonUtils.empty(list)) {
            return 0;
        }
        String firstStr = list.get(0);
        int maxLength = firstStr.length();
        for (int i = 1; i < list.size(); i++) {
            String str = list.get(i);
            for (int j = 0; j < Math.min(maxLength, str.length()); j++) {
                if (firstStr.charAt(j) != str.charAt(j)) {
                    maxLength = j;
                    break;
                }
            }
        }
        return maxLength;
    }

    /**
     * 获取相同子串的长度，从尾开始
     *
     * @author lijuntao
     * @date 2019年6月13日
     */
    public static int getSimpleLenthFormLast(List<String> list) {
        if (CommonUtils.empty(list)) {
            return 0;
        }
        String firstStr = list.get(0);
        int firstStrLength = firstStr.length();
        int maxLength = firstStrLength;
        for (int i = 1; i < list.size(); i++) {
            String str = list.get(i);
            int strLength = str.length();
            for (int j = 0; j < Math.min(maxLength, strLength); j++) {
                if (firstStr.charAt(firstStrLength - j - 1) != str.charAt(strLength - j - 1)) {
                    maxLength = j;
                    break;
                }
            }
        }
        return maxLength;
    }

    public static String getContainsOne(String title, Set<String> set) {
        for (String str : set) {
            if (title.contains(str)) {
                return str;
            }
        }
        return null;
    }

    public static Set<String> getContainsAll(String title, Set<String> set) {
        Set<String> set2 = Sets.newHashSet();
        for (String str : set) {
            if (title.contains(str)) {
                set2.add(str);
            }
        }
        return set2;
    }

    public static String getOne(Set<String> all, Collection<String> partOf) {
        if (CommonUtils.empty(all) || CommonUtils.empty(partOf)) {
            return null;
        }
        for (String part : partOf) {
            if (all.contains(part)) {
                return part;
            }
        }
        return null;
    }

    public static boolean containsAll(Set<String> all, Set<String> partOf) {
        if (CommonUtils.empty(all) || CommonUtils.empty(partOf)) {
            return false;
        }
        for (String part : partOf) {
            if (!all.contains(part)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsAll(String str, Set<String> partOf) {
        if (CommonUtils.blank(str) || CommonUtils.empty(partOf)) {
            return false;
        }
        for (String part : partOf) {
            if (!str.contains(part)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsOne(Set<String> all, Set<String> partOf) {
        if (CommonUtils.empty(all) || CommonUtils.empty(partOf)) {
            return false;
        }
        for (String part : partOf) {
            if (all.contains(part)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsOne(String all, Set<String> partOf) {
        if (CommonUtils.empty(all) || CommonUtils.empty(partOf)) {
            return false;
        }
        for (String part : partOf) {
            if (all.contains(part)) {
                return true;
            }
        }
        return false;
    }

    public static String getContainsOne(Set<String> all, Collection<String> partOf) {
        if (CommonUtils.empty(all) || CommonUtils.empty(partOf)) {
            return null;
        }
        for (String str : partOf) {
            if (all.contains(str)) {
                return str;
            }
        }
        return null;
    }

    /**
     * 获取文件后缀名
     *
     * @author lijuntao
     * @date 2019年7月2日
     */
    public static String getSuffix(String fileName) {
        return substringAfterLast(fileName, ".");
    }

    /**
     * @author lijuntao
     * @date 2019年7月3日
     */
    public static String toOracleFormat(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String regex = "([A-Z]+)";
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.find();
        if (result) {
            do {
                String content = matcher.group(1);
                String value = content;
                if (matcher.start(1) != 0) {
                    value = "_" + content;
                }
                matcher.appendReplacement(sb, value);
                result = matcher.find();
            } while (result);
            matcher.appendTail(sb);
            str = sb.toString().toLowerCase();
        }
        return str;
    }

    /**
     * 取交集
     *
     * @author lijuntao
     * @date 2019年8月13日
     */
    public static <T> Set<T> intersect(Set<T> oneSet, Set<T> theOtherSet) {
        if (CommonUtils.empty(oneSet) || CommonUtils.empty(theOtherSet)) {
            return Sets.newHashSet();
        }
        Set<T> intersections = Sets.newHashSet();
        for (T record : oneSet) {
            if (theOtherSet.contains(record)) {
                intersections.add(record);
            }
        }
        return intersections;
    }

    /**
     * 去掉指定字符串的开头的指定字符
     *
     * @param stream 原始字符串
     * @param trim   要删除的字符串
     * @return
     */
    public static String trimStart(String stream, String trim) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trim == null || trim.length() == 0) {
            return stream;
        }
        // 要删除的字符串结束位置
        int end;
        // 正规表达式
        String regPattern = "[" + trim + "]*+";
        Pattern pattern = Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
        // 去掉原始字符串开头位置的指定字符
        Matcher matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            end = matcher.end();
            stream = stream.substring(end);
        }
        // 返回处理后的字符串
        return stream;
    }

    public static void main(String[] args) {
        System.out.println(trimStart("fg3.是的3.返回是", "\\d\\."));
    }
}
