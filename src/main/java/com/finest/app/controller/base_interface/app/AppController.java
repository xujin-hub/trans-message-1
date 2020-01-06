package com.finest.app.controller.base_interface.app;

import com.finest.app.service.http.impl.HttpServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/base-interface/app")
public class AppController {

    @Autowired
    private HttpServerImpl httpServerImpl;

    @RequestMapping("*.do")
    public Object login(HttpServletRequest request) throws IOException {
        return httpServerImpl.send(request);
    }
}