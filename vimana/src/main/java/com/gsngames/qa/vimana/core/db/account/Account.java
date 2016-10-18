package com.gsngames.qa.vimana.core.db.account;

import org.simpleframework.xml.Attribute;

public class Account {

    @Attribute(required = true)
    private String userName;

    @Attribute(required = true)
    private String password;

    @Attribute(required = false)
    private String test;
    
    private boolean isCurrentlyUsed=false;
    
    public Account(){}

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

	public void setCurrentlyUsed(boolean isCurrentlyUsed) {
		this.isCurrentlyUsed = isCurrentlyUsed;
	}

	public boolean isCurrentlyUsed() {
		return isCurrentlyUsed;
	}

}
