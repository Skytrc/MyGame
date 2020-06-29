package com.fung.server.gameserver.excel2class.test;

import com.fung.server.gameserver.content.config.manager.MapManager;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author skytrc@163.com
 * @date 2020/5/25 15:48
 */
public class ExcelTest {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MapManager bean = context.getBean(MapManager.class);
        bean.mapInit();
    }
}
