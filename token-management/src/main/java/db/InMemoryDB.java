package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Author: Max Perez **/


public class InMemoryDB implements DbInterface {

    private static InMemoryDB inMemoryDB = null;

    List<String> inMemoryGeneratedTokens;
    Map<String, Integer> inMemoryTokens;

    public InMemoryDB() {
        inMemoryGeneratedTokens = new ArrayList<>();
        inMemoryTokens = new HashMap<>();
    }

    public static InMemoryDB getInstance() {
        if (inMemoryDB == null) {
            inMemoryDB = new InMemoryDB();
        }
        return inMemoryDB;
    }

    // GENERATED TOKENS
    public void saveGeneratedToken(String token){
        inMemoryGeneratedTokens.add(token);
    }

    public void deleteGeneratedToken(String token){
        inMemoryGeneratedTokens.remove(token);
    }

    public boolean doesGeneratedTokensExist(String token) {
        return inMemoryGeneratedTokens.contains(token);
    }


    // TOKENS
    public int getCustomersAmountOfTokens(String dtuPayID) {
        if (inMemoryTokens.containsKey(dtuPayID)) {
            return inMemoryTokens.get(dtuPayID);
        }
        return 0;
    }

    public void saveCustomerAmountOfTokens(String dtuPayID, int amount) {
        inMemoryTokens.put(dtuPayID, amount);
    }



}
