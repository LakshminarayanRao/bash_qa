package com.gsngames.qa.vimana.core;

/**
 * Generic Vimana Exception class
 * 
 * @author lnr
 *
 */
public class VimanaException extends Exception {

	private static final long serialVersionUID = 4878388143601534255L;

	public VimanaException() {
		super();
	}

	public VimanaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public VimanaException(String arg0) {
		super(arg0);
	}

	public VimanaException(Throwable arg0) {
		super(arg0);
	}

}
