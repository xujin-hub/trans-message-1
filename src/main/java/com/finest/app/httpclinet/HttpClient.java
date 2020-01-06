package com.finest.app.httpclinet;

import com.alibaba.fastjson.JSON;
import com.finest.app.config.ParamsConfig;
import com.finest.app.httpclinet.util.HttpClientUtil;
import com.finest.app.httpclinet.util.JsonFormat;
import com.finest.app.pramas.Param;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * http客户端
 *
 *
 * @author：XiaoWei
 * @date：2019年12月23日 下午14:27:08
 */
@Component
public class HttpClient {

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private ParamsConfig paramsConfig;

    /**
     * post请求传输json数据
     *
     * @param url
     * @param json
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String sendPostDataByJson(String url, String json, String encoding) throws IOException {
        json = JsonFormat.transJson(json);
        String result = "";
        result = httpClientUtil.getString(url,json,encoding);
        return result;
    }
    public String testSendPostDataByJson(Object object) throws ClientProtocolException, IOException {
        Param param = (Param)object;
        String body = sendPostDataByJson(paramsConfig.getOutSiteHost(), JSON.toJSONString(object), "utf-8");
        System.out.println("响应结果["+param.getServiceDescript().getServiceName()+"?method="+param.getServiceAlias()+"]：" + body);
        return body;
    }

}