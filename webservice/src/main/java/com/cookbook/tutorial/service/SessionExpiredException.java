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

    private FaultBean faultBean;

    public SessionExpiredException(String message, FaultBean faultBean, Throwable cause) {
        super(message, cause);
        this.faultBean = faultBean;
    }

    public SessionExpiredException(String message, FaultBean faultBean) {
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
