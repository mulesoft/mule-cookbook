package com.cookbook.tutorial.service;

import com.cookbook.tutorial.model.CookBookEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
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
@WebFault(name = "InvalidEntity", faultBean = "com.cookbook.tutorial.service.FaultBean")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class InvalidEntityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Field containing the CookBook entity that caused the exception
     */
    private FaultBean faultBean;

    public InvalidEntityException(){

    }

    public InvalidEntityException(String message, FaultBean faultBean, Throwable cause) {
        super(message, cause);
        this.faultBean = faultBean;
    }

    public InvalidEntityException(String message, FaultBean faultBean) {
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
