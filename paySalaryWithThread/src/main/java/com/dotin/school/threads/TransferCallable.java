package com.dotin.school.threads;

import com.dotin.school.constants.Variables;
import com.dotin.school.data.PaymentVO;
import com.dotin.school.data.TransactionVO;
import com.dotin.school.enumeration.Type;
import com.dotin.school.exceptions.DepositNotFoundException;
import com.dotin.school.exceptions.FileFormatException;
import com.dotin.school.service.BalanceService;
import com.dotin.school.service.TransactionsService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class TransferCallable implements Callable<List<TransactionVO>> {
    private final static Logger log = Logger.getLogger(TransferCallable.class.getName());
    private final List<PaymentVO> subList;
    private final PaymentVO debtorObject;
    private final List<TransactionVO> transactionVOList;
    private final Map<String, Type> paymentMap;

    public TransferCallable(List<PaymentVO> subList, PaymentVO debtorObject, List<TransactionVO> transactionVOList, Map<String, Type> paymentMap) {
        this.subList = subList;
        this.debtorObject = debtorObject;
        this.transactionVOList = transactionVOList;
        this.paymentMap = paymentMap;
    }

    @Override
    public List<TransactionVO> call() throws DepositNotFoundException, FileFormatException, IOException {
        BalanceService balanceService = BalanceService.getInstance();
        TransactionsService transactionsService = new TransactionsService();
        for (PaymentVO paymentVO : subList) {
            if (paymentVO.getType().equals(Type.DEBTOR))
                continue;
            TransactionVO transactionVO = new TransactionVO(debtorObject.getDepositNumber(), paymentVO.getDepositNumber(), paymentVO.getAmount());
            if (transactionVOList == null || !transactionVOList.contains(transactionVO)) {
                log.info("payment : " + paymentVO + " is started");
                balanceService.updateBalanceFile(paymentMap, Variables.BALANCE_FILE_PATH, Variables.SEPARATOR, paymentVO);
                transactionsService.writeTransactionFile(Variables.TRANSACTION_FILE_PATH, transactionVO);
                log.info("payment : " + paymentVO + " is done");
            }
        }
        return null;
    }







    /*private final List<TransactionVO> transactionVOList;
    private final PaymentInfo paymentInfo;
    private final PaymentVO paymentVO;
    private final BalanceService balanceService;
    private final TransactionsService transactionsService;

    public TransferCallable(List<TransactionVO> transactionVOList, PaymentInfo paymentInfo, PaymentVO paymentVO, BalanceService balanceService, TransactionsService transactionsService) {
        this.transactionVOList = transactionVOList;
        this.paymentInfo = paymentInfo;
        this.paymentVO = paymentVO;
        this.balanceService = balanceService;
        this.transactionsService = transactionsService;
    }

    @Override
    public List<TransactionVO> call() throws DepositNotFoundException, FileFormatException, IOException {
        TransactionVO transactionVO = new TransactionVO(paymentInfo.getDebtorObject().getDepositNumber(), paymentVO.getDepositNumber(), paymentVO.getAmount());
        if (transactionVOList == null || !transactionVOList.contains(transactionVO)) {
            log.info("payment : " + paymentVO + " is started");
            balanceService.updateBalanceFile(paymentInfo.getPaymentMap(), Variables.BALANCE_FILE_PATH, Variables.SEPARATOR, paymentVO);
            transactionsService.writeTransactionFile(Variables.TRANSACTION_FILE_PATH, transactionVO);
            log.info("payment : " + paymentVO + " is done");
        }
        return null;
    }*/
}
