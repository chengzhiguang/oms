package com.chengzg.oms.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengzg3 on 2018/5/11.
 */
public enum UserType {
    ADMIN("管理员", "ADMIN")
    ;

    private String name;
    private String code;

    private UserType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * enum lookup map
     */
    public static final Map<String, String> lookup = new HashMap<String, String>();

    static {
        for (UserType s : EnumSet.allOf(UserType.class)) {
            lookup.put(s.getCode(), s.getName());
        }
    }
}
