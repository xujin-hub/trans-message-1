package com.finest.app.pramas.special.person;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 * 全国查人、查车接口响应参数
 */
public class DeviceQueryResult implements Serializable {

    /**
     * 返回数据结果
     */
    private List<List<DeviceAttrDto>> dataList;

    public List<List<DeviceAttrDto>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<DeviceAttrDto>> dataList) {
        this.dataList = dataList;
    }
}
