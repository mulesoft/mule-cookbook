package com.cookbook.tutorial.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebFault;

/**
 * Created by Mulesoft.
 * <p/>
 * Randomly this exception is triggered so that users can test the reconnection strategy.
 *
 */
@WebFault(name = "SessionExpired", faultBean = "com.cookbook.tutorial.service.FaultBean")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SessionExpiredException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SessionExpiredException(){

    }

    public SessionExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionExpiredException(String message) {
        super(message);
    }

}
