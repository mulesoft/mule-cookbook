package com.cookbook.tutorial.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebFault;

/**
 * Created by Mulesoft.
 */
@WebFault(name = "InvalidToken", faultBean = "com.cookbook.tutorial.service.FaultBean")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class InvalidTokenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidTokenException(){

    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTokenException(String message) {
        super(message);
    }

}
