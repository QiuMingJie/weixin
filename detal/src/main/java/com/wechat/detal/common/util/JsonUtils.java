package com.wechat.detal.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JsonUtils
 */
public final class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * @param id
     * @return
     */
    public static String getString(Long id) {
        return String.format("id:%s", id);
    }

    /**
     * @param object
     * @return
     */
    public static String getString(Object object) {
        return getString(object, false);
    }

    /**
     * @param object
     * @return
     */
    public static String getString(Object object, boolean pretty) {
        if (object == null) return null;
        try {
            if (pretty) {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            } else {
                return objectMapper.writeValueAsString(object);
            }
        } catch (IOException e) {
            return null;
        }
    }

}
