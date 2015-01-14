package org.mule.tools.devkit.store.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * Created by Mulesoft.
 */
@WebFault(name="SessionExpired")
@XmlAccessorType( XmlAccessType.FIELD )
public class SessionExpiredException extends RuntimeException{

}
