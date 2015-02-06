package com.cookbook.tutorial.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * Created by Mulesoft.
 *
 * Thrown when retrieving or updating entities with a none existing Id.
 *
 */
@WebFault(name = "NoSuchEntity")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoSuchEntityException extends RuntimeException {

}
