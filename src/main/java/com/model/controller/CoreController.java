package com.model.controller;

import com.model.config.StreamMediaConfig;
import com.model.dao.impl.DeviclistImpl;
import com.model.haik.sdk.HaikSDK;
import com.model.pojo.Deviclist;
import com.model.util.FFMpegUtli;
import com.model.util.Result;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
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
    public Result startMp4(String orderNo, String deviceId) {
       try{
           if (StringUtils.isEmpty(orderNo)) {
               return Result.formatToPojo(400,"订单不能为空");
           }
           if (StringUtils.isEmpty(deviceId)) {
               return Result.formatToPojo(400,"设备不能为空");
           }
           List<Deviclist> devices = deviclistImpl.queryDevicDevicId(deviceId);
           if (devices.size() == 0) {
               return Result.formatToPojo(400,"找不到设备");
           }
           //编辑文件夹格式
           String date = new SimpleDateFormat("MM-dd").format(new Date());
           String time = new SimpleDateFormat("HH-mm").format(new Date());
           String savePath = StreamMediaConfig.mp4SavePath + date + "\\" + time + "\\";
           String videoPath = savePath + orderNo + ".mp4";

           //判断是否是新的订单
           Deviclist isNewOrderNo = null;
           for(Deviclist item : devices){
               if(item.getOrderno() == null){
                   isNewOrderNo = item;
                   break;
               }
               boolean equals = orderNo.equals(item.getOrderno());
               if (equals && item.getOrderno() != null) {//判断是否是新订单
                   isNewOrderNo = item;
                   break;
               }
           }

           //如果是新的订单则新增否则修改订单
           boolean DeviclistFlag = false;
           if(isNewOrderNo != null){//新的订单就添加
               if(isNewOrderNo.getVideopath() == null || isNewOrderNo.getVideopath().equals("")){
                   isNewOrderNo.setVideopath(videoPath);
               }
               isNewOrderNo.setOrderno(orderNo);
               DeviclistFlag = deviclistImpl.updateDeviclist(isNewOrderNo);
           }else {
               isNewOrderNo = new Deviclist();
               isNewOrderNo.setOrderno(orderNo);
               isNewOrderNo.setVideopath(videoPath);
               isNewOrderNo.setLuserid(devices.get(0).getLuserid());
               isNewOrderNo.setDevicid(devices.get(0).getDevicid());
               String addtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
               isNewOrderNo.setAddtime(addtime);
               DeviclistFlag = deviclistImpl.insertDeviclist(isNewOrderNo);
           }

           if (!DeviclistFlag) {
               log.error("修改数据订单号失败");
               TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
               return Result.formatToPojo(400,"修改数据失败");
           }

           //创建MP4文件保存路径
           File path = new File(savePath);
           if (!path.exists()) {
               path.mkdirs();
           }

           //海康SDK保存MP4文件
           Map<String,Object> startPreview = haikSDK.StartPreview(isNewOrderNo.getLuserid());
           if (!(boolean)startPreview.get("bool")) {
               log.error("海康SDK保存MP4时异常");
               TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
               return Result.formatToPojo(400,"保存数据异常");
           }

           //更新设备状态
           int sessionID = (int)startPreview.get("sessionID");
           Deviclist deviclist = deviclistImpl.queryDeviclistOrderNo(isNewOrderNo.getOrderno());
           deviclist.setSessionid(sessionID);
           DeviclistFlag = deviclistImpl.updateDeviclist(deviclist);
           if (!DeviclistFlag) {
               log.error("修改数据订单号失败");
               TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
               return Result.formatToPojo(400,"修改数据失败");
           }
           deviclist.setIsrunmp4(1);
           deviclistImpl.updateDeviclist(deviclist);
           return Result.formatToPojo(200,true);
       }catch(Exception ex){
           log.error("保存MP4异常:" + ex.getMessage());
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
           return Result.formatToPojo(400,"系统出错了");
       }
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
            return Result.formatToPojo(400,"订单不能为空");
        }
        if (StringUtils.isEmpty(deviceId)) {
            return Result.formatToPojo(400,"设备不能为空");
        }
        Deviclist deviclist = deviclistImpl.queryDeviclistOrderNo(orderNo);
        if (deviclist == null || deviclist.getDevicid() == null) {
            return Result.formatToPojo(400,"找不到设备");
        }
        boolean StopPreview = haikSDK.StopPreview(deviclist.getLuserid(),deviclist.getSessionid());
        if (!StopPreview) {
            return Result.formatToPojo(400,"停止失败");
        }
        deviclist.setIsrunmp4(0);
        deviclistImpl.updateDeviclist(deviclist);
        return Result.formatToPojo(200,true);
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
                return Result.formatToPojo(400,"订单不能为空");
            }
            if (StringUtils.isEmpty(deviceId)) {
                return Result.formatToPojo(400,"设备不能为空");
            }
            Deviclist deviclist = deviclistImpl.queryDeviclistOrderNo(orderNo);
            //查询设备是否注册
            if(deviclist == null){
                return Result.formatToPojo(400,"设备不存在");
            }
            //判断码流是不是存在
            if(StringUtils.isNotEmpty(deviclist.getStreampath())){
                return Result.formatToPojo(200,deviclist.getStreampath());
            }
            //创建路径调用ffmpeg
            String format = new SimpleDateFormat("MM-dd-hh-mm").format(new Date());
            String m3u8Path = format + "\\" + orderNo;
            Map<String,Object> m3u8 = new FFMpegUtli().getM3u8(deviclist.getVideopath(), m3u8Path);
            if (!(Boolean) m3u8.get("bool")) {
                return Result.formatToPojo(400,"转流失败");
            }
            //码流
            String stream = StreamMediaConfig.m3u8Url + m3u8Path.replace("\\", "/") + ".m3u8";
            //添加推流地址
            deviclist.setStreampath(stream);
            long pid = (long)m3u8.get("pid");
            deviclist.setPid((int) pid);
            deviclist.setIsrunstream(1);
            boolean b = deviclistImpl.updateDeviclist(deviclist);
            if (!b)
                log.error("添加推流地址失败");
            return Result.formatToPojo(200,true);
        } catch (Exception ex) {
            log.error("转流失败:" + ex.getMessage());
            return Result.formatToPojo(400,"转流失败");
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
            boolean ffmpegFlag = new FFMpegUtli().stopStream(deviclist);
            if(ffmpegFlag){
                deviclist.setIsrunstream(0);
                deviclistImpl.updateDeviclist(deviclist);
                return Result.formatToPojo(200,true);
            }else {
                return Result.formatToPojo(400,"停止推流失败");
            }
        }catch(Exception ex){
            log.error("停止推流出错" + ex.getMessage());
            return Result.formatToPojo(400,"停止推流出错");
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
            return Result.formatToPojo(400,"订单不能为空");
        }
        if (StringUtils.isEmpty(deviceId)) {
            return Result.formatToPojo(400,"设备不能为空");
        }
        //判断码流是不是存在
        Deviclist deviclist = deviclistImpl.queryDeviclistOrderNo(orderNo);
        if (deviclist.getStreampath() == null) {
            return Result.formatToPojo(400,"没有捕获到任何媒体流");
        } else {
            return Result.formatToPojo(200,deviclist.getStreampath());
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
