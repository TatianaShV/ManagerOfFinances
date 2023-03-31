package org.example;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class Products {
    Map<String, String> title = new HashMap<>();

    {
        title.put("булка", "еда");
        title.put("колбаса", "еда");
        title.put("сухарики", "еда");
        title.put("курица", "еда");
        title.put("тапки", "одежда");
        title.put("шапка", "одежда");
        title.put("мыло", "быт");
        title.put("акции", "финансы");
    }
    public void localTsv(File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)){
            for(Map.Entry<String,String>kv : title.entrySet())
            out.println(kv.getKey() + '\t' + kv.getValue());
        }
    }
}
