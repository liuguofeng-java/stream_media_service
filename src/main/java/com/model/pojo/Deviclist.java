package com.model.pojo;

public class Deviclist {
    private Integer id;

    private String devicid;

    private Integer luserid;

    private Integer sessionid;

    private Integer previewhandle;

    private String orderno;

    private String videopath;

    private String streampath;

    private Integer pid;

    private String addtime;

    private Integer isrunmp4;

    private Integer isrunstream;

    private Integer isdeviceonline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevicid() {
        return devicid;
    }

    public void setDevicid(String devicid) {
        this.devicid = devicid == null ? null : devicid.trim();
    }

    public Integer getLuserid() {
        return luserid;
    }

    public void setLuserid(Integer luserid) {
        this.luserid = luserid;
    }

    public Integer getSessionid() {
        return sessionid;
    }

    public void setSessionid(Integer sessionid) {
        this.sessionid = sessionid;
    }

    public Integer getPreviewhandle() {
        return previewhandle;
    }

    public void setPreviewhandle(Integer previewhandle) {
        this.previewhandle = previewhandle;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getVideopath() {
        return videopath;
    }

    public void setVideopath(String videopath) {
        this.videopath = videopath == null ? null : videopath.trim();
    }

    public String getStreampath() {
        return streampath;
    }

    public void setStreampath(String streampath) {
        this.streampath = streampath == null ? null : streampath.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public Integer getIsrunmp4() {
        return isrunmp4;
    }

    public void setIsrunmp4(Integer isrunmp4) {
        this.isrunmp4 = isrunmp4;
    }

    public Integer getIsrunstream() {
        return isrunstream;
    }

    public void setIsrunstream(Integer isrunstream) {
        this.isrunstream = isrunstream;
    }

    public Integer getIsdeviceonline() {
        return isdeviceonline;
    }

    public void setIsdeviceonline(Integer isdeviceonline) {
        this.isdeviceonline = isdeviceonline;
    }
}