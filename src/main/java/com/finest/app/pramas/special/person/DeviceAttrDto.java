package com.finest.app.pramas.special.person;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/28.
 */
public class DeviceAttrDto implements Serializable {

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性值
     */
    private Object attrValue;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Object getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(Object attrValue) {
        this.attrValue = attrValue;
    }
}
