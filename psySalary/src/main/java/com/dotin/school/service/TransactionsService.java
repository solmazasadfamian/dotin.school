package main.java.com.dotin.school.service;

import main.java.com.dotin.school.data.TransactionVO;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TransactionsService {
    private final static Logger log = Logger.getLogger(TransactionsService.class.getName());

    public List<TransactionVO> readTransactionFile(String path) {
        List<TransactionVO> transactionVOS = new ArrayList<>();
        String[] transactionData;
        Path transactionPath = Paths.get(path);
        try (BufferedReader reader = Files.newBufferedReader(transactionPath)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                transactionData = currentLine.split("\t");
                TransactionVO transactionVO = new TransactionVO();
                transactionVO.setDebtorDepositNumber(transactionData[0]);
                transactionVO.setCreditorDepositNumber(transactionData[1]);
                transactionVO.setAmount(new BigDecimal(transactionData[2]));
                transactionVOS.add(transactionVO);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return transactionVOS;
    }

    public void writeTransactionFile(String path, TransactionVO transactionVO) {
        Path transactionPath = Paths.get(path);
        try {
            Files.write(transactionPath, transactionVO.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
