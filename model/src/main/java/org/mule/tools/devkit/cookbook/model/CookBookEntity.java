package org.mule.tools.devkit.cookbook.model;

import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Date;

/**
 * Created by Mulesoft.
 */
@XmlSeeAlso({ Ingredient.class, Recipe.class })
public abstract class CookBookEntity {

    private Integer id;
    private Date lastModified;
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
}
