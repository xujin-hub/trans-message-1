package com.finest.app.controller.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String getFileKey(HttpServletRequest request)
    {
        try
        {
            List<String> list = new ArrayList<String>();
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest)request;
            MultiValueMap<String, MultipartFile> map = req.getMultiFileMap();

            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                list.add(key);
            }

            return list.size()>0 ? list.get(0) : "";
        } catch (Exception e)
        {
            logger.info("没有带附件");
        }

        return "";


    }

    public static List<MultipartFile> getFiles(HttpServletRequest request)
    {
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest)request;
            MultiValueMap<String, MultipartFile> map = req.getMultiFileMap();
            List<MultipartFile> files = map.get(getFileKey(request));
            return files;
        } catch (Exception e)
        {
            logger.info("没有携带附件");
        }

        return new ArrayList<MultipartFile>();
    }

    public static List<byte[]> getFileBytes(HttpServletRequest request) throws IOException {
        List<byte[]> list = new ArrayList<byte[]>();
        List<MultipartFile> files = getFiles(request);
        for (int i = 0; i < files.size(); i++) {
            MultipartFile multipartFile = files.get(i);
            byte[] bytes = multipartFile.getBytes();
            list.add(bytes);
        }
        return list;
    }

    /**
     * 文件转base64字符串
     * @param file
     * @return
     */
    public static  String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes=new byte[(int)file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

    public static String multipartFileToBase64(MultipartFile file) throws Exception{
        BASE64Encoder base64Encoder =new BASE64Encoder();
        String base64EncoderImg = file.getOriginalFilename()+","+base64Encoder.encode(file.getBytes());
        return base64EncoderImg;
    }

    public static void base64ToFile(String destPath,String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file=new File(filePath+"/"+fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}