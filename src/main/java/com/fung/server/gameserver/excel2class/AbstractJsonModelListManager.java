package com.fung.server.gameserver.excel2class;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/5/26 15:46
 */
public abstract class AbstractJsonModelListManager<M extends Model> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJsonModelListManager.class);

    private volatile HashMap<Integer, M> modelMap;

    private String excelName;

    private String dir;

    private Class<M> modelClazz;

    @Autowired
    private JsonUtil jsonUtil;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public AbstractJsonModelListManager(String excelName, String dir) {
        this.excelName = excelName;
        this.dir = dir;
        Type type = getClass().getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(type.getClass())) {
            throw new RuntimeException("类" + getClass() + " 继承必须使用泛型");
        }
        this.modelClazz = (Class)((ParameterizedType)type).getActualTypeArguments()[0];
    }

    public void init() throws IOException, InvalidFormatException {
        modelMap = new HashMap<>();

        if (!jsonUtil.getJsonFile(ConfigPath.JSON_RESOURCE_ROOT + "\\" + dir + "\\" + excelName + ".json")) {
            jsonUtil.makeJsonFile(ConfigPath.EXCEL_RESOURCE_ROOT+ "\\" + dir + "\\" + excelName + ".xlsx", dir);
        }
        // 读取json文件，并放入map中
        List<M> models = jsonUtil.jsonToClass(ConfigPath.JSON_RESOURCE_ROOT+ "\\" + dir + "\\" + excelName + ".json", modelClazz);
        models.forEach(item -> modelMap.put(item.getId(), item));
        LOGGER.info(excelName + "读取成功");
    }

    public M getModelById(int id) {
        return modelMap.get(id);
    }

    public int getModelSize() {
        return modelMap.size();
    }

    public HashMap<Integer, M> getModelMap() {
        return modelMap;
    }

}
