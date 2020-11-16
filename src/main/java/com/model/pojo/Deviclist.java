package com.model.pojo;

public class Deviclist {
    private Integer id;

    private String devicid;

    private Integer luserid;

    private String orderno;

    private String videopath;

    private String streampath;

    private String addtime;

    private Integer pid;

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

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}