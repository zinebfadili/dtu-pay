package db;

public interface DbInterface {

    void saveGeneratedToken(String token);

    void deleteGeneratedToken(String token);

    boolean doesGeneratedTokensExist(String token);

    int getCustomersAmountOfTokens(String dtuPayID);

    void saveCustomerAmountOfTokens(String dtuPayID, int amount);


}
