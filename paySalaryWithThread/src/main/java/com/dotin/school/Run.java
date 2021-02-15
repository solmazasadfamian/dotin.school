package com.dotin.school;

import com.dotin.school.constants.Errors;
import com.dotin.school.constants.Variables;
import com.dotin.school.exceptions.DepositNotFoundException;
import com.dotin.school.exceptions.FileFormatException;
import com.dotin.school.exceptions.NotEnoughBalanceException;
import com.dotin.school.service.BalanceService;
import com.dotin.school.service.PaymentService;
import com.dotin.school.service.TransferService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class Run {
    static Logger log = Logger.getLogger(Run.class.getName());

    public static void main(String[] args) {
        log.info("application is started ...");
        PaymentService paymentService = new PaymentService();
        BalanceService balanceService = BalanceService.getInstance();
        TransferService transferService = new TransferService();
        try {
            paymentService.initPaymentFile();
            balanceService.initBalanceFile();
            transferService.paySalary();
        } catch (FileAlreadyExistsException e) {
            log.error(e.getMessage() + Errors.fileExist);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        } catch (FileFormatException e) {
            log.error(e.getMessage());
        } catch (NotEnoughBalanceException e) {
            log.error(e.getErrorMessage());
        } catch (DepositNotFoundException e) {
            log.error(e.getErrorMessage());
        }
    }
}

