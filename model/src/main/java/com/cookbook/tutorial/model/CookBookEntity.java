package com.cookbook.tutorial.model;

import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Date;

/**
 * Base Entity of all Cook Book entities.
 */
@XmlSeeAlso({ Ingredient.class, Recipe.class })
public abstract class CookBookEntity {

    /**
     * Unique identifier of an Entity
     */
    private Integer id;

    /**
     * Date when it was created in the system.
     */
    private Date created;

    /**
     * Date of the last time it was modified
     */
    private Date lastModified;

    /**
     * Descriptive name of the entity.
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
