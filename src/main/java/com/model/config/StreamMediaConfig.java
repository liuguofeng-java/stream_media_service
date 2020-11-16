package com.model.config;

import com.model.dao.impl.DeviclistImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:application.properties")
public class StreamMediaConfig {

    public static String nginxPath;

    public static String m3u8Url;

    public static String serviceIp;

    public static String mp4SavePath;

    public static String pathDll;

    public static String intranetIp;

    @Autowired
    private DeviclistImpl deviclistImpl;

    /**
     * 程序启动时清空表
     */
    @Bean
    public void deleteTable(){
        deviclistImpl.deleteTable();
    }

    @Value("${nginx.path}")
    public void setNginxPath(String nginxPath) {
        StreamMediaConfig.nginxPath = nginxPath;
    }

    @Value("${m3u8.url}")
    public void setM3u8Url(String m3u8Url) {
        StreamMediaConfig.m3u8Url = m3u8Url;
    }

    @Value("${service.ip}")
    public void setServiceIp(String serviceIp) {
        StreamMediaConfig.serviceIp = serviceIp;
    }

    @Value("${savePath.mp4}")
    public void setMp4SavePath(String mp4SavePath) {
        StreamMediaConfig.mp4SavePath = mp4SavePath;
    }

    @Value("${path.dll}")
    public void setPathDll(String pathDll) {
        StreamMediaConfig.pathDll = pathDll;
    }

    @Value("${intranet.ip}")
    public void setIntranetIp(String intranetIp) {
        StreamMediaConfig.intranetIp = intranetIp;
    }
}
