package com.dotin.school.data;

import com.dotin.school.data.PaymentVO;
import com.dotin.school.enumeration.Type;

import java.util.HashMap;
import java.util.Map;

public class PaymentInfo {
    private PaymentVO debtorObject = null;
    private Map<String, Type> paymentMap = new HashMap<>();

    public PaymentVO getDebtorObject() {
        return debtorObject;
    }

    public void setDebtorObject(PaymentVO debtorObject) {
        this.debtorObject = debtorObject;
    }

    public Map<String, Type> getPaymentMap() {
        return paymentMap;
    }

    public void setPaymentMap(Map<String, Type> paymentMap) {
        this.paymentMap = paymentMap;
    }
}
