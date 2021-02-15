package com.dotin.school.service;

import com.dotin.school.constants.Errors;
import com.dotin.school.constants.Variables;
import com.dotin.school.data.BalanceVO;
import com.dotin.school.data.PaymentVO;
import com.dotin.school.enumeration.Type;
import com.dotin.school.exceptions.DepositNotFoundException;
import com.dotin.school.exceptions.FileFormatException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BalanceService {
    private final static Logger log = Logger.getLogger(BalanceService.class.getName());
    private final static BalanceService instance = new BalanceService();

    private BalanceService() {
    }

    public static BalanceService getInstance() {
        return instance;
    }

    public void initBalanceFile() throws FileFormatException, IOException {
        Files.createFile(Paths.get(Variables.BALANCE_FILE_PATH));
        PaymentService paymentService = new PaymentService();
        List<PaymentVO> paymentVOList = paymentService.readPaymentFile(Variables.PAYMENT_FILE_PATH, Variables.SEPARATOR);
        Random random = new Random();
        int counter = 1;
        BalanceVO debtorBalanceVO = new BalanceVO();
        debtorBalanceVO.setDepositNumber(Variables.DEBTOR_DEPOSIT_NO);
        debtorBalanceVO.setAmount(BigDecimal.valueOf(random.nextInt(20000000) + 40000000));
        writeBalanceFile(Variables.BALANCE_FILE_PATH, debtorBalanceVO);
        int randNo = Math.abs(random.nextInt(100) + 1);
        while (counter < paymentVOList.size() + randNo) {
            BalanceVO balanceVO = new BalanceVO();
            balanceVO.setDepositNumber(Variables.CREDITOR_DEPOSIT_NO + counter);
            balanceVO.setAmount(BigDecimal.valueOf(random.nextInt(10000000) + 500000));
            writeBalanceFile(Variables.BALANCE_FILE_PATH, balanceVO);
            counter++;
        }
        log.info("balance file was created");
    }

    public synchronized List<BalanceVO> readBalanceFile(String path, String separator) throws FileFormatException {
        List<BalanceVO> balanceVOS = new ArrayList<>();
        String[] balanceData;
        Path balancePath = Paths.get(path);
        try (BufferedReader reader = Files.newBufferedReader(balancePath)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                balanceData = currentLine.split(separator);
                checkValidBalanceFileFormat(balanceData);
                BalanceVO balanceVO = new BalanceVO(balanceData[0], new BigDecimal(balanceData[1]));
                balanceVOS.add(balanceVO);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            System.exit(0);
        }
        log.info("balance file was read");
        return balanceVOS;
    }

    public synchronized void writeBalanceFile(String path, BalanceVO balanceVO) {
        Path balancePath = Paths.get(path);
        try {
            Files.write(balancePath, balanceVO.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public synchronized void updateBalanceFile(Map<String, Type> paymentMap, String balancePath, String separator, PaymentVO paymentVO) throws FileFormatException, IOException, DepositNotFoundException {
        List<BalanceVO> balanceVOS = readBalanceFile(balancePath, separator);
        if (!balanceVOS.contains(new BalanceVO(paymentVO.getDepositNumber())))
            throw new DepositNotFoundException(paymentVO.getDepositNumber());
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(balancePath));
        writer.flush();
        for (BalanceVO balanceVO : balanceVOS) {
            if (balanceVO.getDepositNumber().equals(paymentVO.getDepositNumber())
                    && paymentMap.get(balanceVO.getDepositNumber()).equals(Type.CREDITOR)) {
                balanceVO.setAmount(balanceVO.getAmount().add(paymentVO.getAmount()));
            }
            if (paymentMap.containsKey(balanceVO.getDepositNumber()) && paymentMap.get(balanceVO.getDepositNumber()).equals(Type.DEBTOR)) {
                balanceVO.setAmount(balanceVO.getAmount().subtract(paymentVO.getAmount()));
            }
            writeBalanceFile(balancePath, balanceVO);
        }
    }

    private void checkValidBalanceFileFormat(String[] data) throws FileFormatException {
        if (data.length != 2) {
            throw new FileFormatException(Variables.ERROR_CODE_BALANCE);
        }
        try {
            BigDecimal balance = new BigDecimal(data[1]);
            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                throw new FileFormatException(Errors.fileFormatECode, Errors.fileFormatEMessage1 + data[1] + Errors.fileFormatEMessage2);
            }
        } catch (NumberFormatException e) {
            throw new FileFormatException(Errors.fileFormatECode, Errors.fileFormatEMessage1 + data[1] + Errors.fileFormatEMessage2);
        }
    }
}
