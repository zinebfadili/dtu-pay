package domain;

import java.math.BigDecimal;

public class PaymentInfo {
    String customerDtuPayID;
    String merchantDtuPayID;
    String customerBankID;
    String merchantBankID;
    BigDecimal amount;
    String token;

    public PaymentInfo() {
    }

    public String getCustomerDtuPayID() {
        return customerDtuPayID;
    }

    public void setCustomerDtuPayID(String customerDtuPayID) {
        this.customerDtuPayID = customerDtuPayID;
    }

    public String getMerchantDtuPayID() {
        return merchantDtuPayID;
    }

    public void setMerchantDtuPayID(String merchantDtuPayID) {
        this.merchantDtuPayID = merchantDtuPayID;
    }

    public String getCustomerBankID() {
        return customerBankID;
    }

    public void setCustomerBankID(String customerBankID) {
        this.customerBankID = customerBankID;
    }

    public String getMerchantBankID() {
        return merchantBankID;
    }

    public void setMerchantBankID(String merchantBankID) {
        this.merchantBankID = merchantBankID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
