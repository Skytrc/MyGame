package com.fung.server.content;

import com.fung.server.content.entity.map.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 9:45
 */
public class MapManagement {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapManagement.class);

    public BaseMap initialPlace;

    public BaseMap village;

    public BaseMap forest;;

    public BaseMap castle;

    public MapManagement() {
        this.initialPlace = InitialPlace.getInstance();
        this.village = Village.getInstance();
        this.forest = Forest.getInstance();
        this.castle = Castle.getInstance();

        initialPlace.init();
        village.init();
        forest.init();
        castle.init();
        LOGGER.info("地图初始化完成");
    }

}
