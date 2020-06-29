package com.fung.server.gameserver.excel2class;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author skytrc@163.com
 * @date 2020/5/22 16:46
 */
@Component
public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * Excel转Json
     * @param excelPath excel文件地址
     * @return JsonObject
     * @throws IOException
     * @throws InvalidFormatException
     */
    public JsonArray excelTransformJson(String excelPath) throws IOException, InvalidFormatException {
        // 读取excel表格
        Workbook workbook = WorkbookFactory.create(new File(excelPath));
        DataFormatter formatter = new DataFormatter();
        Sheet sheet = workbook.getSheetAt(0);
        String fileName = getExcelName(excelPath);

        // 获取第一行，遍历，作为tag名
        XSSFRow row1 = (XSSFRow) sheet.getRow(0);
        String[] tagName = new String[row1.getLastCellNum()];
        for (int i = 0; i < row1.getLastCellNum(); i++) {
            tagName[i] = formatter.formatCellValue(row1.getCell(i));
        }

        // 构建Json基本元素
        JsonArray jsonArray = new JsonArray();

        // 构建json
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            JsonObject jsonElement = new JsonObject();
            int cellNum = sheet.getRow(i).getLastCellNum();
            Row row2 = sheet.getRow(i);
            for (int j = 0; j < cellNum; j++) {
                String value = formatter.formatCellValue(row2.getCell(j));
                jsonElement.addProperty(tagName[j], value);
            }
            jsonArray.add(jsonElement);
        }

        return jsonArray;
    }

    public String getExcelName(String excelPath) {
        String fileName = new File(excelPath).getName();
        String[] split = fileName.split("\\.");
        return split[0];
    }
}
