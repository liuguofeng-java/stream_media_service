package com.model.ffmepg.task.bean.tasks;

import com.model.dao.impl.DeviclistImpl;
import com.model.ffmepg.operation.ffmpeg.vidoe.FFMpegVideoFormatM3u8;
import com.model.ffmepg.task.bean.FFVideoTask;
import com.model.ffmepg.util.FFBigDecimalUtil;
import com.model.ffmepg.util.FFVideoUtil;
import com.model.pojo.Deviclist;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 视频转m3u8任务
 * com.ugirls.ffmepg.task.bean.tasks
 * 2018/6/11.
 *
 * @author zhanghaishan
 * @version V1.0
 */
@Component
@Log4j2
public class FFMepgVideoFormatM3u8Task extends FFVideoTask<FFMpegVideoFormatM3u8> implements ApplicationContextAware {

    /**
     * 进度正则查询
     */
    private String frameRegexDuration = "frame=([\\s,\\d]*) fps=(.*?) q=(.*?) size=([\\s\\S]*) time=(.*?) bitrate=([\\s\\S]*) speed=(.*?)x";

    /**
     * 正则模式
     */
    private Pattern framePattern = Pattern.compile(frameRegexDuration);

    @Override
    public void getPid(long pid) {
        super.getProgress().setPid(pid);
    }

    @Override
    public void callExecEnd() {

    }

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        FFMepgVideoFormatM3u8Task.applicationContext =  applicationContext;
    }
    @Override
    public void removeStream(long pid) {
        DeviclistImpl deviclistImpl = applicationContext.getBean(DeviclistImpl.class);
        try{
            Thread.sleep(1000);
            if(pid != -1){
                Deviclist device = deviclistImpl.queryPid(pid);
                System.out.println("pid"+pid);
                System.out.println(device);
                if(device != null){
                    device.setPid(null);
                    device.setStreampath(null);
                    device.setStreampath(null);
                    device.setIsrunstream(0);
                    deviclistImpl.updateDeviclist(device);
                    log.info("进程结束pid:" + pid);
                }
            }
        }catch(Exception ex){
            log.error("结束进程回调函数" + ex.getMessage());
        }
    }

    public FFMepgVideoFormatM3u8Task(){
        super();
    }

    /**
     * 任务构造
     * @param format 操作
     */
    public FFMepgVideoFormatM3u8Task(FFMpegVideoFormatM3u8 format){
        super(format);
    }

    /**
     * 回调
     * @param line 一行结果
     */
    @Override
    public void callBackResultLine(String line) {

        if(super.getTimeLengthSec()!=null){
            //获取视频信息
            Matcher m = framePattern.matcher(line);
            if(m.find()){
                try {
                    String execTimeStr = m.group(5);
                    int execTimeInt = FFVideoUtil.getTimelen(execTimeStr);
                    double devnum = FFBigDecimalUtil.div(execTimeInt,super.getTimeLengthSec(),5);
                    double progressDouble = FFBigDecimalUtil.mul(devnum,100);
                    super.getProgress().setProgress((int) progressDouble);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
