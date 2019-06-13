package com.chengzg.oms.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *   46000 中国移动
 * 46001 中国联通
 * 46002 中国移动
 * 46003 中国电信
 * 46005 中国电信
 * 46006 中国联通
 * 46007 中国移动
 * Created by chengzg3 on 2018/5/11.
 */
public enum Networktor {
    /**
     *
     */
    E46000("中国移动", "46000"),
    E46001("中国联通", "46001"),
    E46002("中国移动", "46002"),
    E46003("中国电信", "46003"),
    E46005("中国电信", "46005"),
    E46006("中国联通", "46006"),
    E46007("中国移动", "46007"),
    ;
    /**
     * 1
     */
    private String name;
    /**
     * 2
     */
    private String code;

    private Networktor(String name, String code) {
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
        for (Networktor s : EnumSet.allOf(Networktor.class)) {
            lookup.put(s.getCode(), s.getName());
        }
    }

    /**
     *
     * @return
     */
    public static Networktor getRandomOne() {
        Random ran2 = new Random(7);
        Integer random = ran2.nextInt(7);
        if (random == 1) {
            return Networktor.E46000;
        } else if (random == 2) {
            return Networktor.E46001;

        } else if (random == 3) {
            return Networktor.E46002;

        } else if (random == 4) {
            return Networktor.E46003;

        } else if (random == 5) {
            return Networktor.E46005;

        } else if (random == 6) {
            return Networktor.E46006;

        } else if (random == 7) {
            return Networktor.E46007;
        }

        return E46007;
    }
}
