package com.finest.app.httpclinet.util;

public class JsonFormat {

    public static String transStr(String json)
    {
        json = json.replaceAll("\\\\"+"\"","\"");
        json = json.replaceAll("\"\\{\"","\\{\"");
        json = json.replaceAll("\"\\}\"","\"\\}");
        if(json.startsWith("\"") && json.endsWith("\""))
        {
            json = json.substring(1,json.length()-1);
        }

        return json;
    }

    public static String transJson(String json)
    {
        json = json.replaceAll("\\\\" + "\"", "\"");
        json = json.replaceAll("\"\\{\"","\\{\"");
        json = json.replaceAll("\""+"\\}\"","\""+"\\}");
        json = json.replaceAll("\"\\{"+"'","\\{"+"'");
        json = json.replaceAll("'"+"\\}"+"\"","'"+"\\}");
        json = json.replaceAll("'","\"");
        json = json.replaceAll("\""+"\\[","\\[");
        json = json.replaceAll("\\]"+"\"","\\]");
        json = json.replaceAll("\\}"+"\"","\\}");
        return json;
    }
}