package com.dotin.school.service;

import com.dotin.school.constants.Errors;
import com.dotin.school.constants.Variables;
import com.dotin.school.data.BalanceVO;
import com.dotin.school.data.PaymentVO;
import com.dotin.school.data.TransactionVO;
import com.dotin.school.enumeration.Type;
import com.dotin.school.exceptions.DepositNotFoundException;
import com.dotin.school.exceptions.FileFormatException;
import com.dotin.school.exceptions.NotEnoughBalanceException;
import com.dotin.school.threads.TransferCallable;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransferService {
    private final static Logger log = Logger.getLogger(TransferService.class.getName());

    private void validationCheck(List<TransactionVO> transactionVOS) throws FileFormatException, NotEnoughBalanceException, DepositNotFoundException {
        BalanceService balanceService = BalanceService.getInstance();
        PaymentService paymentService = new PaymentService();
        BigDecimal paidAmount = BigDecimal.ZERO;
        if (transactionVOS != null) {
            for (TransactionVO transactionVO : transactionVOS) {
                paidAmount = paidAmount.add(transactionVO.getAmount());
            }
        }
        List<BalanceVO> balanceVOList = balanceService.readBalanceFile(Variables.BALANCE_FILE_PATH, Variables.SEPARATOR);
        Boolean isExist;
        for (PaymentVO paymentVO : paymentService.readPaymentFile(Variables.PAYMENT_FILE_PATH, Variables.SEPARATOR)) {
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

    public void paySalary() throws FileFormatException, DepositNotFoundException, NotEnoughBalanceException, IOException {
        int threadSize = Variables.THREAD_POOL_SIZE;
        TransactionsService transactionService = new TransactionsService();
        PaymentService paymentService = new PaymentService();
        PaymentVO debtorObject = null;
        Map<String, Type> paymentMap = new HashMap<>();
        List<TransactionVO> transactionVOS = null;
        try {
            Files.createFile(Paths.get(Variables.TRANSACTION_FILE_PATH));
        } catch (FileAlreadyExistsException e) {
            transactionVOS = transactionService.readTransactionFile(Variables.TRANSACTION_FILE_PATH);
        }
        List<PaymentVO> paymentVOS = paymentService.readPaymentFile(Variables.PAYMENT_FILE_PATH, Variables.SEPARATOR);
        if (paymentVOS.size() < Variables.THREAD_POOL_SIZE)
            threadSize = paymentVOS.size();
        for (PaymentVO paymentVO : paymentVOS) {
            paymentMap.put(paymentVO.getDepositNumber(), paymentVO.getType());
            if (paymentVO.getType().equals(Type.DEBTOR))
                debtorObject = paymentVO;
        }
        int subListSize = paymentVOS.size() / threadSize;
        int x = 0;
        validationCheck(transactionVOS);
        ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
        for (int i = 0; i < threadSize; i++) {
            if (i != threadSize - 1)
                executorService.submit(new TransferCallable(paymentVOS.subList(x, x + subListSize), debtorObject, transactionVOS, paymentMap));
            else
                executorService.submit(new TransferCallable(paymentVOS.subList(x, (x + subListSize) + paymentVOS.size() % threadSize), debtorObject, transactionVOS, paymentMap));
            x += subListSize;
        }
        executorService.shutdown();
        log.info("payment was successfully done");
    }
}
