package org.mule.tools.devkit.store.model;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by Mulesoft.
 */
@XmlSeeAlso({ Author.class, Book.class })
public abstract class Entity {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
