package com.finest.app.controller.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CountUtils {
    public static Map<String,AtomicInteger> map = null;

    static
    {
        map = new HashMap<String,AtomicInteger>();
    }

    public static void add(String key)
    {
        if(map.containsKey(key))
        {
            map.get(key).incrementAndGet();
        }else
        {
            map.put(key,new AtomicInteger(1));
        }
    }
}