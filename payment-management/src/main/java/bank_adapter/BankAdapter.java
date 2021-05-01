package bank_adapter;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;

import java.math.BigDecimal;

/** Author: Yousef Mohsen **/

public class BankAdapter implements BankAdapterInterface {
    BankService bank = new BankServiceService().getBankServicePort();
    @Override
    public void bankTransferring(String customerBankAccount, String merchantBankAccount, BigDecimal amount) throws Exception {
        bank.transferMoneyFromTo(customerBankAccount, merchantBankAccount, amount, "Payment through DTUPay-service");
    }
}
