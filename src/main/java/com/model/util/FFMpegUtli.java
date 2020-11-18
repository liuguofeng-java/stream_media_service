package com.model.util;

import com.model.config.StreamMediaConfig;
import com.model.ffmepg.operation.ffmpeg.vidoe.FFMpegVideoFormatM3u8;
import com.model.ffmepg.operation.ffmpeg.vidoe.FFMpegVideoInfo;
import com.model.ffmepg.result.defaultResult.FFVideoInfoResult;
import com.model.ffmepg.task.bean.FFTaskStateEnum;
import com.model.ffmepg.task.bean.tasks.FFMepgVideoFormatM3u8Task;
import com.model.ffmepg.task.bean.tasks.FFMepgVideoInfoTask;
import com.model.ffmepg.task.context.FFTaskContext;
import com.model.ffmepg.util.FFTerminalCreater;
import com.model.pojo.Deviclist;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuguofeng
 * @title: FFMpegUtli
 * @projectName stream_media_service
 * @description: TODO
 * @date 2020/10/2322:45
 */
@Component
@Log4j2
public class FFMpegUtli {
    private FFMepgVideoFormatM3u8Task fFMepgVideoFormatM3u8Task;
    /**
     * 推流
     * @param filePath 保存路径
     * @param m3u8Path 推流子路径
     * @return 推流状态
     * @throws ParseException 时间格式
     */
    public Map<String,Object> getM3u8(String filePath, String m3u8Path) throws ParseException {
        FFMpegVideoFormatM3u8 m3u8Operation = new FFMpegVideoFormatM3u8();
        m3u8Operation.setVideoFileName(filePath);
        m3u8Operation.setBitrate("2048k");
        m3u8Operation.setTimes(5);

        FFVideoInfoResult result = new FFVideoInfoResult();
        FFMpegVideoInfo ffMpegVideoInfo = new FFMpegVideoInfo();
        ffMpegVideoInfo.setVideoUrl(filePath);
        FFMepgVideoInfoTask videoInfoTask = new FFMepgVideoInfoTask(result,ffMpegVideoInfo);
        FFTaskContext.getContext().submit(videoInfoTask,null);
        String timeLength = result.getTimeLength();
        timeLength = timeLength.substring(0,timeLength.indexOf("."));
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date parse = df.parse(timeLength);
        long time = parse.getTime() - (5 * 1000);
        Date date = new Date(time);
        String format = df.format(date);

        m3u8Operation.setPlayTime(format + ".00");
        String savePath = StreamMediaConfig.nginxPath + "\\" + m3u8Path;
        File file = new File(savePath);
        if(!file .exists()) {//如果文目录不存在就创建
            file.mkdirs();
        }
        m3u8Operation.setM3u8File(savePath + ".m3u8");
        m3u8Operation.setTsFiles(savePath + "%5d.ts");
        FFMepgVideoFormatM3u8Task task = new FFMepgVideoFormatM3u8Task(m3u8Operation);
        FFTaskContext.getContext().addTask(task);
        Map<String,Object> map = new HashMap<>();
        while (!task.getProgress().getState().equals(FFTaskStateEnum.COMPLETE)){
            try {
                Thread.sleep(1000);
                int progress = task.getProgress().getProgress();
                long pid = task.getProgress().getPid();
                if(progress >= 1){
                    map.put("bool",true);
                    map.put("pid",pid);
                    return map;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                map.put("bool",false);
                map.put("pid",-1);
            }
        }
        return map;
    }

    /**
     * 停止推流
     * @param deviclist 实体
     */
    public boolean stopStream(Deviclist deviclist) throws IOException {
        if(deviclist != null && !StringUtils.isEmpty(deviclist.getPid())){
            String cmd = "cmd.exe /c taskkill /PID "+ deviclist.getPid() +" /F /T ";
            FFTerminalCreater.FFTerminal terminal = FFTerminalCreater.getCreater().getTerminal(cmd);
            String result = terminal.readLine();
            log.info(result);
            return result.contains("成功");
        }
        return false;
    }


}
