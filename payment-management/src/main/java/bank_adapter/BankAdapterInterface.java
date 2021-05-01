package bank_adapter;

import java.math.BigDecimal;

public interface BankAdapterInterface {
    void bankTransferring(String customerBankAccount, String merchantBankAccount, BigDecimal amount) throws Exception;
}
