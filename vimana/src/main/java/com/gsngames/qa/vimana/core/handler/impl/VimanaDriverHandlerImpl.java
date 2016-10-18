package com.gsngames.qa.vimana.core.handler.impl;


import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.VimanaRemoteDriver;
import com.gsngames.qa.vimana.core.handler.VimanaDriverHandler;

public class VimanaDriverHandlerImpl implements VimanaDriverHandler {
	private static ThreadLocal<VimanaRemoteDriver> threadLocalDriver = new ThreadLocal<VimanaRemoteDriver>();

	@Override
	public VimanaRemoteDriver newInstance() throws VimanaException {
		VimanaRemoteDriver driver = null;
		try {
			driver = new VimanaRemoteDriver();
			threadLocalDriver.set(driver);
		} catch (Exception e) {
			e.printStackTrace();
			throw new VimanaException("Error while creating session for thread:"+ Thread.currentThread(), e);
		}
		return driver;
	}

	@Override
	public VimanaRemoteDriver getInstance() throws VimanaException {
		VimanaRemoteDriver driver = null;
		if (threadLocalDriver.get() == null) {
			driver = newInstance();
		} else {
			driver = threadLocalDriver.get();
		}
		return driver;
	}

	@Override
	public void releaseInstace() throws VimanaException {
		getInstance().releaseDrivers();
	}
}
