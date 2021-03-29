package br.com.nubank.authorizer.repository;

import br.com.nubank.authorizer.model.dto.Account;
import br.com.nubank.authorizer.model.dto.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstraction of a memory database that stores accounts and transactions. The Singleton Design Patter were used here
 * to grant unique database to all classes in code.
 */
public class MemoryDatabase {
    private static MemoryDatabase database;
    private static List<Account> accounts;
    private static List<Transaction> transactions;

    private  MemoryDatabase() {
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public static MemoryDatabase getInstance(){
        if(Objects.isNull(database)){
            database = new MemoryDatabase();
        }
        return database;
    }

    public synchronized List<Account> getAccounts() {
        return accounts;
    }

    public synchronized List<Transaction> getTransactions() {
        return transactions;
    }

}
