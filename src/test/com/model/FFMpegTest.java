package com.model;


import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FFMpegTest {

    @Test
    public void testVideo() throws IOException {

        String ffmpegPath = "ffmpeg.exe"; // ffmpeg.exe的目录
        String upFilePath = "C:\\Users\\13961\\Desktop\\47276514.mp4";
        String codcFilePath =   "C:\\Users\\13961\\Desktop\\output.mp4"; // 截图的视频目录;

        try {
            FFMpegTest exchangeToMp4 = new FFMpegTest();
            exchangeToMp4.exchangeToMp4(ffmpegPath, upFilePath, codcFilePath);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 视频转码 (PC端MP4)
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath    格式转换后的的文件保存路径
     * @return
     * @throws Exception
     */
    public boolean exchangeToMp4(String ffmpegPath, String upFilePath, String codcFilePath) throws Exception {
        // 创建List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add(ffmpegPath); // 添加转换工具路径
        convert.add("-y"); // 该参数指定将覆盖已存在的文件
        convert.add("-i");
        convert.add(upFilePath);
        convert.add("-c:v");
        convert.add("libx264");
        convert.add("-c:a");
        convert.add("aac");
        convert.add("-strict");
        convert.add("-2");
        convert.add("-pix_fmt");
        convert.add("yuv420p");
        convert.add("-movflags");
        convert.add("faststart");
        //convert.add("-vf");   // 添加水印
        //convert.add("movie=watermark.gif[wm];[in][wm]overlay=20:20[out]");
        convert.add(codcFilePath);

        boolean mark = true;

        try {
            Process videoProcess = new ProcessBuilder(convert).redirectErrorStream(true).start();
            InputStream in = videoProcess.getInputStream();
            OutputStream out = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = in.read(bytes)) != -1) {
                out.write(bytes, 0, length);
            }
            System.out.println(out.toString());
            //videoProcess.waitFor();  // 加上这句，系统会等待转换完成。不加，就会在服务器后台自行转换。

        } catch (Exception e) {
            mark = false;
            e.printStackTrace();
        }
        return mark;
    }
}