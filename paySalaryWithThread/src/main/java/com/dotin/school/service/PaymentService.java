package com.dotin.school.service;

import com.dotin.school.constants.Errors;
import com.dotin.school.constants.Variables;
import com.dotin.school.data.PaymentVO;
import com.dotin.school.enumeration.Type;
import com.dotin.school.exceptions.FileFormatException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PaymentService {
    private final static Logger log = Logger.getLogger(PaymentService.class.getName());

    public void initPaymentFile() throws IOException {
        Files.createFile(Paths.get(Variables.PAYMENT_FILE_PATH));
        BigDecimal creditorsAmount = BigDecimal.ZERO;
        Random random = new Random();
        int counter = 1;
        PaymentVO debtor = new PaymentVO(Type.DEBTOR, Variables.DEBTOR_DEPOSIT_NO, BigDecimal.valueOf(random.nextInt(40000000) + 10000000));
        writePaymentFile(Variables.PAYMENT_FILE_PATH, debtor);
        while (creditorsAmount.compareTo(debtor.getAmount()) < 0) {
            PaymentVO creditor = new PaymentVO();
            creditor.setType(Type.CREDITOR);
            creditor.setDepositNumber(Variables.CREDITOR_DEPOSIT_NO + (counter));
            creditor.setAmount(BigDecimal.valueOf(random.nextInt(3000000) + 1000000));
            if (creditor.getAmount().add(creditorsAmount).compareTo(debtor.getAmount()) < 0) {
                creditorsAmount = creditorsAmount.add(creditor.getAmount());
                writePaymentFile(Variables.PAYMENT_FILE_PATH, creditor);
                counter++;
            } else
                break;
        }
        PaymentVO finalCreditor = new PaymentVO();
        finalCreditor.setType(Type.CREDITOR);
        finalCreditor.setDepositNumber(Variables.CREDITOR_DEPOSIT_NO + (counter));
        finalCreditor.setAmount(debtor.getAmount().subtract(creditorsAmount));
        writePaymentFile(Variables.PAYMENT_FILE_PATH, finalCreditor);
        log.info("payment file was created ");
    }

    public List<PaymentVO> readPaymentFile(String path, String separator) throws FileFormatException {
        List<PaymentVO> paymentVOS = new ArrayList<>();
        String[] paymentData;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                paymentData = currentLine.split(separator);
                checkValidPaymentFileFormat(paymentData);
                PaymentVO paymentVO = new PaymentVO();
                if (paymentData[0].equalsIgnoreCase(Type.DEBTOR.label))
                    paymentVO.setType(Type.DEBTOR);
                else if (paymentData[0].equalsIgnoreCase(Type.CREDITOR.label))
                    paymentVO.setType(Type.CREDITOR);
                paymentVO.setDepositNumber(paymentData[1]);
                paymentVO.setAmount(new BigDecimal(paymentData[2]));
                paymentVOS.add(paymentVO);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("payment file was read");
        return paymentVOS;
    }

    public void writePaymentFile(String paymentPath, PaymentVO paymentVO) throws IOException {
        Files.write(Paths.get(paymentPath), paymentVO.toString().getBytes(), StandardOpenOption.APPEND);
    }

    private void checkValidPaymentFileFormat(String[] data) throws FileFormatException {
        if (!((data.length == 3 || data.length == 0)
                && (data[0].equalsIgnoreCase(Type.DEBTOR.label) || data[0].equalsIgnoreCase(Type.CREDITOR.label)))) {
            throw new FileFormatException(Variables.ERROR_CODE_PAYMENT);
        }
        try {
            Long balance = Long.valueOf(data[2]);
            if (balance < 0) {
                throw new FileFormatException(Errors.fileFormatECode, Errors.fileFormatEMessage1 + data[2] + Errors.fileFormatEMessage2);
            }
        } catch (NumberFormatException e) {
            throw new FileFormatException(Errors.fileFormatECode, Errors.fileFormatEMessage1 + data[2] + Errors.fileFormatEMessage2);
        }
    }
}
