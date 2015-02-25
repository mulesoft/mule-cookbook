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

    private FaultBean faultBean;

    public InvalidCredentialsException(String message, FaultBean faultBean, Throwable cause) {
        super(message, cause);
        this.faultBean = faultBean;
    }

    public InvalidCredentialsException(String message, FaultBean faultBean) {
        super(message);
        this.faultBean = faultBean;
    }

    public FaultBean getFaultBean() {
        return faultBean;
    }

    public void setFaultBean(FaultBean faultBean) {
        this.faultBean = faultBean;
    }

}
