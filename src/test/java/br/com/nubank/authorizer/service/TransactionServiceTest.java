package br.com.nubank.authorizer.service;

import br.com.nubank.authorizer.constants.ViolationConstant;
import br.com.nubank.authorizer.model.dto.Account;
import br.com.nubank.authorizer.model.dto.Transaction;
import br.com.nubank.authorizer.model.response.ResponseDto;
import br.com.nubank.authorizer.repository.AccountRepository;
import br.com.nubank.authorizer.repository.TransactionRepository;
import br.com.nubank.authorizer.repository.impl.AccountRepositoryImpl;
import br.com.nubank.authorizer.repository.impl.TransactionRepositoryImpl;
import br.com.nubank.authorizer.service.impl.AccountServiceImpl;
import br.com.nubank.authorizer.service.impl.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;
    private TransactionService transactionService;
    private TransactionRepository transactionRepository;

    @Before
    public void init(){
        accountRepository = new AccountRepositoryImpl();
        transactionService = new TransactionServiceImpl();
        accountService = new AccountServiceImpl();
        transactionRepository = new TransactionRepositoryImpl();
    }

    private void initTest(){
        accountRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    @Test
    public void processTransactionSuccess(){
        initTest();
        var account = Account.builder()
                .availableLimit(100)
                .activeCard(true)
                .build();
        accountService.processAccount(account);

        var transaction = Transaction.builder()
                .amount(100)
                .time(LocalDateTime.now())
                .merchant("Bob's")
                .build();

        var response = transactionService.processTransaction(transaction);
        var expectedAccount = Account.builder()
                .activeCard(account.isActiveCard())
                .availableLimit(0)
                .build();
        var expected = ResponseDto.builder()
                .account(expectedAccount)
                .violations(List.of())
                .build();
        assertEquals(expected, response);
    }

    @Test
    public void processTransactionFailCardNotActive(){
        initTest();
        var account = Account.builder()
                .availableLimit(100)
                .activeCard(false)
                .build();
        accountService.processAccount(account);

        var transaction = Transaction.builder()
                .amount(100)
                .time(LocalDateTime.now())
                .merchant("Bob's")
                .build();

        var response = transactionService.processTransaction(transaction);
        var expected = ResponseDto.builder()
                .account(accountRepository.findFirst())
                .violations(List.of(ViolationConstant.CARD_NOT_ACTIVE_MESSAGE))
                .build();
        assertEquals(expected, response);
    }


    @Test
    public void processTransactionFailInsufficientLimit(){
        initTest();
        var account = Account.builder()
                .availableLimit(100)
                .activeCard(true)
                .build();
        accountService.processAccount(account);

        var transaction = Transaction.builder()
                .amount(1000)
                .time(LocalDateTime.now())
                .merchant("Bob's")
                .build();

        var response = transactionService.processTransaction(transaction);
        var expected = ResponseDto.builder()
                .account(accountRepository.findFirst())
                .violations(List.of(ViolationConstant.INSUFFICIENT_LIMIT_MESSAGE))
                .build();
        assertEquals(expected, response);
    }


    @Test
    public void processTransactionFailDoubleTransaction(){
        initTest();
        var account = Account.builder()
                .availableLimit(100)
                .activeCard(true)
                .build();
        accountService.processAccount(account);

        var transaction = Transaction.builder()
                .amount(50)
                .time(LocalDateTime.now())
                .merchant("Bob's")
                .build();

        transactionService.processTransaction(transaction);
        var response = transactionService.processTransaction(transaction);

        var expected = ResponseDto.builder()
                .account(accountRepository.findFirst())
                .violations(List.of(ViolationConstant.DOUBLED_TRANSACTION_MESSAGE))
                .build();
        assertEquals(expected, response);
    }

    @Test
    public void processTransactionFailHighFrequencyTransaction(){
        initTest();
        var now = LocalDateTime.now();
        var account = Account.builder()
                .availableLimit(100)
                .activeCard(true)
                .build();
        accountService.processAccount(account);

        var response = ResponseDto.builder().build();
        for(var i = 0 ; i < 5 ; i++) {
            var t = Transaction.builder()
                    .amount(10 + i)
                    .time(now)
                    .merchant("Bob's")
                    .build();
            response = transactionService.processTransaction(t);
            now = now.plusSeconds(40);
        }

        var expected = ResponseDto.builder()
                .account(accountRepository.findFirst())
                .violations(List.of(ViolationConstant.HIGH_FREQUENCY_SMALL_INTERVAL_MESSAGE))
                .build();
        assertEquals(expected, response);
    }

}
