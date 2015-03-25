package com.cookbook.tutorial.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebFault;

/**
 * Created by Mulesoft.
 *
 * Thrown when retrieving or updating entities with a none existing Id.
 *
 */
@WebFault(name = "NoSuchEntity", faultBean = "com.cookbook.tutorial.service.FaultBean")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class NoSuchEntityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Field containing the CookBook entity that caused the exception
     */
    private FaultBean faultBean;

    public NoSuchEntityException(){

    }

    public NoSuchEntityException(String message, FaultBean faultBean, Throwable cause) {
        super(message, cause);
        this.faultBean = faultBean;
    }

    public NoSuchEntityException(String message, FaultBean faultBean) {
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
