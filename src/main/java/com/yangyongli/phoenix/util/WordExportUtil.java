package com.yangyongli.phoenix.util;

import org.apache.commons.io.IOUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


/**
 * Created by yangyongli on 9/25/16.
 * http://www.javadocexamples.com/java_source/org/apache/poi/hpsf/examples/
 */
public class WordExportUtil {

    public static void downloadWord(String fileName, String content, HttpServletResponse response) throws Exception {
        ByteArrayInputStream in = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            response.reset();
            response.setContentType("application/vnd.ms-word;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".doc").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();

            byte b[] = content.getBytes("UTF-8");
            in = new ByteArrayInputStream(b);
            POIFSFileSystem poiFs = new POIFSFileSystem();
            DirectoryEntry directory = poiFs.getRoot();
            directory.createDocument("WordDocument", in);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            poiFs.writeFilesystem(os);

            //下载缓冲
            byte[] bytes = os.toByteArray();
            InputStream is = new ByteArrayInputStream(bytes);
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(bos);
        }
    }


    public static void main(String[] args) throws Exception {
        //File file = new File("web.pc.html/标准合同.docx");
        //System.out.println(file.getAbsolutePath());
        convertHtmlToDocDemo();
        //convertDocxFromTemplate();
    }

    private static void convertDocxFromTemplate() throws IOException {
        String file = "web.pc.html/模板.docx";
        InputStream is = new FileInputStream(file);
        XWPFDocument doc = new XWPFDocument(is);
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            System.out.println("[paragraph] " + paragraph.getParagraphText());
            List<XWPFRun> runs = paragraph.getRuns();
            int runPos = 0;
            for (XWPFRun run : runs) {
                String runText = run.toString();
                System.out.println("[runText] " + runText);
                if (null != runText) {
                    String newText = runText.replace("{{hello}}", "你好");
                    if (!newText.equals(runText)) {
                        paragraph.removeRun(runPos);
                        paragraph.insertNewRun(runPos).setText(newText);
                    }
                }
                runPos++;
            }
        }

        String savefile = "web.pc.html/模板替换.docx";
        OutputStream out = new FileOutputStream(savefile);
        doc.write(out);
    }


    private static void convertHtmlToDocDemo() throws FileNotFoundException {
        InputStream in = new FileInputStream("export/标准合同.html");
        OutputStream out = new FileOutputStream("export/html生成.docx");

        //创建 POIFSFileSystem 对象
        POIFSFileSystem poiFs = new POIFSFileSystem();
        //获取DirectoryEntry
        DirectoryEntry directory = poiFs.getRoot();


        try {
            //创建文档,1.格式,2.HTML文件输入流
            directory.createDocument("WordDocument", in);
            //写入
            poiFs.writeFilesystem(out);
            //释放资源

            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }


    private static void convertHtmlToDocDemo2() throws Exception {
        expWordByPoi("hello world", "web.pc.html/标准合同2.doc");
    }


    private static void expWordByPoi(String content, String filePath) throws IOException {
        byte[] buf = content.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        POIFSFileSystem poifs = new POIFSFileSystem();
        DirectoryEntry directory = poifs.getRoot();
        DocumentEntry document = directory.createDocument("WordDocument", bais);
        FileOutputStream fos = new FileOutputStream(filePath);
        poifs.writeFilesystem(fos);
        File file = new File(filePath);
        // 释放资源
        bais.close();
        fos.close();
    }

}
