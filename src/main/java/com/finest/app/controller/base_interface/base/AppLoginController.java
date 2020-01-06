package com.finest.app.controller.base_interface.base;

import com.finest.app.service.http.impl.HttpServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/base-interface")
public class AppLoginController {

    @Autowired
    private HttpServerImpl httpServerImpl;

    @RequestMapping("*.do")
    public Object login(HttpServletRequest request) throws IOException {
        return httpServerImpl.send(request);
    }
}