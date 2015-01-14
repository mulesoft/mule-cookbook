package org.mule.tools.devkit.store.service;

import org.mule.tools.devkit.store.model.Entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * Created by Mulesoft.
 */
@WebFault(name="InvalidEntity")
@XmlAccessorType( XmlAccessType.FIELD )
public class InvalidEntityException extends RuntimeException {
    /**
     * We only define the fault details here. Additionally each fault has a message
     * that should not be defined separately
     */
    Entity entity;

}
