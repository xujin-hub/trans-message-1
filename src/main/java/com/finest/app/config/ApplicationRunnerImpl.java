package com.finest.app.config;

import com.finest.app.controller.Utils.CountUtils;
import com.finest.app.controller.Utils.PropertiesUtil;
import com.finest.app.task.Utils.FSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    ParamsConfig paramsConfig;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        PropertiesUtil p = new PropertiesUtil(paramsConfig.getCount().getPath()+paramsConfig.getCount().getName());
        FSUtil.checkFileExists(paramsConfig.getCount().getPath(),paramsConfig.getCount().getName());
        Properties properties = p.getProperties();
        for (String key : properties.stringPropertyNames()) {
            CountUtils.map.put(key, new AtomicInteger(Integer.parseInt(properties.getProperty(key))));
        }
    }
}