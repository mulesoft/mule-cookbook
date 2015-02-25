package com.cookbook.tutorial.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * Created by Mulesoft.
 */
@WebFault(name = "InvalidToken")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvalidTokenException extends RuntimeException {

}
