package com.fung.server.gameserver.excel2class;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author skytrc@163.com
 * @date 2020/5/25 19:33
 * TODO 把Excel表格转化为MMap存储在Json，这样读取时不用再进行多一步的处理，可以直接用Map读取。
 * TODO 根据Excel表格的目录自动生成对应的文件夹
 */
@Component
public class JsonUtil {

    private Gson gson = new Gson();

    @Autowired
    private ExcelUtil excelUtil;

    /**
     * Json文件是否存在
     * @param jsonPath Json文件地址
     * @return boolean
     */
    public boolean getJsonFile(String jsonPath) {
        return new File(jsonPath).exists();
    }

    /**
     * 读取Excel数据转化为Json格式
     * @param excelPath excel地址
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void makeJsonFile(String excelPath, String dir) throws IOException, InvalidFormatException {
        JsonArray jsonArray = excelUtil.excelTransformJson(excelPath);
        String fileName = excelUtil.getExcelName(excelPath);
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\config\\" + dir
                + "\\" + fileName + ".json";
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(jsonArray.toString().getBytes());
        outputStream.close();
    }

    /**
     * 读取Json转化为String
     * @param jsonPath Json文件地址
     * @return String
     * @throws IOException
     */
    public String readJsonFile(String jsonPath) throws IOException {
        StringBuffer buffer = new StringBuffer();
        File file = new File(jsonPath);
        InputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // 记录读取行
        String line = reader.readLine();
        while(line != null) {
            buffer.append(line);
            line = reader.readLine();
        }
        return buffer.toString();
    }

    /**
     * @param jsonPath
     * @param <M>
     * @return
     * @throws IOException
     */
    @SuppressWarnings({"unchecked"})
    public <M extends Model> List<M> jsonToClass(String jsonPath, Class<M> clzz) throws IOException {
        String jsonContent = readJsonFile(jsonPath);
        List<M> mList = new ArrayList<>();
        JsonArray jsonArray = JsonParser.parseString(jsonContent).getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
            mList.add(gson.fromJson(jsonElement, clzz));
        }
        return mList;
    }
}
