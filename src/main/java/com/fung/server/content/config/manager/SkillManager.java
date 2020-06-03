package com.fung.server.content.config.manager;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author skytrc@163.com
 * @date 2020/6/3 11:38
 */
@Component
public class SkillManager {

    @Autowired
    DamageSkillManager damageSkillManager;

    public void skillInit() throws IOException, InvalidFormatException {
        damageSkillManager.damageSkillInit();
    }
}
