package com.gsngames.qa.vimana.core.handler.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.gsngames.qa.vimana.core.VimanaConfig;
import com.gsngames.qa.vimana.core.VimanaException;
import com.gsngames.qa.vimana.core.db.PreDataReader;
import com.gsngames.qa.vimana.core.db.account.Account;
import com.gsngames.qa.vimana.core.db.account.Accounts;
import com.gsngames.qa.vimana.core.db.account.Pool;
import com.gsngames.qa.vimana.core.handler.AccountHandler;

/**
 * 
 * @author lnr
 * 
 */
public class AccountHandlerImpl implements AccountHandler {
	private PreDataReader preDataReader;
	private Accounts accounts;

	private Pool pool;
	private Map<String, Account> accountCache = Collections.synchronizedMap(new HashMap<String, Account>());
	private int totalAccounts = 0;

	public AccountHandlerImpl() throws VimanaException {
		String poolName = VimanaConfig.getConfigProperty("accountPool");
		this.preDataReader = new PreDataReader("account.xml", VimanaConfig.getConfigProperty("product"),
				VimanaConfig.getConfigProperty("intl"));
		Serializer serializer = new Persister();
		String xml = null;
		try {
			xml = preDataReader.getPreProcessedXML();
			this.accounts = serializer.read(Accounts.class, xml);
			this.pool = this.accounts.getPool(poolName);
			if (this.pool != null) {
				totalAccounts = this.pool.getAccounts().size();
			} else {
				throw new VimanaException("account.xml:Account pool is not available for '" + poolName + "'");
			}
		} catch (Exception e) {
			throw new VimanaException(e.fillInStackTrace());
		}
	}

	public synchronized int getTotalAccounts() {
		return (this.totalAccounts);
	}

	/**
	 * updated to get an unused account from the account pool
	 */
	public synchronized Account getUnusedAccount() throws VimanaException {
		String key = Thread.currentThread().getName();
		Account account = accountCache.get(key);
		if (account == null) {
			// Get the account which is currently not being used
			for (int counter = 0; counter < this.totalAccounts; counter++) {
				Account buffer = this.pool.getAccounts().get(counter);
				if (!buffer.isCurrentlyUsed()) {
					account = buffer;
					this.pool.getAccounts().get(counter).setCurrentlyUsed(true);
					break;
				}
			}
			accountCache.put(key, account);
		}
		if (account == null) {
			throw new VimanaException("Unused account not found");
		} else {
			return account;
		}

	}

	public synchronized Account getAccount(String testId) throws VimanaException {

		Account account = accountCache.get(testId);
		if (account == null) {
			for (Account temp : this.pool.getAccounts()) {
				if (testId.equalsIgnoreCase(temp.getTest())) {
					accountCache.put(testId, temp);
					break;
				}
			}
		}
		account = accountCache.get(testId);
		if (account == null) {
			throw new VimanaException("Account not for test id:" + testId);
		} else {
			return account;
		}
	}

	public synchronized void releaseAccountForCurrentThread() throws VimanaException {
		Account removeAccount = accountCache.get(Thread.currentThread().getName());

		// the below step ensures that the account to be released would be the
		// last in the list after reseting the currentlyUsed flag
		removeAccount(removeAccount);
		removeAccount.setCurrentlyUsed(false);
		addAccount(removeAccount);
		accountCache.remove(Thread.currentThread().getName());
	}

	/**
	 * This method is used to add a new account to the pool of accounts in real
	 * time
	 * 
	 * @param account
	 * @throws VimanaException
	 */
	public synchronized void addAccount(Account account) throws VimanaException {
		this.pool.addAccount(account);
		totalAccounts = this.pool.getAccounts().size();
	}

	/**
	 * This method is used to remove an account from the pool of account
	 * 
	 * @param account
	 * @throws VimanaException
	 */
	public synchronized void removeAccount(Account account) throws VimanaException {
		this.pool.removeAccount(account);
		totalAccounts = this.pool.getAccounts().size();
	}

}
