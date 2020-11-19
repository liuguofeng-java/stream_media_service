package com.model.dao;

import com.model.pojo.Deviclist;

import java.util.List;

/**
 * @author liuguofeng
 * @title: DeviclistMapperDao
 * @projectName stream_media_service
 * @description: TODO
 * @date 2020/10/2320:32
 */
public interface DeviclistDao {
    /**
     * 根据设备id查询
     * @param orderNo 设备id
     * @return Deviclist
     */
    Deviclist queryDeviclistOrderNo(String orderNo);

    /**
     * 查询全部
     * @return List<Deviclist>
     */
    List<Deviclist> queryDeviclistAll();

    /**
     * 根据设备id查询
     * @param devicId 设备id
     * @return Deviclist
     */
    List<Deviclist> queryDevicDevicId(String devicId);

    /**
     * 添加对象
     * @param deviclist deviclist
     * @return boolean
     */
    boolean insertDeviclist(Deviclist deviclist);

    /**
     * 根据订单修改推流地址
     * @param deviclist 对象
     * @return boolean
     */
    boolean updateDeviclist(Deviclist deviclist);


    /**
     * 根据设备唯一id查询
     * @param luserID 设备唯一id
     * @return Deviclist
     */
    Deviclist queryLuserID(Integer luserID);

    /**
     * 根据设备唯一删除
     * @param luserID 设备唯一id
     * @return Deviclist
     */
    boolean deleteLuserID(Integer luserID);

    /**
     * 清空表
     * @return boolean
     */
    boolean deleteTable();

    /**
     * 根据pid查询
     * @param pid 进程pid
     * @return Deviclist
     */
    Deviclist queryPid(long pid);


    /**
     * 根据sessionId查询
     * @param sessionId 设备预览id，调用停止预览方法 sessionId失效
     * @return Deviclist
     */
    Deviclist queryDevicDevicSessionId(int sessionId);

    /**
     * 改变userId
     * @param deviclist
     * @return
     */
    boolean updateDeviclistAll(Deviclist deviclist);
}
