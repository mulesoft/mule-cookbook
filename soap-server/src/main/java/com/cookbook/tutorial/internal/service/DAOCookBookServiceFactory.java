package com.cookbook.tutorial.internal.service;

import com.cookbook.tutorial.service.IDAOCookBookService;

/**
 * Created by Mulesoft.
 */
public class DAOCookBookServiceFactory {

    public static IDAOCookBookService newInstance(){
        return new CookBookDefaultBackEndImp();
    }
}
