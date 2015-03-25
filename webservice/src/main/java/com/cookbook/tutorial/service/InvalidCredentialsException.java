package com.cookbook.tutorial.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebFault;

/**
 * Exception thrown when Username and password where not the expected ones
 */
@WebFault(name = "InvalidCredentials", faultBean = "com.cookbook.tutorial.service.FaultBean")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvalidCredentialsException extends Exception {

    private static final long serialVersionUID = 1L;


    public InvalidCredentialsException(){

    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialsException(String message, FaultBean faultBean) {
        super(message);
    }

}
