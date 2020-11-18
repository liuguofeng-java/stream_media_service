package com.model.haik.sdk;

import com.model.config.StreamMediaConfig;
import com.model.dao.impl.DeviclistImpl;
import com.model.pojo.Deviclist;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@Component
public class HaikSDK {
    private static final HCISUPCMS hCEhomeCMS = HCISUPCMS.INSTANCE;
    private static final HCISUPStream hCEhomeStream = HCISUPStream.INSTANCE;

    @Autowired
    private DeviclistImpl deviclistImpl;

    FPREVIEW_NEWLINK_CB fPREVIEW_NEWLINK_CB;//预览监听回调函数实现
    FPREVIEW_DATA_CB fPREVIEW_DATA_CB;//预览回调函数实现

    //预览请求输出参数
    NativeLong lPreviewHandle;//预览句柄

    HCISUPCMS.NET_EHOME_CMS_LISTEN_PARAM struCMSListenPara = new HCISUPCMS.NET_EHOME_CMS_LISTEN_PARAM();
    HCISUPStream.NET_EHOME_LISTEN_PREVIEW_CFG struListen = new HCISUPStream.NET_EHOME_LISTEN_PREVIEW_CFG();

    public HaikSDK() {
        fPREVIEW_NEWLINK_CB= null;
        fPREVIEW_DATA_CB = null;
        init();
    }

    /**
     * 注册回调函数
     */
    public class FRegisterCallBack implements HCISUPCMS.DEVICE_REGISTER_CB
    {
        public boolean invoke(NativeLong lUserID, int dwDataType, Pointer pOutBuffer, int dwOutLen, Pointer pInBuffer, int dwInLen, Pointer pUser)
        {
            log.info("注册回调函数, dwDataType:" + dwDataType+", lUserID:" + lUserID);
            switch (dwDataType)
            {
                case 0:  //ENUM_DEV_ON
                    HCISUPCMS.NET_EHOME_DEV_REG_INFO_V12 strDevRegInfo = new HCISUPCMS.NET_EHOME_DEV_REG_INFO_V12();
                    strDevRegInfo.write();
                    Pointer pDevRegInfo = strDevRegInfo.getPointer();
                    pDevRegInfo.write(0, pOutBuffer.getByteArray(0, strDevRegInfo.size()), 0, strDevRegInfo.size());
                    strDevRegInfo.read();

                    String deviceId = new String(strDevRegInfo.struRegInfo.byDeviceID).replaceAll("\\u0000","");
                    log.info("设备id" + deviceId);
                    //添加设备信息
                    return insertDevicInfo(deviceId, lUserID.intValue());
                case 1:
                    log.info("设备退出lUserID:" + lUserID);
                    //添加设备信息
                    return deviceQuit(lUserID.intValue());
                default:
                    log.info("FRegisterCallBack default type:" + dwDataType);
                    break;
            }
            return true;
        }
    }

    /**
     * 预览监听回调函数
     */
    public class FPREVIEW_NEWLINK_CB implements HCISUPStream.PREVIEW_NEWLINK_CB
    {
        public boolean invoke(NativeLong lLinkHandle, HCISUPStream.NET_EHOME_NEWLINK_CB_MSG pNewLinkCBMsg, Pointer pUserData)
        {
            log.info("FPREVIEW_NEWLINK_CB callback");
            lPreviewHandle = lLinkHandle;

            //预览数据回调参数
            HCISUPStream.NET_EHOME_PREVIEW_DATA_CB_PARAM struDataCB = new HCISUPStream.NET_EHOME_PREVIEW_DATA_CB_PARAM();
            if (fPREVIEW_DATA_CB == null)
            {
                fPREVIEW_DATA_CB= new FPREVIEW_DATA_CB();
            }
            struDataCB.fnPreviewDataCB = fPREVIEW_DATA_CB;

            if (!hCEhomeStream.NET_ESTREAM_SetPreviewDataCB(lLinkHandle, struDataCB))
            {
                log.error("NET_ESTREAM_SetPreviewDataCB()错误代码号：" + hCEhomeStream.NET_ESTREAM_GetLastError());
                return false;
            }
            return true;
        }
    }

