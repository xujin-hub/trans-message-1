package com.finest.app.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Count {
    private boolean enable;
    private String fileName;
    private String filePath;
    private String type;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath()
    {
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        return this.getFilePath() + month.format(new Date())
                + "//";
    }

    public String getName()
    {
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        return this.getFileName() + "_" + day.format(new Date())+"."+
                this.getType();
    }

    public String getLastPath()
    {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        String yesterdayDate=dateFormat.format(calendar.getTime());
        return this.getFilePath() + yesterdayDate
                + "//";
    }

    public String getLastName()
    {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        String yesterdayDate=dateFormat.format(calendar.getTime());
        return this.getFileName() + "_" + yesterdayDate+"."+
                this.getType();
    }
}