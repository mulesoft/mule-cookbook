package com.cookbook.tutorial.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * Exception thrown when Username and password where not the expected ones
 */
@WebFault(name = "InvalidCredentials")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvalidCredentialsException extends RuntimeException {

}
