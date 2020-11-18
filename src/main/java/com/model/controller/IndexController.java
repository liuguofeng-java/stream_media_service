package com.model.controller;

import com.model.dao.impl.DeviclistImpl;
import com.model.pojo.Deviclist;
import com.model.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private DeviclistImpl deviclistImpl;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("/GetDeviceInfo")
    public Result getDeviceInfo(){
        List<Deviclist> deviclists = deviclistImpl.queryDeviclistAll();
        return Result.formatToPojo(200,deviclists);
    }
}
