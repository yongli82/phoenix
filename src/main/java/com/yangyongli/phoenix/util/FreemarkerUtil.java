package com.yangyongli.phoenix.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * Created by yangyongli on 9/29/16.
 */
public class FreemarkerUtil {

    private static Configuration cfg = new Configuration();
    static {
        cfg.setDefaultEncoding("UTF-8");
        //设置对象的包装器
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        //设置异常处理器
        //这样的话就可以${a.b.c.d}即使没有属性也不会出错
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        //定义模板的位置，从类路径相对FreeMarkerManager所在的模板加载路径
        //从JUnit中运行测试用例，class获得resource的路径是从target/test-classes/开始的，找不到main中的资源
        //cfg.setTemplateLoader(new ClassTemplateLoader(FreemarkerUtil.class, "/template"));
        try {
            cfg.setTemplateLoader(new FileTemplateLoader(new File("src/main/java/com/yangyongli/phoenix/util/template")));
        } catch (IOException e) {

        }
    }

    /**
     * 通过
     * @param templateContent
     * @param data
     * @return
     */
    public static String convert(String name, String templateContent, Map<String, Object> data) throws Exception {
        Template template = new Template(name, new StringReader(templateContent), cfg);
        StringWriter stringWriter = new StringWriter();
        template.process(data, stringWriter);
        String result = stringWriter.toString();
        return result;
    }

    /**
     * 通过
     * @param templateName
     * @param data
     * @return
     */
    public static String render(String templateName, Map<String, Object> data) throws Exception {
        Template template = cfg.getTemplate(templateName);
        StringWriter stringWriter = new StringWriter();
        template.process(data, stringWriter);
        String result = stringWriter.toString();
        return result;
    }

}
