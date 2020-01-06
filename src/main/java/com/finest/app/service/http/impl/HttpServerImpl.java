package com.finest.app.service.http.impl;

import com.alibaba.fastjson.JSONObject;
import com.finest.app.config.ParamsConfig;
import com.finest.app.httpclinet.HttpClient;
import com.finest.app.httpclinet.util.HttpClientUtil;
import com.finest.app.pramas.Descript;
import com.finest.app.pramas.Param;
import com.finest.app.pramas.rsp.ResponseParam;
import com.finest.app.pramas.special.person.DeviceQueryResult;
import com.finest.app.pramas.util.ParamUtil;
import com.finest.app.service.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HttpServerImpl implements HttpServer {

    private Logger logger = LoggerFactory.getLogger(HttpServerImpl.class);

    @Autowired
    HttpClient httpClient;

    @Autowired
    ParamsConfig paramsConfig;

    @Autowired
    HttpClientUtil httpClientUtil;

    @Override
    public String sendPostDataByJson(Object object) throws IOException {

        return httpClient.testSendPostDataByJson(object);
    }

    @Override
    public Object send(HttpServletRequest request) throws IOException
    {
        Long startTime = System.currentTimeMillis();
        httpClientUtil.getThreadLocal().set(startTime);
        Map<String, String> specialMap = paramsConfig.getSpecialMap();
        String method = request.getParameter("method");
        if(!StringUtils.isEmpty(method) && specialMap.containsKey(method))
        {
            String res = send_(request);
            JSONObject dataObj = JSONObject.parseObject(res);
            DeviceQueryResult obj = null;
            try {
                Class clazz = Class.forName(specialMap.get(method));
                logger.info("总耗时：{}ms",System.currentTimeMillis()-startTime);
                return JSONObject.toJavaObject(dataObj,clazz);
            } catch (ClassNotFoundException e) {
                logger.info("缺少类["+specialMap.get(method)+"]",e);
            }
            return obj;
        }
        String res = send_(request);
        logger.info("总耗时：{}ms",System.currentTimeMillis()-startTime);
        return res;
    }

    public String send_(HttpServletRequest request) throws IOException {
        //step1: 转换二类网请求参数
        Param param = transRequestParam(request);
        //step2: 发送请求至二类网
        String res = sendPostDataByJson(param);
        //step3: 解析二类网响应结果
        String result = transResponseParam(request, res);
        //step4: 将结果返回移动终端
        return result;
    }

    private String transResponseParam(HttpServletRequest request, String res) {
        logger.info("二类网返回报文 -> TRANS-MESSAGE:{}",res);
        JSONObject dataObj = JSONObject.parseObject(res);
        ResponseParam rsp = JSONObject.toJavaObject(dataObj, ResponseParam.class);
        String result = "";
        if(rsp!=null && rsp.getServiceDescript()!=null && rsp.getServiceDescript().getData() != null)
        {
            String type = request.getAttribute("type")+"";
            result = (String)rsp.getServiceDescript().getData();
            if(!"image".equals(type))
            {
                String startTime = "";
                JSONObject obj = JSONObject.parseObject(result);
                startTime = obj.getString("startTime");
                Long endTime = System.currentTimeMillis();
                logger.info("三类网 -> 二类网 -> TRANS-MESSAGE共耗时:" + (endTime - Long.parseLong(startTime)) + " ms");
            }
        }
        logger.info("TRANS-MESSAGE -> 移动终端报文:{}",result);
        logger.info("===================终端请求结束===================");
        logger.info("");
        return result;
    }

    private Param transRequestParam(HttpServletRequest request) {
        String methodName = request.getParameter("method");
        logger.info("");
        logger.info("===================终端请求开始===================");
        List<String> methods = paramsConfig.getUploadList();
        String requestParam = ParamUtil.setParams(request, paramsConfig.getUploadPath(), methods.contains(methodName));
        logger.info("终端请求路径:{}",request.getServletPath());
        logger.info("移动终端 -> TRANS-MESSAGE报文:{}",requestParam);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Client-App-ID","HSAPP");
        header.put("Client-App-Secret","ASDS123ADSA44");
        Param param = new Param(3,"C10-00000005","S10-00000013",methodName);
        Descript descript = new Descript();
        descript.setData(requestParam);
        descript.setHeader(header);
        descript.setServiceName(request.getServletPath());
        descript.setServiceMethod("POST");
        param.setServiceDescript(descript);
        return param;
    }
}