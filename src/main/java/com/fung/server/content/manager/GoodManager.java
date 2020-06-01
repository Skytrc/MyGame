package com.fung.server.content.manager;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author skytrc@163.com
 * @date 2020/6/1 11:09
 */
@Component
public class GoodManager {

    @Autowired
    MedicineManager medicineManager;

    public void goodInit() throws IOException, InvalidFormatException {
        medicineManager.medicineInit();
    }

    @SuppressWarnings({"rawtypes"})
    private Map getGood(String goodName) {
        if ("medicine".equals(goodName)) {
            return medicineManager.getMedicineMap();
        }
        else {
            return null;
        }
    }
}
