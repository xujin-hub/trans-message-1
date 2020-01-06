package com.finest.app.httpclinet.util;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpClientUtil {

    private Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public ThreadLocal<Long> threadLocal = null;

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig requestConfig;

    public ThreadLocal getThreadLocal()
    {
        if(null == this.threadLocal)
        {
            this.threadLocal = new ThreadLocal<Long>();
            return this.threadLocal;
        }
        return this.threadLocal;
    }

    public String getString(String url,String json,String encoding) throws IOException {
        logger.info("TRASN-MESSAGE 传送报文至二类网:{}",json);
        String result = "";
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        // 设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding(encoding);
        httpPost.setEntity(stringEntity);

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = null;
        try {
            Long startTime = System.currentTimeMillis();
            logger.info("TRASN-MESSAGE 传送报文至二类网准备参数耗时: {}ms",startTime-this.threadLocal.get());
            response = httpClient.execute(httpPost);
            logger.info("二类网->三类网->内网->三类网->二类网->TRASN-MESSAGE共耗时:{}ms",System.currentTimeMillis()-startTime);
            // 获取结果实体
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), encoding);
            }
        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            // 释放链接
            this.threadLocal.remove();
            if(null!=response)
                response.close();
        }

        return result;
    }
/*
    //文件上传请求
    public HttpClientVo uploadFile(MultipartFile file, String url) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);

            // 创建待处理的文件
            String fileName = file.getOriginalFilename();
            ContentBody files = new ByteArrayBody(file.getBytes(), fileName);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("file", files);
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null && response.getStatusLine().getStatusCode() == 200) {
                // 将响应内容转换为字符串
                String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-    8"));
            }
            httpPost.abort();
        } catch (Exception e) {
            httpPost.abort();
            logger.error("error");
        }
    }*/
}