package br.com.nubank.authorizer.repository.impl;

import br.com.nubank.authorizer.model.dto.Transaction;
import br.com.nubank.authorizer.repository.MemoryDatabase;
import br.com.nubank.authorizer.repository.TransactionRepository;

import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository {

    @Override
    public boolean addTransaction(final Transaction t) {
        var db = MemoryDatabase.getInstance();
        var transactions = db.getTransactions();
        return transactions.add(t);
    }

    @Override
    public List<Transaction> findAllTransactions() {
        var db = MemoryDatabase.getInstance();
        return db.getTransactions();
    }

    @Override
    public void deleteAll() {
        var db = MemoryDatabase.getInstance();
        var transactions = db.getTransactions();
        transactions.clear();
    }
}
