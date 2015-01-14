package org.mule.tools.devkit.store.model;

import java.util.List;

/**
 * Created by Mulesoft.
 */
public class Sale extends Entity {

   private List<Book> items;

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }
}
