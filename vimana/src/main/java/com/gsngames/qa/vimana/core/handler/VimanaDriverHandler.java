package com.gsngames.qa.vimana.core.handler;

import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.VimanaRemoteDriver;

public interface VimanaDriverHandler {
    
    public VimanaRemoteDriver newInstance() throws VimanaException;
    public VimanaRemoteDriver getInstance() throws VimanaException;
    public void releaseInstace() throws VimanaException;
}
