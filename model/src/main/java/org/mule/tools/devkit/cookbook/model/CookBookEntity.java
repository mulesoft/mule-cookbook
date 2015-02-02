package org.mule.tools.devkit.cookbook.model;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by Mulesoft.
 */
@XmlSeeAlso({ Ingredient.class, Recipe.class })
public abstract class CookBookEntity {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
