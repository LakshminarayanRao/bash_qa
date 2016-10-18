package com.gsngames.qa.vimana.core.handler.impl;

import java.util.HashMap;
import java.util.Map;

import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.handler.CacheHandler;


public class CacheHandlerImpl implements CacheHandler {

    private static ThreadLocal<Map<Object, Object>> threadSafeCache = new ThreadLocal<Map<Object, Object>>();

    public CacheHandlerImpl() throws VimanaException {
        threadSafeCache.set(new HashMap<Object, Object>());
    }

    @Override
    public Object get(Object name) throws VimanaException {
        Map<Object, Object> map = threadSafeCache.get();
        return map.get(name);
    }

    @Override
    public void remove(Object name) throws VimanaException {
        Map<Object, Object> map = threadSafeCache.get();
        map.remove(name);

    }

    @Override
    public void put(Object name, Object value) throws VimanaException {
        Map<Object, Object> map = threadSafeCache.get();
        if (map == null){
            System.out.println("Cannot put into null map");
        }
        map.put(name, value);
    }

    @Override
    public void createCache() throws VimanaException {
        Map<Object, Object> map = threadSafeCache.get();
        if (map == null) {
            threadSafeCache.set(new HashMap<Object, Object>());
        }
    }

    @Override
    public void deleteCache() throws VimanaException {
        threadSafeCache.set(null);
    }

}
