package com.finest.app.controller.cd_interface.terminal;

import com.finest.app.controller.Utils.RemoteSourcesUtils;
import com.finest.app.service.http.impl.HttpServerImpl;
import com.finest.app.service.http.impl.RemoteFileServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping(value = "/cdupload")
public class FileController {

    @Autowired
    RemoteFileServerImpl remoteFileServerImpl;

    @Autowired
    private HttpServerImpl httpServerImpl;

    @RequestMapping("{fileType}/{data}/*.*")
    public void getAttachment(HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setAttribute("type", "image");
        String request_uri = request.getServletPath();
        String[] temps = request_uri.split("/");
        String fileName = temps[temps.length-1];
        String base64 = httpServerImpl.send_(request);
        InputStream trans_is = RemoteSourcesUtils.base64ToInputStream(base64);
        doFile(response, fileName, trans_is);
    }

    public void doFile(HttpServletResponse response,String fileName,InputStream is) throws IOException {
        String fileSuff = fileName.substring(fileName.lastIndexOf(".")+1);
        response.addHeader("pragma", "NO-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addDateHeader("Expries", 0);
        try {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859_1");
            if(remoteFileServerImpl.isImage(fileSuff))
            {
                response.setContentType("image/jpeg");
            }else
            {
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        OutputStream out = null;
        try{
            out = response.getOutputStream();
            int length = 0;
            byte buffer[] = new byte[1024];
            while((length = is.read(buffer)) != -1){
                out.write(buffer, 0, length);
            }
        } catch (Exception e) {

        } finally{
            out.close();
            is.close();
        }
    }
}