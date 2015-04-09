package com.cookbook.tutorial.client;

import com.cookbook.tutorial.service.Recipe;

import java.util.List;

/**
 * Created by Mulesoft.
 */
public interface ICookbookCallback {
    void execute(List<Recipe> recipe) throws Exception;
}
