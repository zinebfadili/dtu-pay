package db;

import domain.DTUPayAccount;

import java.util.Map;

public interface DbInterface {

    void saveAccount(String dtuPayID, DTUPayAccount dtuPayAccount);

    DTUPayAccount getDTUPayAccount(String dtyPayID);

    Map<String, DTUPayAccount> getAccounts();
}
