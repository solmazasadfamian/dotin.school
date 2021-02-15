package main.java.com.dotin.school.service;

import main.java.com.dotin.school.constants.Errors;
import main.java.com.dotin.school.data.BalanceVO;
import main.java.com.dotin.school.data.PaymentVO;
import main.java.com.dotin.school.data.TransactionVO;
import main.java.com.dotin.school.enumeration.Type;
import main.java.com.dotin.school.exceptions.DepositNotFoundException;
import main.java.com.dotin.school.exceptions.FileFormatException;
import main.java.com.dotin.school.exceptions.NotEnoughBalanceException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferService {
    private final static Logger log = Logger.getLogger(TransferService.class.getName());

    private void validationCheck(String balancePath, String paymentPath, String separator, List<TransactionVO> transactionVOS) throws FileFormatException, NotEnoughBalanceException, DepositNotFoundException {
        BalanceService balanceService = new BalanceService();
        PaymentService paymentService = new PaymentService();
        BigDecimal paidAmount = BigDecimal.ZERO;
        if (transactionVOS != null) {
            for (TransactionVO transactionVO : transactionVOS) {
                paidAmount = paidAmount.add(transactionVO.getAmount());
            }
        }
        List<BalanceVO> balanceVOList = balanceService.readBalanceFile(balancePath, separator);
        Boolean isExist;
        for (PaymentVO paymentVO : paymentService.readPaymentFile(paymentPath, separator)) {
            isExist = false;
            BigDecimal amount = null;
            if (paymentVO != null && paymentVO.getType() == Type.DEBTOR)
                amount = paymentVO.getAmount();
            for (BalanceVO balanceVO : balanceVOList) {
                if (paymentVO != null && paymentVO.getType() == Type.DEBTOR
                        && paymentVO.getDepositNumber().equals(balanceVO.getDepositNumber())
                        && balanceVO.getAmount().compareTo(amount.subtract(paidAmount)) < 0)
                    throw new NotEnoughBalanceException(Errors.NOT_ENOUGH_BALANCE, Errors.balanceEMessage);
                if (paymentVO.getDepositNumber().equals(balanceVO.getDepositNumber()))
                    isExist = true;
            }
            if (isExist == false) {
                throw new DepositNotFoundException(paymentVO.getDepositNumber());
            }
        }
        log.info("balance debtor has sufficient amount");
    }

    public void paySalary(String paymentPath, String balancePath, String transactionPath, String separator) throws FileFormatException, DepositNotFoundException, NotEnoughBalanceException, IOException {
        TransactionsService transactionService = new TransactionsService();
        BalanceService balanceService = new BalanceService();
        PaymentService paymentService = new PaymentService();
        PaymentVO debtorObject = null;
        Map<String, Type> paymentMap = new HashMap<>();
        List<TransactionVO> transactionVOS = null;
        try {
            Files.createFile(Paths.get(transactionPath));
        } catch (FileAlreadyExistsException e) {
            transactionVOS = transactionService.readTransactionFile(transactionPath);
        }
        for (PaymentVO paymentVO : paymentService.readPaymentFile(paymentPath, separator)) {
            paymentMap.put(paymentVO.getDepositNumber(), paymentVO.getType());
            if (paymentVO.getType().equals(Type.DEBTOR))
                debtorObject = paymentVO;
        }
        validationCheck(balancePath, paymentPath, separator, transactionVOS);
        for (PaymentVO paymentVO : paymentService.readPaymentFile(paymentPath, separator)) {
            if (paymentVO.getType().equals(Type.DEBTOR))
                continue;
            TransactionVO transactionVO = new TransactionVO(debtorObject.getDepositNumber(), paymentVO.getDepositNumber(), paymentVO.getAmount());
            if (transactionVOS != null && transactionVOS.contains(transactionVO)) {
                continue;
            }
            balanceService.updateBalanceFile(paymentMap, balancePath, separator, paymentVO);
            transactionService.writeTransactionFile(transactionPath, transactionVO);
        }
        log.info("payment was successfully done");
    }
}
