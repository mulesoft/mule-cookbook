package com.cookbook.tutorial;

import com.cookbook.tutorial.internal.service.CookBookDefaultBackEndImp;
import com.cookbook.tutorial.service.IDAOCookBookService;

/**
 * Created by Mulesoft.
 */
public class DALProvider {
    private static IDAOCookBookService INSTANCE;
    private DALProvider(){

    }

    public static IDAOCookBookService getInstance(){
        if(INSTANCE==null){
            INSTANCE = new CookBookDefaultBackEndImp();
        }
        return INSTANCE;
    }
}
