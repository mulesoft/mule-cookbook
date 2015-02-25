package com.cookbook.tutorial.service;

import com.cookbook.tutorial.model.CookBookEntity;

/**
 * Created by Mulesoft.
 */
public class FaultBean {

    public CookBookEntity getEntity() {
        return entity;
    }

    public void setEntity(CookBookEntity entity) {
        this.entity = entity;
    }

    private CookBookEntity entity;

}
