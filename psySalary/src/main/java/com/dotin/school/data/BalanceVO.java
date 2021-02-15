package main.java.com.dotin.school.data;

import java.math.BigDecimal;
import java.util.Objects;

public class BalanceVO {
    private String depositNumber;
    private BigDecimal amount;

    public BalanceVO() {
    }

    public BalanceVO(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    public BalanceVO(String depositNumber, BigDecimal amount) {
        this.depositNumber = depositNumber;
        this.amount = amount;
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
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
        BalanceVO balanceVO = (BalanceVO) o;
        return depositNumber.equals(balanceVO.depositNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depositNumber);
    }

    @Override
    public String toString() {
        return depositNumber + "\t" + amount + "\n";
    }
}
