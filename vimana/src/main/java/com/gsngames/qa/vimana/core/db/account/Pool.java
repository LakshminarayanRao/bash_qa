package com.gsngames.qa.vimana.core.db.account;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class Pool {

    @Attribute(required = true)
    private String name;

    @ElementList(name = "account", inline = true, required = true)
    private List<Account> accounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    /**
     * Adds a new account to the end of the list
     * 
     */
    public void addAccount(Account account) {
    	List<Account> oldaccounts = getAccounts();
    	oldaccounts.add(accounts.size()-1, account);
    	setAccounts(oldaccounts);
    }
    
    /**
     * Removes a specified account
     * 
     */
    public void removeAccount(Account account) {
    	List<Account> oldaccounts = getAccounts();
    	oldaccounts.remove(account);
    	setAccounts(oldaccounts);
    }

}
