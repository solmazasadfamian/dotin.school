package main.java.com.dotin.school.data;

import main.java.com.dotin.school.enumeration.Type;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentVO {
    private Type type;
    private String depositNumber;
    private BigDecimal amount;

    public PaymentVO() {
    }

    public PaymentVO(Type type, String depositNumber) {
        this.type = type;
        this.depositNumber = depositNumber;
    }

    public PaymentVO(Type type, String depositNumber, BigDecimal amount) {
        this.type = type;
        this.depositNumber = depositNumber;
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
        PaymentVO paymentVO = (PaymentVO) o;
        return depositNumber.equals(paymentVO.depositNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depositNumber);
    }

    @Override
    public String toString() {
        return type.toString().toLowerCase() + "\t" + depositNumber + "\t" + amount + "\n";
    }
}
