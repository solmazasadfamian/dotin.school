package main.java.com.dotin.school;

import main.java.com.dotin.school.constants.Variables;
import main.java.com.dotin.school.exceptions.DepositNotFoundException;
import main.java.com.dotin.school.exceptions.FileFormatException;
import main.java.com.dotin.school.exceptions.NotEnoughBalanceException;
import main.java.com.dotin.school.service.BalanceService;
import main.java.com.dotin.school.service.PaymentService;
import main.java.com.dotin.school.service.TransferService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Run {
    static Logger log = Logger.getLogger(Run.class.getName());

    public static void main(String[] args) {
        log.info("application is started ...");
        PaymentService paymentService = new PaymentService();
        BalanceService balanceService = new BalanceService();
        TransferService transferService = new TransferService();
        try {
            Files.createFile(Paths.get(Variables.PAYMENT_FILE_PATH));
            paymentService.initPaymentFile(Variables.PAYMENT_FILE_PATH);
            Files.createFile(Paths.get(Variables.BALANCE_FILE_PATH));
            balanceService.initBalanceFile(Variables.BALANCE_FILE_PATH, Variables.PAYMENT_FILE_PATH, Variables.SEPARATOR);
            transferService.paySalary(Variables.PAYMENT_FILE_PATH, Variables.BALANCE_FILE_PATH, Variables.TRANSACTION_FILE_PATH, Variables.SEPARATOR);
        } catch (FileAlreadyExistsException e) {
            log.error(e.getMessage() + " Already exists");
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

