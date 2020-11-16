package com.model.ffmepg.task.bean;

import com.model.dao.impl.DeviclistImpl;
import com.model.ffmepg.operation.FFOperationBase;
import com.model.ffmepg.task.context.FFTaskContext;
import com.model.ffmepg.util.FFTerminalCreater;
import com.model.ffmepg.util.UUIDUtil;
import com.model.pojo.Deviclist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 任务父类
 * com.ugirls.ffmepg.task
 * 2018/6/11.
 *
 * @author zhanghaishan
 * @version V1.0
 */
@Component
public abstract class FFTask<T extends FFOperationBase> implements Runnable {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 执行结果的全部内容
     */
    protected StringBuffer result = new StringBuffer("");

    /**
     * 内容操作
     */
    protected T operationBase;

    /**
     * 任务进度
     */
    private FFTaskProgress progress;

    /**
     * 任务命令行
     */
    private FFTerminalCreater.FFTerminal terminal = null;

    @Override
    public void run() {
        //任务开始
        progress.setState(FFTaskStateEnum.START);

        //执行的命令
        String cmd = operationBase.toString();

        //任务执行状态
        boolean state = true;

        long pid = -1;
        try {
            terminal = FFTerminalCreater.getCreater().getTerminal(cmd);
            //结果
            String str = null;
            if(cmd.contains(".m3u8")){//获取pid
                pid = terminal.killProcessTree();
            }
            while ((str = terminal.readErrorLine()) != null){
                result.append(str);
                callRsultLine(str);
                getPid(pid);
            }
        } catch (IOException e) {
            state = false;
        }

        //设置状态
        if(state){
            progress.setState(FFTaskStateEnum.COMPLETE);
        } else {
            progress.setState(FFTaskStateEnum.FAILED);
        }
        progress.setProgress(100);
        removeStream(pid);
        FFTaskContext.getContext().removeTask(this.getTaskId());
        //执行结束回调
        callExecEnd();
    }


    /**
     * 正确结果行
     * @param line 一行结果
     */
    public abstract void callRsultLine(String line);

    /**
     * 获取pid
     * @param pid 线程pid
     */
    public abstract void getPid(long pid);

    /**
     * 执行结束
     */
    public abstract void callExecEnd();

    /**
     * 删除流
     */
    public abstract void removeStream(long pid);


    public FFTask(){
    }
    /**
     * 任务构造
     * @param operation 操作
     */
    public FFTask(T operation){
        this.operationBase = operation;
        this.taskId = UUIDUtil.getUUIDSimpl();
        this.progress = new FFTaskProgress();
    }

    public String getTaskId() {
        return taskId;
    }

    public FFTaskProgress getProgress() {
        return progress;
    }

    public void setProgress(FFTaskProgress progress) {
        this.progress = progress;
    }


}
