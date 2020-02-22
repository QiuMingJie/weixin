package com.wechat.detal.list;

import com.wechat.detal.common.util.CommonUtils;
import com.wechat.detal.config.ReturnDict;

import java.util.ArrayList;

/**
 * @author QiuMingJie
 * @date 2020-02-21 15:22
 * @description 改写toString方法，加密
 */
public class ResultStream<MessageStreamDto> extends ArrayList<MessageStreamDto> {

    @Override
    public String toString() {
        if (CommonUtils.empty(this)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            if (CommonUtils.empty(this.get(i))) {
                continue;
            }
            result.append(this.get(i).toString());
            if (i < this.size() - 1) {
                result.append(ReturnDict.BETWEEN_ORDER);
            }
        }
        return result.toString();
    }

    public String print() {
        if (CommonUtils.empty(this)) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(this.size());
        result.append("   ");
        for (int i = 0; i < this.size(); i++) {
            if (CommonUtils.empty(this.get(i))) {
                continue;
            }
            result.append(this.get(i).toString());
        }
        return result.toString();
    }
}