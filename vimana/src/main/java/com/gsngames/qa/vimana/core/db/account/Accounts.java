package com.gsngames.qa.vimana.core.db.account;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Accounts {

    @ElementList(name = "pool", inline = true, required = true)
    private List<Pool> pools;

    public List<Pool> getPools() {
        return pools;
    }

    public void setPools(List<Pool> pools) {
        this.pools = pools;
    }

    public Pool getPool(String poolName) {
        Pool resultPool = null;
        for (Pool pool : this.pools) {
            if (pool.getName().trim().equalsIgnoreCase(poolName)) {
                resultPool = pool;
                break;
            }
        }
        return resultPool;
    }

}
