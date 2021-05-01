package domain;

import java.math.BigDecimal;
import java.util.Objects;

public class MerchantReport {

    String merchantDtuPayID;
    String token;
    BigDecimal amount;
    String timestamp;

    public MerchantReport(String merchantDtuPayID, String token, BigDecimal amount, String timestamp) {
        this.merchantDtuPayID = merchantDtuPayID;
        this.token = token;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public MerchantReport(Report t){
        this.merchantDtuPayID=t.getmerchantDtuPayID();
        this.token=t.getToken();
        this.amount=t.getAmount();
        this.timestamp=t.getTimestamp();
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
        MerchantReport that = (MerchantReport) o;
        return Objects.equals(merchantDtuPayID, that.merchantDtuPayID) && Objects.equals(token, that.token) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchantDtuPayID, token, amount);
    }
}

