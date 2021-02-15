package main.java.com.dotin.school.data;

import java.math.BigDecimal;
import java.util.Objects;

public class TransactionVO {
    private String debtorDepositNumber;
    private String creditorDepositNumber;
    private BigDecimal amount;

    public TransactionVO(String debtorDepositNumber, String creditorDepositNumber, BigDecimal amount) {
        this.debtorDepositNumber = debtorDepositNumber;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;
    }

    public TransactionVO() {
    }

    public String getDebtorDepositNumber() {
        return debtorDepositNumber;
    }

    public void setDebtorDepositNumber(String debtorDepositNumber) {
        this.debtorDepositNumber = debtorDepositNumber;
    }

    public String getCreditorDepositNumber() {
        return creditorDepositNumber;
    }

    public void setCreditorDepositNumber(String creditorDepositNumber) {
        this.creditorDepositNumber = creditorDepositNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionVO that = (TransactionVO) o;
        return amount.equals(that.amount) &&
                Objects.equals(debtorDepositNumber, that.debtorDepositNumber) &&
                Objects.equals(creditorDepositNumber, that.creditorDepositNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debtorDepositNumber, creditorDepositNumber, amount);
    }

    @Override
    public String toString() {
        return debtorDepositNumber + "\t" + creditorDepositNumber + "\t" + amount + "\n";

    }
}
