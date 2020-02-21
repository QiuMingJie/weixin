package com.wechat.detal.common.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;
import com.wechat.detal.annotation.ColCopy;
import com.wechat.detal.annotation.DateTrueOrFalse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

public class CopyUtils {

    private static Log logger = LogFactory.getLog(CopyUtils.class);

    public final static Validate validate = new Validate();

    /**
     * 两种不同类之间的赋值转换,按照注解来转换,有注解的类为源类
     *
     * @param source
     * @param target
     * @throws Exception
     * @author 豪
     * @date 2019年3月2日
     */
    public static void copyProperties(Object source, Object target) throws Exception {
        // 1:获取源实体类的所有属性，返回Field数组
        List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), ColCopy.class, true);
        // 2:获取目标实体类的所有属性，返回Field数组
        Class<? extends Object> targetObj = target.getClass();
        // 3:循环遍历源实体类得属性，获取属性名和类型和值
        for (Field fieldSource : listsFields) {
            if (fieldSource.isAnnotationPresent(ColCopy.class)) {
                ColCopy colCopy = fieldSource.getAnnotation(ColCopy.class);
                String nameSource = colCopy.name();
                // 5:获取属性类型
                String typeSource = fieldSource.getType().toString();
                // 关键。。。可访问私有变量（才可以得到值）
                fieldSource.setAccessible(true);
                // 6:获取源实体类的值
                String valueSource = null;
                if (fieldSource.get(source) != null) {
                    valueSource = fieldSource.get(source).toString();
                }
                // 7:根据源实体类注解里的名字去目标实体类中查找对应的属性信息
                Field field = ClassUtils.getField(target.getClass(), nameSource);
                if (CommonUtils.empty(field)) continue;
                ClassUtils.setValue(target, field.getName(), valueSource);
//
//                if (CommonUtils.empty(field)) continue;
//                //8：获取目标类的属性类型
//                String typeTarget = field.getType().toString();
//                field.setAccessible(true);
//                if ("class java.lang.String".equals(typeTarget) && valueSource != null) {
//                    field.set(target, String.valueOf(valueSource));
//                }
//                if ("class java.lang.Integer".equals(typeTarget) && CommonUtils.notBlank(valueSource)) {
//                    field.set(target, Integer.valueOf(valueSource));
//                } else if ("class java.lang.Long".equals(typeTarget) && valueSource != null) {
//                    field.set(target, Long.valueOf(valueSource));
//                } else if ("class java.lang.Double".equals(typeTarget) && valueSource != null) {
//                    field.set(target, Double.valueOf(valueSource));
//                } else if ("class java.math.BigDecimal".equals(typeTarget) && valueSource != null) {
//                    field.set(target, new BigDecimal(valueSource));
//                } else if ("class java.util.Date".equals(typeTarget) && valueSource != null) {
//                    Date date = null;
//                    if (!valueSource.contains(":")) {
//                        date = new SimpleDateFormat("yyyy-MM-dd").parse(valueSource);
//                        field.set(target, date);
//                    } else if (valueSource.contains(":") && valueSource.length() < 18) {
//                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(valueSource);
//                        field.set(target, date);
//                    } else if (valueSource.contains(":") && valueSource.length() > 18) {
//                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(valueSource);
//                        field.set(target, date);
//                    } else {
//                        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(valueSource);
//                        field.set(target, date);
//                    }
//                }
            }
        }
    }

    /**
     * 获取类中对应属性的值
     *
     * @param source
     * @throws Exception
     * @author 豪
     * @date 2019年3月2日
     */
    public static List<Object> getValues(Object source) throws Exception {
        List<Object> params = new ArrayList<>();
        // 1:获取源实体类的所有属性，返回Field数组
        List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
        for (Field field : listsFields) {
            // 关键。。。可访问私有变量（才可以得到值）
            field.setAccessible(true);
            // 6:获取源实体类的值
            String valueSource = null;
            if (field.get(source) != null) {
                if (field.isAnnotationPresent(JsonFormat.class)) {
                    Date str = (Date) field.get(source);
                    JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
                    String date = jsonFormat.pattern();
                    valueSource = CalendarUtil.DateTransString(str, date);
                } else if (boolean.class.equals(field.getType()) || Boolean.class.equals(field.getType())) {
                    Object object = field.get(source);
                    if (object != null) {
                        valueSource = ((boolean) object) ? "1" : "0";
                    }
                } else {
                    valueSource = field.get(source).toString();
                }
            }
            params.add(valueSource);
        }
        return params;
    }

    /**
     * 获取类中对应ID属性的值
     *
     * @param source
     * @param
     * @throws Exception
     * @author 豪
     * @date 2019年3月2日
     */
    public static List<Object> getValuesId(Object source) throws Exception {
        List<Object> params = new ArrayList<>();
        // 1:获取源实体类的所有属性，返回Field数组
        List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
        // 3:循环遍历源实体类得属性，获取属性名和类型和值
        for (Field field : listsFields) {
            if (field.isAnnotationPresent(Id.class)) {
                // 关键。。。可访问私有变量（才可以得到值）
                field.setAccessible(true);
                // 6:获取源实体类的值
                String valueSource = null;
                if (field.get(source) != null) {
                    if (field.isAnnotationPresent(JsonFormat.class)) {
                        Date str = (Date) field.get(source);
                        JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
                        String date = jsonFormat.pattern();
                        valueSource = CalendarUtil.DateTransString(str, date);
                    } else {
                        valueSource = field.get(source).toString();
                    }
                }
                params.add(valueSource);
            }
        }
        return params;
    }

    /**
     * 获取类中对应的数据库字段，返回字段
     *
     * @param source
     * @param
     * @throws Exception
     * @author 豪
     * @date 2019年3月2日
     */
    public static Set<String> findClassAnnotation(Object source) throws Exception {
        Set<String> params = new LinkedHashSet<>();
        // 1:获取源实体类的所有属性，返回Field数组
        List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
        // 3:循环遍历源实体类得属性，获取属性名和类型和值
        for (Field field : listsFields) {
            Column column = field.getAnnotation(Column.class);
            String nameSource = column.name();
            params.add(nameSource);
        }
        return params;
    }

    /**
     * 获取类中ID对应的数据库字段
     *
     * @param source
     * @param
     * @throws Exception
     * @author 豪
     * @date 2019年3月2日
     */
    public static Set<String> findClassAnnotationId(Object source) throws Exception {
        Set<String> params = new LinkedHashSet<>();
        // 1:获取源实体类的所有属性，返回Field数组
        List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
        // 3:循环遍历源实体类得属性，获取属性名和类型和值
        for (Field field : listsFields) {
            if (field.isAnnotationPresent(Id.class)) {
                Column column = field.getAnnotation(Column.class);
                String nameSource = column.name();
                params.add(nameSource);
            }
        }
        return params;
    }


    public static <T, E> E copyPropertiesOnly(T sourceObject, Class<E> targetClass) throws Exception {
        E targetEntity = targetClass.newInstance();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!declaredField.getName().equals("serialVersionUID"))
                ClassUtils.setValue(targetEntity, declaredField.getName(), ClassUtils.getValue(sourceObject, declaredField.getName()), declaredField.getType());
        }
        return targetEntity;
    }


    /**
     * 复制单个对象的属性
     *
     * @author sugx
     * @date 2019年12月3日
     */
    public static <T, E> E copy(T sourceObject, Class<E> targetClass) throws Exception {
        E targetEntity = targetClass.newInstance();
        CopyUtils.copyProperties(sourceObject, targetEntity);
        return targetEntity;
    }


    /**
     * 复制单个对象的属性
     *
     * @author sugx
     * @date 2019年12月19日
     */
    public static <T, E> E copy(T sourceObject, E targetObject) throws Exception {
        CopyUtils.copyProperties(sourceObject, targetObject);
        return targetObject;
    }


    /**
     * 批量赋值
     *
     * @author 豪
     * @date 2019年4月12日
     */
    public static <T, E> List<E> copy(List<T> sourcelList, Class<E> targetClass) throws Exception {
        List<E> list = new ArrayList<>();
        E targetEntity = null;
        for (T t : sourcelList) {
            targetEntity = targetClass.newInstance();
            CopyUtils.copyProperties(t, targetEntity);
            list.add(targetEntity);
        }
        return list;
    }

    /**
     * @author 豪
     * @date 2019年4月15日
     */
    public static String convertToInsertMark(Set<String> fields, Object source) {
        Iterator<String> marks = Iterators.transform(fields.iterator(), new Function<String, String>() {
            @Override
            public String apply(String input) {
                String valueSource = null;
                // 1:获取源实体类的所有属性，返回Field数组
                List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
                // 3:循环遍历源实体类得属性，获取属性名和类型和值
                try {
                    valueSource = resultValues(listsFields, input, source, "insert");
                } catch (Exception e) {
                    validate.error(e.getMessage());
                }
                return valueSource;
            }
        });
        return Joiner.on(",").join(marks);
    }

    /**
     * @author 豪
     * @date 2019年4月15日
     */
    public static String convertToUpdateMark(Set<String> fields, Object source) throws Exception {
        StringBuilder buf = new StringBuilder();
        for (String item : fields) {
            // 1:获取源实体类的所有属性，返回Field数组
            List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
            // 3:循环遍历源实体类得属性，获取属性名和类型和值
            String result = resultValues(listsFields, item, source, "update");
            buf.append(result);
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * @author 豪
     * @date 2019年4月15日
     */
    public static String convertToSelect(Set<String> fields, Object source) throws Exception {
        StringBuilder buf = new StringBuilder();
        for (String item : fields) {
            // 1:获取源实体类的所有属性，返回Field数组
            List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
            // 3:循环遍历源实体类得属性，获取属性名和类型和值
            String result = resultValues(listsFields, item, source, "select");
            buf.append(result);
        }
        if (buf.length() > 0) {
            buf.delete(buf.length() - 5, buf.length());
        }
        return buf.toString();
    }

    /**
     * 对字段进行不同的修饰，返回对应的修饰内容
     *
     * @author 豪
     * @date 2019年4月15日
     */
    @SuppressWarnings("unused")
    private static String resultValues(List<Field> fieldSource, String item, Object source, String values)
            throws Exception {
        String result = null;
        // 3:循环遍历源实体类得属性，获取属性名和类型和值
        for (Field field : fieldSource) {
            // 关键。。。可访问私有变量（才可以得到值）
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column colCopy = field.getAnnotation(Column.class);
                String nameSource = colCopy.name();
                if (nameSource.equals(item)) {
                    // 6:获取源实体类的值
                    Column column = field.getAnnotation(Column.class);
                    String str = column.columnDefinition();
                    if ("date".equals(str) && field.isAnnotationPresent(JsonFormat.class)) {
                        JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
                        Date strDate = (Date) field.get(source);
                        String date = jsonFormat.pattern();
                        if (!date.contains(":")) {
                            date = "yyyy-MM-dd";
                        } else {
                            date = "yyyy-MM-dd HH24:MI:SS";
                        }
                        if ("select".equals(values))
                            result = nameSource + "= to_date(?,'" + date + "') and ";
                        else if ("update".equals(values))
                            result = nameSource + "= to_date(?,'" + date + "'),";
                        else
                            result = "to_date(?,'" + date + "')";
                    } else {
                        if ("select".equals(values))
                            result = nameSource + "=? and ";
                        else if ("update".equals(values))
                            result = nameSource + "=?,";
                        else
                            result = "?";
                    }
                }
            }
        }
        return result;
    }

    /**
     * 对字段进行不同的修饰，返回对应的修饰内容(用于mergeinto) 把传进来的参数拼接成T1.patient_id=T2.patient_id
     * AND T1.visit_id=T2.visit_id这种形式 把传进来的参数拼接T1.patient_id, T1.visit_id,
     * T1.order_no, T1.order_sub_no
     *
     * @author 豪
     * @date 2019年4月15日
     */
    @SuppressWarnings("unused")
    private static String mergeIntoResult(List<Field> fieldSource, String item, Object source, String values)
            throws Exception {
        String result = null;
        // 3:循环遍历源实体类得属性，获取属性名和类型和值
        for (Field field : fieldSource) {
            // 关键。。。可访问私有变量（才可以得到值）
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column colCopy = field.getAnnotation(Column.class);
                String nameSource = colCopy.name();
                if (nameSource.equals(item)) {
                    // 6:获取源实体类的值
                    Column column = field.getAnnotation(Column.class);
                    String str = column.columnDefinition();
                    if ("date".equals(str) && field.isAnnotationPresent(JsonFormat.class)) {
                        JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
                        Date strDate = (Date) field.get(source);
                        String date = jsonFormat.pattern();
                        if (!date.contains(":")) {
                            date = "yyyy-MM-dd";
                        } else {
                            date = "yyyy-MM-dd HH24:MI:SS";
                        }
                        if ("select".equals(values))
                            result = "T1." + nameSource + "= to_date(T2." + nameSource + " ,'" + date + "') and ";
                        else if ("update".equals(values))
                            result = "T1." + nameSource + "= to_date(T2." + nameSource + " ,'" + date + "'),";
                        else
                            result = "to_date(T2." + nameSource + ",'" + date + "'),";
                    } else {
                        if ("select".equals(values)) {
                            result = "T1." + nameSource + "=T2." + nameSource + " and ";
                        } else if ("update".equals(values)) {
                            result = "T1." + nameSource + "=T2." + nameSource + ",";
                        } else {
                            result = "T2." + nameSource + ",";
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取类中对应的数据库字段，返回字段
     *
     * @param source
     * @throws Exception
     * @author 豪
     * @date 2019年3月2日
     */
    public static Set<String> findClassAnnotationNotId(Object source) throws Exception {
        Set<String> params = new LinkedHashSet<>();
        // 1:获取源实体类的所有属性，返回Field数组
        List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
        // 3:循环遍历源实体类得属性，获取属性名和类型和值
        for (Field field : listsFields) {
            if (!field.isAnnotationPresent(Id.class)) {
                Column column = field.getAnnotation(Column.class);
                String nameSource = column.name();
                params.add(nameSource);
            }
        }
        return params;
    }

    /**
     * 把传进来的参数拼接成T1.patient_id, T1.visit_id, T1.order_no,
     * T1.order_sub_no这种形式（用于merge into）
     *
     * @author 豪
     * @date 2019年3月2日
     */
    public static String insertCondition(Set<String> params, String alias) {
        StringBuilder sql = new StringBuilder();
        Iterator<String> it = params.iterator();
        String name = null;
        while (it.hasNext()) {
            name = it.next();
            sql.append(alias).append(".").append(name).append(",");
        }
        if (sql.length() > 0) {
            sql.delete(sql.length() - 1, sql.length());
        }
        return sql.toString();
    }

    /**
     * 把传进来的参数拼接 insert成T1.patient_id, T1.visit_id, T1.order_no,
     * T1.order_sub_no, T1.order_class这种形式（用于merge into）
     * update成T1.patient_id=T2.patient_id AND
     * T1.visit_id=T2.visit_id这种形式（用于merge into）
     *
     * @throws Exception
     * @author 豪
     * @date 2019年3月2日
     */
    public static String insertOrUpdateCondition(Set<String> fields, Object source, String saveOrUpdate)
            throws Exception {
        StringBuilder buf = new StringBuilder();
        for (String item : fields) {
            // 1:获取源实体类的所有属性，返回Field数组
            List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
            // 3:循环遍历源实体类得属性，获取属性名和类型和值
            String result = mergeIntoResult(listsFields, item, source, saveOrUpdate);
            buf.append(result);
        }
        if (buf.length() > 0) {
            buf.delete(buf.length() - 1, buf.length());
        }
        return buf.toString();
    }

    public static String selectCondition(Set<String> fields, Object source, String saveOrUpdate) throws Exception {
        StringBuilder buf = new StringBuilder();
        for (String item : fields) {
            // 1:获取源实体类的所有属性，返回Field数组
            List<Field> listsFields = ClassUtils.getFieldByAnnotation(source.getClass(), Column.class, true);
            // 3:循环遍历源实体类得属性，获取属性名和类型和值
            String result = mergeIntoResult(listsFields, item, source, saveOrUpdate);
            buf.append(result);
        }
        if (buf.length() > 0) {
            buf.delete(buf.length() - 5, buf.length());
        }
        return buf.toString();
    }

    /**
     * 判断当前对象这个属性是不是时间类型
     *
     * @param obj       对象
     * @param fieldName 属性名
     * @return
     * @throws Exception
     */
    public static Boolean isDate(Object obj, String fieldName) throws Exception {
        if (obj == null || StringUtils.isEmpty(fieldName))
            return null;
        // 获取对象的属性
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (fieldName.equals(field.getName())) {
            if (field.isAnnotationPresent(DateTrueOrFalse.class)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public static <T> void copyMapToBean(Map<String, String> dataMap, T record) {
        if (CommonUtils.empty(dataMap) || CommonUtils.empty(record)) {
            return;
        }
        try {
            BeanUtils.populate(record, dataMap);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * 功能描述:
     *
     * @auther: 豪
     * @date: 2019/9/3 18:00
     */
    public static <T> List<T> addList(List<T> lists) {
        List<T> listNew = new ArrayList<>();
        for (T entityOld : lists) {
            listNew.add(entityOld);
        }
        return listNew;
    }


    /**
     * 两个类相互转换的方法，从源类到去类，遍历源类的每一个成员，根据名字匹配，将源类的成员复制到去类中，假如去类不为空则跳过
     * @param fromObject 源类
     * @param toObject 去类
     * @param <T>
     * @param <K>
     * @return 去类
     * @throws ParseException
     * @throws IllegalAccessException
     */
    public static <T, K> T transfer(K fromObject, T toObject) {
        Field[] toObjectDeclaredFields = getAllFields(toObject);
        Field[] fromObjectDeclaredFields = getAllFields(fromObject);
        for (Field fromObjectDeclaredField : fromObjectDeclaredFields) {
            if (fromObjectDeclaredField.getName().equals("serialVersionUID")) {
                continue;
            }
            for (Field toObjectDeclaredField : toObjectDeclaredFields) {
                if (fromObjectDeclaredField.getName().equals(toObjectDeclaredField.getName())) {
                    //假如去类有值，不进行覆盖
                    if (ClassUtils.getValue(toObject, toObjectDeclaredField.getName()) != null) {
                        continue;
                    }
                    if (ClassUtils.getValue(fromObject, toObjectDeclaredField.getName()) != null) {
                        ClassUtils.setValue(toObject, fromObjectDeclaredField.getName(), ClassUtils.getValue(fromObject, toObjectDeclaredField.getName()), (Class) toObjectDeclaredField.getGenericType());
                    }
                }
            }
        }
        return toObject;
    }

    /**
     * 获取所有的field
     *
     * @param o
     * @return
     */
    public static Field[] getAllFields(Object o) {
        Class c = o.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (c != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(c.getDeclaredFields())));
            c = c.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }


}
