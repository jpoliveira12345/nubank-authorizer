package br.com.nubank.authorizer.service.impl;

import br.com.nubank.authorizer.constants.ViolationConstant;
import br.com.nubank.authorizer.model.dto.Account;
import br.com.nubank.authorizer.model.dto.Transaction;
import br.com.nubank.authorizer.model.response.ResponseDto;
import br.com.nubank.authorizer.repository.AccountRepository;
import br.com.nubank.authorizer.repository.TransactionRepository;
import br.com.nubank.authorizer.repository.impl.AccountRepositoryImpl;
import br.com.nubank.authorizer.repository.impl.TransactionRepositoryImpl;
import br.com.nubank.authorizer.service.TransactionService;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl() {
        transactionRepository = new TransactionRepositoryImpl();
        accountRepository = new AccountRepositoryImpl();
    }

    @Override
    public ResponseDto processTransaction(final Transaction t) {
        final var violations = new HashSet<String>();
        final var accountDb = accountRepository.findFirst();
        final var transactionsDb = transactionRepository.findAllTransactions();

        if(accountRepository.count() < 1){
            violations.add(ViolationConstant.ACCOUNT_NOT_INITIALIZED_MESSAGE);
            return getDefaultResponse(violations);
        }

        if(!accountDb.isActiveCard()){
            violations.add(ViolationConstant.CARD_NOT_ACTIVE_MESSAGE);
        }

        if(t.getAmount() > accountDb.getAvailableLimit() ){
            violations.add(ViolationConstant.INSUFFICIENT_LIMIT_MESSAGE);
        }

        violations.addAll(checkFrequency(t, transactionsDb));

        int balance = accountDb.getAvailableLimit() - t.getAmount();
        var account = Account.builder()
                .activeCard(accountDb.isActiveCard())
                .availableLimit(balance)
                .build();

        if(violations.isEmpty()) {
            accountRepository.deleteAll();
            accountRepository.addAccount(account);
            transactionRepository.addTransaction(t);
        }
        return getDefaultResponse(violations);
    }

    private Set<String> checkFrequency(Transaction t, List<Transaction> transactions) {
        final var violations = new HashSet<String>();
        var nTransactionInTwoMinutes = 1;
        for(var transaction : transactions){
            var transactionTime = t.getTime();
            long interval = Math.abs(transactionTime.until( transaction.getTime(), ChronoUnit.MINUTES ));
            if( interval <= 2L ){
                nTransactionInTwoMinutes++;
                if(Objects.equals(t.getMerchant(), transaction.getMerchant())
                        && Objects.equals(t.getAmount(), transaction.getAmount())){
                    violations.add(ViolationConstant.DOUBLED_TRANSACTION_MESSAGE);
                }
            }
            if(nTransactionInTwoMinutes > 3){
                violations.add(ViolationConstant.HIGH_FREQUENCY_SMALL_INTERVAL_MESSAGE);
            }
        }
        return violations;
    }

    private ResponseDto getDefaultResponse(Set<String> violations){
        return ResponseDto.builder()
                .account(accountRepository.findFirst())
                .violations(new ArrayList<>(violations))
                .build();
    }

}
