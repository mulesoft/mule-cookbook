package com.cookbook.tutorial.service;

import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;

/**
 * Created by Mulesoft.
 */
public class DAOCookBookServiceFactory {

    private static IDAOCookBookService instance;

    public static IDAOCookBookService getInstance(){
        if(instance==null){
            instance = new CookBookDefaultBackEndImp();
        }
        return instance;
    }
}
