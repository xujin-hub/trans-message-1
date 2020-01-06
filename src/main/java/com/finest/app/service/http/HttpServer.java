package com.finest.app.service.http;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface HttpServer {
    String sendPostDataByJson(Object object) throws IOException;

    Object send(HttpServletRequest request) throws IOException;
}