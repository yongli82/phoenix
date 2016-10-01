package com.yangyongli.phoenix.util;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by yangyongli on 9/24/16.
 */
public class ExcelExportUtil {

    public static Workbook createWorkbook(String sheetName, List<String> headers, List<List<String>> dataList) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        for(int column = 0; column < headers.size(); column++){
            //自动调整列宽
            sheet.setColumnWidth(column, 4800);
            sheet.autoSizeColumn(column);
        }

        //create headers
        CellStyle headerStyle = createCellStyle(workbook, HSSFColor.LIGHT_CORNFLOWER_BLUE.index, (short) 12);
        int headRowNum = 0;
        createRow(sheet, headRowNum, headers, headerStyle);

        //create data grid
        CellStyle bodyStyle = createCellStyle(workbook, HSSFColor.WHITE.index, (short) 12);

        for(int i = 0; i < dataList.size(); i++){
            List<String> rowData = dataList.get(i);
            //header 占据一行
            int rowNum = i + 1;
            createRow(sheet, rowNum, rowData, bodyStyle);
        }

        return workbook;
    }

    private static void createRow(Sheet sheet, int rowNum, List<String> rowData, CellStyle bodyStyle) {
        Row row = sheet.createRow(rowNum);
        for(int columnNum = 0; columnNum < rowData.size(); columnNum++){
            Cell cell = row.createCell(columnNum);
            cell.setCellValue(rowData.get(columnNum));
            cell.setCellStyle(bodyStyle);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        }
    }

    public static void download(String fileName, Workbook workbook, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
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
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(bos);
        }
    }

    private static CellStyle createCellStyle(Workbook wb, short color, short fontPoint) {

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(color);

        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);

        cellStyle.setBorderBottom((short) 1); // 设置下划线，参数是黑线的宽度
        cellStyle.setBorderLeft((short) 1); // 设置左边框
        cellStyle.setBorderRight((short) 1); // 设置有边框
        cellStyle.setBorderTop((short) 1); // 设置下边框

        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
        // 设置标题字体格式
        Font font = wb.createFont();
        // 设置字体样式
        font.setFontHeightInPoints(fontPoint); // --->设置字体大小
        font.setFontName("宋体"); //---》设置字体，是什么类型例如：宋体
        cellStyle.setFont(font); // --->将字体格式加入到style1中
        return cellStyle;
    }
}
