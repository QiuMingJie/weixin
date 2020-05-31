package com.wechat.detal.common.util;

/**
 * @author QiuMingJie
 * @date 2020-01-26 21:43
 * @description 这个类用来记录一些魔法值和一些公共的生成方法
 */
public class FormUtil {
    /**
     * 输出之间的间隔，四个空格
     */
    public static String soutBank = "    ";


    /**
     * 计算下一个实体的id
     *
     * @param currentId 当前实体id E0001_001_0001
     * @return 下一个实体id E0001_001_0002
     */
    public static String caculFormEntityId(String currentId) {
        if (currentId.length() <= 10) {
            return currentId + "_0001";
        }
        String templateId = getFormDictId(currentId);
        String index = currentId.substring(11, 15);
        Integer i = splitZero(index) + 1;
        return templateId + fillInteger(i);
    }

    /**
     * 切前面的0，然后返回一个int
     *
     * @param str 0001
     * @return 1
     */
    private static Integer splitZero(String str) {
        int len = str.length();
        int index = 0;
        char[] strs = str.toCharArray();
        for (int i = 0; i < len; i++) {
            if ('0' != strs[i]) {
                index = i;
                break;
            }
        }
        return Integer.parseInt(str.substring(index, len));
    }

    /**
     * 填补数字前面的0到四位数和_
     *
     * @param i 1
     * @return 0001
     */
    private static String fillInteger(Integer i) {
        String s = String.valueOf(i);
        while (s.length() < 4) {
            s = "0" + s;
        }
        return "_" + s;
    }

    /**
     * 根据formId剪切得到formDictId
     *
     * @param formId 实体id E0001_001_0001
     * @return 字典id E0001_001
     */
    public static String getFormDictId(String formId) {
        return formId.substring(0, 10);
    }

    /**
     * 获取itemId的头部前缀
     *
     * @param formIdOrFormDictId 实体id E0001_001_0001 或字典id E0001_001
     * @param type               类型评估：I 措施:M 公共：C 拓展：K，为空时直接用I
     * @return I0001
     */
    public static String getItemDictHead(String formIdOrFormDictId, String type) {
        if (formIdOrFormDictId.length() < 5) {
            Validate.error("获取itemId长度出错，长度不足");
        }
        if (CommonUtils.empty(type)) {
            type = "I";
        }
        return type + formIdOrFormDictId.substring(1, 5);
    }

    /**
     * 计算itemId
     *
     * @param formIdOrFormDictId 实体id E0001_001_0001 或字典id E0001_001
     * @param type               类型评估：I 措施:M 公共：C 拓展：K，为空时直接用I
     * @param nextNum            下一位数2
     * @return I00010002
     */
    public static String caculItemDictId(String formIdOrFormDictId, String type, Integer nextNum) {
        return getItemDictHead(formIdOrFormDictId, type) + fillInteger(nextNum);
    }

    public static String caculItemDictId(String itemIdHead, Integer nextNum) {
        return itemIdHead + fillInteger(nextNum);
    }
}
