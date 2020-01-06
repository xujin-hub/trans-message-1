package com.finest.app.task.Utils;

import java.io.File;
import java.io.IOException;

public class FSUtil {

    /**
     * 判断文件是否存在
     * @param path
     */
    public static boolean checkFileExists(String path,String fileName) {
        boolean isMkfile = false;
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs(); //创建目录
        }
        File file = new File(path,fileName);
        if(!file.exists()){ //surround with try/catch
            try {
                file.createNewFile();
                isMkfile = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return isMkfile;

    }
}