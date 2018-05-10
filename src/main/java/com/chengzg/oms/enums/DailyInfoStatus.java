package com.chengzg.oms.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengzg3 on 2018/5/10.
 */
public enum DailyInfoStatus {
    unexport("未导入", 0),
    exported("已导入", 1),
    ;

    private String name;
    private int index;

    private DailyInfoStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * enum lookup map
     */
    public static final Map<Integer, String> lookup = new HashMap<Integer, String>();

    static {
        for (DailyInfoStatus s : EnumSet.allOf(DailyInfoStatus.class)) {
            lookup.put(s.getIndex(), s.getName());
        }
    }
}
