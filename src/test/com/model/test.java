package com.model;

import com.model.dao.impl.DeviclistImpl;
import com.model.ffmepg.operation.ffmpeg.vidoe.FFMpegVideoInfo;
import com.model.ffmepg.result.defaultResult.FFVideoInfoResult;
import com.model.ffmepg.task.bean.tasks.FFMepgVideoInfoTask;
import com.model.ffmepg.task.context.FFTaskContext;
import com.model.util.FFMpegUtli;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;

/**
 * @author liuguofeng
 * @title: test
 * @projectName stream_media_service
 * @description: TODO
 * @date 2020/10/2418:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StreamMediaServiceApplication.class})
@SpringBootTest
@Log4j2
public class test {


    public void getRtmp(){

    }

    @Test
    public void testM3u8() throws Exception {
        new FFMpegUtli().getM3u8("C:\\Users\\13961\\Desktop\\47276514.mp4","test");
        /*FFMpegVideoFormatM3u8 m3u8Operation = new FFMpegVideoFormatM3u8();
        m3u8Operation.setVideoFileName("C:\\Users\\13961\\Desktop\\video\\2020-10-23\\13-54-12\\47276514.mp4");
        m3u8Operation.setBitrate("2048k");
        m3u8Operation.setTimes(5);
        m3u8Operation.setM3u8File("D:\\nginx-1.18.0\\html\\hls\\test.m3u8");
        m3u8Operation.setTsFiles("D:\\nginx-1.18.0\\html\\hls\\test%5d.ts");

        System.out.println(m3u8Operation.toString());

        FFMepgVideoFormatM3u8Task task = new FFMepgVideoFormatM3u8Task(m3u8Operation);

        FFTaskContext.getContext().addTask(task);

        while (!task.getProgress().getState().equals(FFTaskStateEnum.COMPLETE)){
            System.out.println(task.getProgress().getProgress());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }


    @Test
    public void testInfo(){
        FFVideoInfoResult result = new FFVideoInfoResult();
        FFMpegVideoInfo ffMpegVideoInfo = new FFMpegVideoInfo();
        ffMpegVideoInfo.setVideoUrl("C:\\Users\\13961\\Desktop\\video\\2020-10-23\\10-28-24\\47276514.mp4");
        FFMepgVideoInfoTask videoInfoTask = new FFMepgVideoInfoTask(result,ffMpegVideoInfo);

        FFTaskContext.getContext().submit(videoInfoTask,null);

        System.out.println(result.getTimeLengthSec());
        System.out.println(result.getTimeLength());
        System.out.println(result.getStartTime());
        System.out.println(result.getBitrate());
        System.out.println(result.getWidth());
        System.out.println(result.getHeight());
        System.out.println(result.getFps());
        System.out.println(result.getTbr());
        System.out.println(result.getTbn());
        System.out.println(result.getTbc());
    }

    @Autowired
    private DeviclistImpl deviclistImpl;

    @Test
    public void devicList(){
        /*Deviclist deviclist = new Deviclist();
        deviclist.setId(1);
        deviclist.setDevicid("2020");
        deviclist.setOrderno("20201920");
        deviclist.setVideopath("c://sec.mp4");
        String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        deviclist.setAddtime(format);
        deviclistImpl.insertDeviclist(deviclist);*/
        //List<Deviclist> deviclists = deviclistImpl.queryDeviclistAll();
        //System.out.println(deviclists);


        /*//记录数据
        Deviclist devic = new Deviclist();
        devic.setDevicid("xx");

        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        devic.setAddtime(format);

        String date = new SimpleDateFormat("MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH-mm").format(new Date());
        String savePath = StreamMediaConfig.mp4SavePath + date + "\\" + time + "\\";
        devic.setVideopath("C:\\Users\\13961\\Desktop\\video\\10-26\\12-37\\"+savePath + ".mp4");

        boolean b = deviclistImpl.insertDeviclist(devic);*/
    }

}
