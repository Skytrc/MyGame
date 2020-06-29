package com.fung.server.gameserver.excel2class.test;

import com.fung.server.gameserver.content.entity.Test;
import com.fung.server.gameserver.excel2class.AbstractJsonModelListManager;
import org.springframework.stereotype.Component;

/**
 * @author skytrc@163.com
 * @date 2020/5/26 17:46
 */
@Component
public class ModelTest extends AbstractJsonModelListManager<Test> {

    public ModelTest() {
        super("test", "test");
    }
}
