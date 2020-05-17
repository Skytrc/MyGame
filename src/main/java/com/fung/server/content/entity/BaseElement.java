package com.fung.server.content.entity;

import javax.persistence.*;

/**
 * @author skytrc@163.com
 * @date 2020/5/14 10:39
 */
@MappedSuperclass
public abstract class BaseElement {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * 名字
     */
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
