package domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Report {
    String customerDtuPayID;
    String merchantDtuPayID;
    String token;
    BigDecimal amount;
    String timestamp;

    public Report() {
    }

    public Report(String customerDtuPayID, String merchantDtuPayID, String token, BigDecimal amount, String timestamp) {
        this.customerDtuPayID = customerDtuPayID;
        this.merchantDtuPayID = merchantDtuPayID;
        this.token = token;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getCustomerDtuPayId() {
        return customerDtuPayID;
    }

    public void setCustomerDtuPayId(String customerDtuPayID) {
        this.customerDtuPayID = customerDtuPayID;
    }

    public String getmerchantDtuPayID() {
        return merchantDtuPayID;
    }

    public void setmerchantDtuPayID(String merchantDtuPayID) {
        this.merchantDtuPayID = merchantDtuPayID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report that = (Report) o;
        return Objects.equals(customerDtuPayID, that.customerDtuPayID) && Objects.equals(merchantDtuPayID, that.merchantDtuPayID) && Objects.equals(token, that.token) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerDtuPayID, merchantDtuPayID, token, amount);
    }
}
