package com.finest.app.task;

import com.finest.app.config.ParamsConfig;
import com.finest.app.controller.Utils.CountUtils;
import com.finest.app.controller.Utils.PropertiesUtil;
import com.finest.app.task.Utils.DB;
import com.finest.app.task.Utils.FSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CountTask {

    @Autowired
    ParamsConfig paramsConfig;

    @Scheduled(cron = "0/30 * * * * *")
    public void run() throws InterruptedException {

        if(paramsConfig.getCount().isEnable())
        {
            PropertiesUtil p = null;
            boolean isMkdir = FSUtil.checkFileExists(paramsConfig.getCount().getPath(),paramsConfig.getCount().getName());
            if(isMkdir)
            {
                p = new PropertiesUtil(paramsConfig.getCount().getLastPath()+paramsConfig.getCount().getLastName());
            }else
            {
                p = new PropertiesUtil(paramsConfig.getCount().getPath()+paramsConfig.getCount().getName());
            }

            for (Map.Entry<String, AtomicInteger> entry : CountUtils.map.entrySet()) {
                if(!(p.readProperty("entry.getKey()")+"").equals(entry.getValue().get()+""))
                {
                    p.writeProperty(entry.getKey(),entry.getValue().get()+"");
                }
            }

            if(isMkdir)
            {
                CountUtils.map.clear();
            }
        }
    }

    //@Scheduled(cron = "0 */5 * * * ?")
    public void createData()
    {
        try {
            System.out.println("==================");
            DB.mockData();
        } catch (SQLException e) {

        }
    }

}