package com.model.controller;

import com.model.config.StreamMediaConfig;
import com.model.dao.impl.DeviclistImpl;
import com.model.haik.sdk.HaikSDK;
import com.model.pojo.Deviclist;
import com.model.util.FFMpegUtli;
import com.model.util.Result;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.nio.file.StandardOpenOption.READ;

@Controller
@RequestMapping("/core")
@Log4j2
public class CoreController {

    @Autowired
    private DeviclistImpl deviclistImpl;

    @Autowired
    public HaikSDK haikSDK;

    @RequestMapping(value = "/video", method = RequestMethod.GET)
    public String index() {
        return "video";
    }

    /**
     * 保存MP4
     *
     * @param orderNo  订单号
     * @param deviceId 设备号
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/startmp4", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public Result startMp4(String orderNo, String deviceId) throws InterruptedException {
        if (StringUtils.isEmpty(orderNo)) {
            return Result.formatToPojo("订单不能为空");
        }
        if (StringUtils.isEmpty(deviceId)) {
            return Result.formatToPojo("设备不能为空");
        }
        Deviclist devic = deviclistImpl.queryDevicDevicId(deviceId);
        if (devic == null) {
            return Result.formatToPojo("找不到设备");
        }
        String date = new SimpleDateFormat("MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH-mm").format(new Date());
        String savePath = StreamMediaConfig.mp4SavePath + date + "\\" + time + "\\";
        String videoPath = savePath + orderNo + ".mp4";
        devic.setVideopath(videoPath);
        devic.setDevicid(deviceId);
        devic.setOrderno(orderNo);
        boolean updateDeviclist = deviclistImpl.updateDeviclist(devic);
        if (!updateDeviclist) {
            log.error("修改数据订单号失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
            return Result.formatToPojo("修改数据失败");
        }
        File path = new File(savePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        //海康SDK保存MP4文件
        boolean startPreview = haikSDK.StartPreview(devic.getLuserid());
        if (!startPreview) {
            log.error("海康SDK保存MP4时异常");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
            return Result.formatToPojo("保存数据异常");
        }
        return Result.formatToPojo(true);
    }

    /**
     * 结束保存MP4
     *
     * @param orderNo  订单号
     * @param deviceId 设备号
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/stopmp4", method = RequestMethod.GET)
    public Result stopMp4(String orderNo, String deviceId) {
        if (StringUtils.isEmpty(orderNo)) {
            return Result.formatToPojo("订单不能为空");
        }

        if (StringUtils.isEmpty(deviceId)) {
            return Result.formatToPojo("设备不能为空");
        }
        boolean StopPreview = haikSDK.StopPreview();
        if (!StopPreview) {
            return Result.formatToPojo("停止失败");
        }
        Deviclist d = deviclistImpl.queryDeviclistOrderNo(orderNo);
        if(d != null){
            d.setPid(null);
            d.setStreampath(null);
            d.setStreampath(null);
            deviclistImpl.updateDeviclist(d);
        }
        return Result.formatToPojo(true);
    }

    /**
     * 推流
     *
     * @param orderNo  订单号
     * @param deviceId 设备号
     * @return 推流地址
     */
    @ResponseBody
    @RequestMapping(value = "/getstream", method = RequestMethod.GET)
    public Result getStream(String orderNo, String deviceId) {
        try {
            if (StringUtils.isEmpty(orderNo)) {
                return Result.formatToPojo("订单不能为空");
            }
            if (StringUtils.isEmpty(deviceId)) {
                return Result.formatToPojo("设备不能为空");
            }
            //判断码流是不是存在
            String path = deviclistImpl.queryOrderNoAndStreamPathNotNull(orderNo);
            if (path != null) {
                return Result.formatToPojo(path);
            }
            //查询设备是否注册
            Deviclist deviclist = deviclistImpl.queryDeviclistOrderNo(orderNo);
            if (deviclist == null) {
                return Result.formatToPojo("设备不在线");
            }
            //调用线程池
            String format = new SimpleDateFormat("MM-dd-hh-mm").format(new Date());
            String m3u8Path = format + "\\" + orderNo;
            Map<String,Object> m3u8 = new FFMpegUtli().getM3u8(deviclist.getVideopath(), m3u8Path);
            if (!(Boolean) m3u8.get("bool")) {
                Result.formatToPojo("转流失败");
            }
            //码流
            String stream = StreamMediaConfig.m3u8Url + m3u8Path.replace("\\", "/") + ".m3u8";
            //添加推流地址
            deviclist.setStreampath(stream);
            long pid = (long)m3u8.get("pid");
            deviclist.setPid((int) pid);
            boolean b = deviclistImpl.updateDeviclist(deviclist);
            if (!b)
                log.error("添加推流地址失败");
            return Result.formatToPojo(true);
        } catch (Exception ex) {
            log.error("转流失败:" + ex.getMessage());
            return Result.formatToPojo("转流失败");
        }
    }

