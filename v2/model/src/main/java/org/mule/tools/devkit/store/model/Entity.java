package org.mule.tools.devkit.store.model;

import org.mule.tools.devkit.store.model.custom.CustomEntity;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by Mulesoft.
 */
@XmlSeeAlso({ Author.class, Book.class, CustomEntity.class })
public abstract class Entity {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