    /**
     * 实时流回调函数
     */
    public class FPREVIEW_DATA_CB implements HCISUPStream.PREVIEW_DATA_CB
    {
        public void invoke(NativeLong iPreviewHandle, HCISUPStream.NET_EHOME_PREVIEW_CB_MSG pPreviewCBMsg, Pointer pUserData)
        {
            try{
                Deviclist deviclist = deviclistImpl.queryLuserID(0);
                FileOutputStream out = new FileOutputStream(new File(deviclist.getVideopath()),true);
                long offset = 0;
                ByteBuffer byteBuffer = pPreviewCBMsg.pRecvdata.getByteBuffer(offset,pPreviewCBMsg.dwDataLen);
                byte[] bytes = new byte[pPreviewCBMsg.dwDataLen];
                byteBuffer.get(bytes);
                out.write(bytes);
                out.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * 初始化SDK
     */
    public void init() {
        //CMS注册模块初始化
        boolean binit = hCEhomeCMS.NET_ECMS_Init();
        if(!binit){
            log.error("CMS注册模块初始化失败");
        }
        //注册监听参数
        FRegisterCallBack fRegisterCallBack= new FRegisterCallBack();//注册回调函数实现

        struCMSListenPara.struAddress.szIP = StreamMediaConfig.intranetIp.getBytes();
        struCMSListenPara.struAddress.wPort = 7660;
        struCMSListenPara.fnCB = fRegisterCallBack;

        //启动监听，接收设备注册信息
        NativeLong lListen = hCEhomeCMS.NET_ECMS_StartListen(struCMSListenPara);
        if(lListen.longValue() < -1)
        {
            log.error("NET_ECMS_StartListen failed, error code:" + hCEhomeCMS.NET_ECMS_GetLastError());
            hCEhomeCMS.NET_ECMS_Fini();
            return;
        }
        log.info("NET_ECMS_StartListen启动注册监听成功!");
    }

    /**
     * 保存录像
     * @return 是否成功
     */
    public boolean StartPreview(int Id) {
        NativeLong lLoginID = new NativeLong(Id);
        ////////////////////////////////////////////////////////////////////////
        //流媒体服务器(VTDU)监听取流
        //取流库初始化
        boolean nInit = hCEhomeStream.NET_ESTREAM_Init();
        if(!nInit){
            log.error("取流库初始化");
        }

        //预览监听参数
        if (fPREVIEW_NEWLINK_CB == null)
        {
            fPREVIEW_NEWLINK_CB= new FPREVIEW_NEWLINK_CB();
        }

        struListen.struIPAdress.szIP = StreamMediaConfig.serviceIp.getBytes();
        struListen.struIPAdress.wPort = 9093; //流媒体服务器监听端口
        struListen.fnNewLinkCB = fPREVIEW_NEWLINK_CB; //预览连接请求回调函数
        struListen.pUser = null;
        struListen.byLinkMode = 1; //0- TCP方式，1- UDP方式

        //启动预览监听
        if(lPreviewHandle.byteValue() < 0)
        {
            NativeLong lHandle = hCEhomeStream.NET_ESTREAM_StartListenPreview(struListen);
            if(lHandle.longValue() < -1)
            {
                log.error("NET_ESTREAM_StartListenPreview failed, error code:" + hCEhomeStream.NET_ESTREAM_GetLastError());
                hCEhomeStream.NET_ESTREAM_Fini();
                return false;
            }
            log.info("NET_ESTREAM_StartListenPreview启动预览监听成功!");

            //预览请求输入参数
            HCISUPCMS.NET_EHOME_PREVIEWINFO_IN struPreviewIn = new HCISUPCMS.NET_EHOME_PREVIEWINFO_IN();
            struPreviewIn.iChannel = 1; //通道号
            struPreviewIn.dwLinkMode = 1; //0- TCP方式，1- UDP方式
            struPreviewIn.dwStreamType = 0; //码流类型：0- 主码流，1- 子码流, 2- 第三码流
            struPreviewIn.struStreamSever.szIP = StreamMediaConfig.serviceIp.getBytes();//流媒体服务器IP地址
            struPreviewIn.struStreamSever.wPort = 9093; //流媒体服务器端口，需要跟服务器启动监听端口一致

            //预览请求
            HCISUPCMS.NET_EHOME_PREVIEWINFO_OUT struPreviewOut = new HCISUPCMS.NET_EHOME_PREVIEWINFO_OUT();
            if(!hCEhomeCMS.NET_ECMS_StartGetRealStream(lLoginID, struPreviewIn, struPreviewOut))
            {
                log.error("NET_ECMS_StartGetRealStream failed, error code:" + hCEhomeCMS.NET_ECMS_GetLastError());
                return false;
            }

            struPreviewOut.read();
            log.info("NET_ECMS_StartGetRealStream预览请求成功, sessionID:" + struPreviewOut.lSessionID);

            HCISUPCMS.NET_EHOME_PUSHSTREAM_IN struPushInfoIn = new HCISUPCMS.NET_EHOME_PUSHSTREAM_IN();
            struPushInfoIn.read();
            struPushInfoIn.dwSize = struPushInfoIn.size();
            struPushInfoIn.lSessionID = struPreviewOut.lSessionID;
            struPushInfoIn.write();

            HCISUPCMS.NET_EHOME_PUSHSTREAM_OUT struPushInfoOut = new HCISUPCMS.NET_EHOME_PUSHSTREAM_OUT();
            struPushInfoOut.read();
            struPushInfoOut.dwSize = struPushInfoOut.size();
            struPushInfoOut.write();
            if(!hCEhomeCMS.NET_ECMS_StartPushRealStream(lLoginID,struPushInfoIn, struPushInfoOut)){
                log.error("NET_ECMS_StartPushRealStream failed, error code:" + hCEhomeCMS.NET_ECMS_GetLastError());
                return false;
            }
            log.info("NET_ECMS_StartPushRealStream succeed, sessionID:" + struPushInfoIn.lSessionID);
        }else {
            log.info("lPreviewHandle" + lPreviewHandle.byteValue());
        }
        return true;
    }

    /**
     * 停止录像
     */
    public boolean StopPreview(int loginID,int sessionID) {
        NativeLong lSessionID = new NativeLong(sessionID);
        NativeLong lLoginID = new NativeLong(loginID);
        //释放CMS预览请求资源
        if(!hCEhomeCMS.NET_ECMS_StopGetRealStream(lLoginID, lSessionID))
        {
            JOptionPane.showMessageDialog(null,"NET_ECMS_StopGetRealStream failed, error code: %d\n" + hCEhomeCMS.NET_ECMS_GetLastError());
            return false;
        }
        return true;
    }


    //添加设备信息
    public boolean insertDevicInfo(String devicId, Integer luserID){
        try{
            //记录数据
            Deviclist devic = new Deviclist();
            devic.setDevicid(devicId);

            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            devic.setAddtime(format);
            devic.setLuserid(luserID);
            boolean b = deviclistImpl.insertDeviclist(devic);
            if(!b){
                log.error("注册回调函数添加记录失败");
                return false;
            }

            return true;
        }catch (Exception ex){
            log.error("注册回调函数添加记录失败" + ex.getMessage());
            return false;
        }
    }

    //设备退出记录
    public boolean deviceQuit(Integer luserID){
        try{
            return deviclistImpl.deleteLuserID(luserID);
        }catch (Exception ex){
            log.error("设备退出记录" + ex.getMessage());
            return false;
        }
    }

}