package org.mule.tools.devkit.store.service;

import org.mule.tools.devkit.store.model.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * Created by Mulesoft.
 * <p/>
 * An invalid entity exception will be thrown in any of the following cases:
 * At creation:
 * <ul>
 * <li>Try to create an entity and provide an Id.</li>
 * <li>Try to create an Author with out a first name.</li>
 * <li>Try to create a Book with no price.</li>
 * <li>Try to create a sale with no books.</li>
 * </ul>
 *
 * During updates:
 * <ul>
 * <li>When an entity Id provided doesn't exists.</li>
 * <li>When an entity didn't provide an Id and the required fields.</li>
 * </ul>
 *
 * At deletes:
 * <ul>
 * <li>When an entity Id provided doesn't exists.</li>
 * </ul>
 */
@WebFault(name = "InvalidEntity")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvalidEntityException extends RuntimeException {

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * We only define the fault details here. Additionally each fault has a message
     * that should not be defined separately
     */
    private Entity entity;

    public InvalidEntityException() {
        super("");
        this.entity = null;
    }

    public InvalidEntityException(Entity entity,String message) {
        super(message);
        this.entity = entity;
    }
}