    /**
     * 停止推流
     * @param orderNo 订单
     * @return 是否停止成功
     */
    @ResponseBody
    @RequestMapping(value = "/stopstream",method = RequestMethod.GET)
    public Result stopStream(String orderNo) {
        try {
            Deviclist deviclist = deviclistImpl.queryDeviclistOrderNo(orderNo);
            if(new FFMpegUtli().stopStream(deviclist)){
                Deviclist d = deviclistImpl.queryDeviclistOrderNo(orderNo);
                if(d != null){
                    log.info("进程结束pid:" + d.getPid());
                    d.setPid(null);
                    d.setStreampath(null);
                    d.setStreampath(null);
                    deviclistImpl.updateDeviclist(d);
                }
                return Result.formatToPojo(true);
            }else {
                return Result.formatToPojo("停止推流失败");
            }

        }catch(Exception ex){
            log.error("停止推流出错" + ex.getMessage());
            return Result.formatToPojo("停止推流出错");
        }
    }


    /**
     * 获取流
     *
     * @param orderNo  订单号
     * @param deviceId 设备号
     * @return 推流地址
     */
    @ResponseBody
    @RequestMapping(value = "/GetHls", method = RequestMethod.POST)
    public Result GetHls(String orderNo, String deviceId) {
        if (StringUtils.isEmpty(orderNo)) {
            return Result.formatToPojo("订单不能为空");
        }
        if (StringUtils.isEmpty(deviceId)) {
            return Result.formatToPojo("设备不能为空");
        }
        //判断码流是不是存在
        String path = deviclistImpl.queryOrderNoAndStreamPathNotNull(orderNo);
        if (path == null) {
            return Result.formatToPojo("没有捕获到任何媒体流");
        } else {
            return Result.formatToPojo(path);
        }
    }


    private static final int BUFFER_LENGTH = 1024 * 16;
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;
    private static final Pattern RANGE_PATTERN = Pattern.compile("bytes=(?<start>\\d*)-(?<end>\\d*)");
    private String videoPath;

    @RequestMapping("/getVideoStream")
    public void getVideoStream(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String videoFilename = URLDecoder.decode(request.getParameter("video"), "UTF-8");
        videoPath = "C:\\Users\\13961\\Desktop\\47276514.mp4";
        Path video = Paths.get(videoPath, videoFilename);

        int length = (int) Files.size(video);
        int start = 0;
        int end = length - 1;

        String range = request.getHeader("Range");
        range = range == null ? "" : range;
        Matcher matcher = RANGE_PATTERN.matcher(range);

        if (matcher.matches()) {
            String startGroup = matcher.group("start");
            start = startGroup.isEmpty() ? start : Integer.parseInt(startGroup);
            start = Math.max(start, 0);

            String endGroup = matcher.group("end");
            end = endGroup.isEmpty() ? end : Integer.parseInt(endGroup);
            end = Math.min(end, length - 1);
        }

        int contentLength = end - start + 1;

        response.reset();
        response.setBufferSize(BUFFER_LENGTH);
        response.setHeader("Content-Disposition", String.format("inline;filename=\"%s\"", videoFilename));
        response.setHeader("Accept-Ranges", "bytes");
        response.setDateHeader("Last-Modified", Files.getLastModifiedTime(video).toMillis());
        response.setDateHeader("Expires", System.currentTimeMillis() + EXPIRE_TIME);
        response.setContentType(Files.probeContentType(video));
        response.setHeader("Content-Range", String.format("bytes %s-%s/%s", start, end, length));
        response.setHeader("Content-Length", String.format("%s", contentLength));
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

        int bytesRead;
        int bytesLeft = contentLength;
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);

        try (SeekableByteChannel input = Files.newByteChannel(video, READ);
             OutputStream output = response.getOutputStream()) {

            input.position(start);

            while ((bytesRead = input.read(buffer)) != -1 && bytesLeft > 0) {
                buffer.clear();
                output.write(buffer.array(), 0, Math.min(bytesLeft, bytesRead));
                bytesLeft -= bytesRead;
            }
        }
    }
}
