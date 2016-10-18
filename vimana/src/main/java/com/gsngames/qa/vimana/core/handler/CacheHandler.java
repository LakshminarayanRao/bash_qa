package com.gsngames.qa.vimana.core.handler;

import com.gsngames.qa.vimana.core.VimanaException;

/**
 * The Cache is created per test/thread. This serves as a session cache
 * during the test execution. Any parameters that need to be saved and accessed can 
 * be saved in the session object
 * 
 * 
 * @author lnr
 *
 */
public interface CacheHandler {

    public void createCache() throws VimanaException;

    public void deleteCache() throws VimanaException;

    public void put(Object name,Object value) throws VimanaException;
    
    public Object get(Object name) throws VimanaException;

    public void remove(Object name) throws VimanaException;

}
