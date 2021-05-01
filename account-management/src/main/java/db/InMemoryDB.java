package db;

import domain.DTUPayAccount;

import java.util.HashMap;
import java.util.Map;

/** Author: Josef Brøndum Schmidt **/


public class InMemoryDB implements DbInterface {

    private static InMemoryDB inMemoryDB = null;
    private Map<String, DTUPayAccount> inMemoryAccounts;


    public InMemoryDB() {
        inMemoryAccounts = new HashMap<>();
    }

    public static InMemoryDB getInstance() {
        if (inMemoryDB == null) {
            inMemoryDB = new InMemoryDB();
        }
        return inMemoryDB;
    }

    @Override
    public Map<String, DTUPayAccount> getAccounts() {
        return inMemoryAccounts;
    }

    @Override
    public void saveAccount(String dtuPayID, DTUPayAccount dtuPayAccount) {
        inMemoryAccounts.put(dtuPayID, dtuPayAccount);
    }

    @Override
    public DTUPayAccount getDTUPayAccount(String dtuPayID) {
        return inMemoryAccounts.get(dtuPayID);
    }


}
