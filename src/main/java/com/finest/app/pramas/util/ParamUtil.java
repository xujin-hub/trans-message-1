package com.finest.app.pramas.util;

import com.alibaba.fastjson.JSONObject;
import com.finest.app.controller.Utils.FileUtils;
import com.finest.app.controller.Utils.MultipartFileToFile;
import com.finest.app.httpclinet.util.JsonFormat;
import com.finest.app.pramas.FileParam;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

public class ParamUtil {

    public static String setParams(HttpServletRequest request,String path,boolean isUpload) {
        String tempKey = "";
        Long startTime = System.currentTimeMillis();
        String temp = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("servletPath", request.getServletPath());
        jsonObject.put("startTime", startTime+"");
        jsonObject.put("type",request.getAttribute("type"));
        jsonObject.put("methodName", request.getParameter("method"));
        Map<String,Object> map = new HashMap<String,Object>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length >0) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        if(isUpload)
        {
            String fileKey = FileUtils.getFileKey(request);
            tempKey = fileKey;
            if(!StringUtils.isEmpty(fileKey))
            {
                String base64 = "";
                jsonObject.put("fileKey", fileKey);
                List<MultipartFile> files = FileUtils.getFiles(request);
                List<String> filenames = new ArrayList<String>();
                try {
                    List<FileParam> ls = new ArrayList<FileParam>();
                    for (int i = 0; i < files.size(); i++) {
                        FileParam fileparam = new FileParam();
                        MultipartFile file = files.get(i);
                        String filename = file.getOriginalFilename();
                        filenames.add(filename);
                        base64 = FileUtils.fileToBase64(MultipartFileToFile.multipartFileToFile(file,path));
                        //base64 = FileUtils.multipartFileToBase64(file);
                        fileparam.setFilename(filename);
                        fileparam.setContent(base64);
                        ls.add(fileparam);
                    }
                    jsonObject.put("file", ls);
                    ///**/FileUtils.base64ToFile("E:\\xiaowei\\temp",base64,filename);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(filenames.size()>0)
                    {
                        for (int i = 0; i < filenames.size(); i++) {
                            File file =new File(path+"//"+filenames.get(i));
                            file.delete();
                        }
                    }
                }

            }
        }
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry entry : set) {
            String tp = (entry.getValue()+"").replaceAll("\"", "'");
            jsonObject.put(entry.getKey() + "", tp);
//            System.out.println(entry.getKey() + "---------------" + tp);
        }

        if(!StringUtils.isEmpty(tempKey))
        {
            jsonObject.remove(tempKey);
        }
        temp = jsonObject.toJSONString();
/*        if(temp.indexOf("\"[") >= 0 && temp.indexOf("]\"") >= 0)
        {
            temp = temp.replaceAll("\\\\" + "\"", "'");
        }else
        {
            temp = JsonFormat.transStr(temp);

            temp = temp.replaceAll("\\\\" + "\"", "\"");
            if(temp.startsWith("\"") && temp.endsWith("\""))
            {
                temp = temp.substring(1,temp.length()-1);
            }
            temp = temp.replaceAll("\"\\{\"", "\\{\"");
            temp = temp.replaceAll("\\}\"","\\}");
        }*/

        return temp;
    }
}