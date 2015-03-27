package com.cookbook.tutorial.internal.service;

import com.cookbook.tutorial.service.ObjectFactory;

/**
 * Created by Mulesoft.
 */
public class ObjectFactorySingleton {

    private static ObjectFactory factory;

    public static ObjectFactory getInstance() {
        if (factory == null)
            factory = new ObjectFactory();
        return factory;
    }
}
