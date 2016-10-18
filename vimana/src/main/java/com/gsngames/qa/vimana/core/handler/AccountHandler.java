package com.gsngames.qa.vimana.core.handler;


import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.db.account.Account;

/**
 * 
 * @author lnr
 *
 */
public interface AccountHandler {

  //  public Account getAccount(int index) throws VimanaException;

    public int getTotalAccounts();

    public Account getUnusedAccount() throws VimanaException;

    public Account getAccount(String testId) throws VimanaException;
    
    public  void releaseAccountForCurrentThread() throws VimanaException;
    
    public void addAccount(Account account) throws VimanaException;
    public  void removeAccount(Account account) throws VimanaException ;

}
