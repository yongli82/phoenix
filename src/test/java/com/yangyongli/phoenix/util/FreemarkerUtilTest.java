package com.yangyongli.phoenix.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by yangyongli on 9/29/16.
 */
public class FreemarkerUtilTest {
    @Test
    public void convert() throws Exception {
        String templateContent = "Hello ${name}. 欢迎${名字}";
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Joe Down");
        data.put("名字", "川普");
        String result = FreemarkerUtil.convert("test", templateContent, data);
        System.out.println(result);
        assertEquals("Hello Joe Down. 欢迎川普", result);
    }

    @Test
    public void render() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Joe Down");
        data.put("名字", "川普");
        String result = FreemarkerUtil.render("test.ftl", data);
        System.out.println(result);
        assertEquals("Hello Joe Down. 欢迎川普", result.trim());
    }
}
