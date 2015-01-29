package org.mule.tools.devkit.store.model;

import java.util.List;

/**
 * Created by Mulesoft.
 */
public class Sale extends Entity {

    private List<Entity> items;

    public List<Entity> getItems() {
        return items;
    }

    public void setItems(List<Entity> items) {
        this.items = items;
    }
}
