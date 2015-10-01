/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook.handler;

import org.mule.api.annotations.Handle;
import org.mule.api.annotations.components.Handler;

import com.cookbook.tutorial.service.InvalidEntityException;

@Handler
public class CookbookHandler {

    @Handle
    public void handleException(Exception ex) throws Exception {
        if (ex instanceof InvalidEntityException) {
            throw new RuntimeException("You cannot provide an Id when creating an Ingredient");
        } else {
            // Just let it go
            throw ex;
        }
    }

}
