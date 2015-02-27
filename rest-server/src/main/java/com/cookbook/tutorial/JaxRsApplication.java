package com.cookbook.tutorial;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * C
 */
public class JaxRsApplication extends Application {
    private final Set<Class<?>> classes;
    public JaxRsApplication() {
        HashSet<Class<?>> c = new HashSet<Class<?>>();
        c.add(IngredientResource.class);
        c.add(RecipeResource.class);
        c.add(AuthorizeResource.class);
        classes = Collections.unmodifiableSet(c);
    }
    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}