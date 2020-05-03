package com.fung.server.content;

/**
 * @author skytrc@163.com
 * @date 2020/4/30 9:45
 */
public class MapInitialization {

    MapInitialization instance = new MapInitialization();

    private MapInitialization() {}

    public MapInitialization getInstance() {
        return instance;
    }
}
