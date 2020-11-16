package com.model.ffmepg.operation.ffmpeg.vidoe;

import com.model.ffmepg.annotation.FFCmd;
import com.model.ffmepg.operation.ffmpeg.FFMpegOperationBase;

/**
 * FFmpeg操作父类
 * com.ugirls.ffmepg.operation.ffmpeg.vidoe
 * 2018/6/11.
 *
 * @author zhanghaishan
 * @version V1.0
 */
public class FFMpegVideoInfo extends FFMpegOperationBase {

    @FFCmd(key = "-i")
    private String videoUrl;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
