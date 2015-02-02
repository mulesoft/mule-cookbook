package org.mule.tools.devkit.cookbook.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * Created by Mulesoft.
 * <p/>
 * Randomly this exception is triggered so that users can test the reconnection strategy.
 *
 */
@WebFault(name = "SessionExpired")
@XmlAccessorType(XmlAccessType.FIELD)
public class SessionExpiredException extends RuntimeException {

}
