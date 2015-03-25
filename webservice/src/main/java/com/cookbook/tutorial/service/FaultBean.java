package com.cookbook.tutorial.service;

import com.cookbook.tutorial.model.CookBookEntity;

/**
 * A simple container for the {@link com.cookbook.tutorial.model.CookBookEntity} that caused the exception.
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
