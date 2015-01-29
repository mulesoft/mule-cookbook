package org.mule.tools.devkit.store.model;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by Mulesoft.
 */
@XmlSeeAlso({ Author.class, Book.class })
public abstract class Entity {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
