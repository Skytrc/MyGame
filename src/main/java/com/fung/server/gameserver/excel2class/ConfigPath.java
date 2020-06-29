package com.fung.server.gameserver.excel2class;

/**
 * @author skytrc@163.com
 * @date 2020/5/26 18:01
 */
public interface ConfigPath {

    String ROOT = System.getProperty("user.dir");

    String EXCEL_RESOURCE_ROOT = ROOT + "\\src\\main\\resources\\excel\\";

    String JSON_RESOURCE_ROOT = ROOT + "\\src\\main\\resources\\config\\";

    String TEST_JSON_PATH = JSON_RESOURCE_ROOT + "\\test.json";

    String TEST_EXCEL_PATH = EXCEL_RESOURCE_ROOT + "\\test.xlsx";
}
