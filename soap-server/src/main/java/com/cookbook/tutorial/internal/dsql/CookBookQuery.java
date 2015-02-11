package com.cookbook.tutorial.internal.dsql;

import java.util.List;

/**
 * Created by Mulesoft.
 */
public interface CookBookQuery {
    boolean getAllFields();
    List<String> getFields();
    String getEntity();
    List<String> getCriteria();
}
