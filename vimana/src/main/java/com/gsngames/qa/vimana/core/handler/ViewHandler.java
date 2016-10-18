package com.gsngames.qa.vimana.core.handler;

import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.db.view.Locator;

/**
 * 
 * @author lnr
 *
 */
public interface ViewHandler {
    
    public Locator getElement(String viewName,String elementName) throws VimanaException;
    public String getAction(String viewName,String elementName) throws VimanaException, Exception;

}
