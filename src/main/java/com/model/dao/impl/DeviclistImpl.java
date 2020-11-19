package com.model.dao.impl;

import com.model.dao.DeviclistDao;
import com.model.mapper.DeviclistMapper;
import com.model.pojo.Deviclist;
import com.model.pojo.DeviclistExample;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuguofeng
 * @title: DeviclistMapperImpl
 * @projectName stream_media_service
 * @description: TODO
 * @date 2020/10/2320:32
 */
@Service
@Log4j2
public class DeviclistImpl implements DeviclistDao {

    @Autowired
    private DeviclistMapper deviclistMapper;

    @Override
    public Deviclist queryDeviclistOrderNo(String orderNo) {
        DeviclistExample example = new DeviclistExample();
        DeviclistExample.Criteria criteria = example.createCriteria();
        criteria.andOrdernoEqualTo(orderNo);
        List<Deviclist> deviclists = deviclistMapper.selectByExample(example);
        if(deviclists.size() == 0){
            return null;
        }else {
            return deviclists.get(0);
        }
    }

    @Override
    public List<Deviclist> queryDeviclistAll() {
        return deviclistMapper.selectByExample(null);
    }

    @Override
    public List<Deviclist> queryDevicDevicId(String devicId) {
        DeviclistExample example = new DeviclistExample();
        DeviclistExample.Criteria criteria = example.createCriteria();
        criteria.andDevicidEqualTo(devicId);
        List<Deviclist> deviclists = deviclistMapper.selectByExample(example);
        return deviclists;
    }

    @Override
    public boolean insertDeviclist(Deviclist deviclist) {
        int insert = deviclistMapper.insert(deviclist);
        return insert > 0;
    }

    @Override
    public boolean updateDeviclist(Deviclist deviclist) {
        DeviclistExample example = new DeviclistExample();
        DeviclistExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(deviclist.getId());
        int i = deviclistMapper.updateByExample(deviclist,example);
        return i > 0;
    }

    @Override
    public Deviclist queryLuserID(Integer luserID) {
        DeviclistExample example = new DeviclistExample();
        DeviclistExample.Criteria criteria = example.createCriteria();
        criteria.andLuseridEqualTo(luserID);
        List<Deviclist> deviclists = deviclistMapper.selectByExample(example);
        if(deviclists.size() == 0){
            return null;
        }else {
            return deviclists.get(0);
        }
    }

    @Override
    public boolean deleteLuserID(Integer luserID) {
        Deviclist deviclist = queryLuserID(luserID);//查询是否存在
        if(deviclist != null &&deviclist.getVideopath() == null){//如果没有保存MP4就删除
            DeviclistExample example = new DeviclistExample();
            DeviclistExample.Criteria criteria = example.createCriteria();
            criteria.andLuseridEqualTo(luserID);
            int i = deviclistMapper.deleteByExample(example);
            return i > 0;
        }else if(deviclist != null ){
            deviclist.setLuserid(null);
            deviclist.setDevicid(null);

            DeviclistExample example = new DeviclistExample();
            DeviclistExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(deviclist.getId());
            int i = deviclistMapper.updateByExample(deviclist, example);
            return i > 0;
        }
        return false;
    }

    @Override
    public boolean deleteTable() {
        int i = deviclistMapper.decdeleteTable();
        return i > 0;
    }

    @Override
    public Deviclist queryPid(long pid) {
        DeviclistExample example = new DeviclistExample();
        DeviclistExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo((int) pid);
        List<Deviclist> deviclists = deviclistMapper.selectByExample(example);
        if(deviclists.size() == 0){
            return null;
        }else {
            return deviclists.get(0);
        }
    }

    @Override
    public Deviclist queryDevicDevicSessionId(int sessionId) {
        DeviclistExample example = new DeviclistExample();
        DeviclistExample.Criteria criteria = example.createCriteria();
        criteria.andSessionidEqualTo(sessionId);
        List<Deviclist> deviclists = deviclistMapper.selectByExample(example);
        if(deviclists.size() == 0){
            return null;
        }else {
            return deviclists.get(0);
        }
    }

    @Override
    public boolean updateDeviclistAll(Deviclist deviclist) {
        DeviclistExample example = new DeviclistExample();
        DeviclistExample.Criteria criteria = example.createCriteria();
        criteria.andDevicidEqualTo(deviclist.getDevicid());
        Deviclist device = new Deviclist();
        device.setDevicid(deviclist.getDevicid());
        device.setLuserid(deviclist.getLuserid());
        int flag = deviclistMapper.updateByExample(deviclist, example);
        return flag > 0;
    }
}
