package br.com.nubank.authorizer.repository;

import br.com.nubank.authorizer.model.dto.Transaction;

import java.util.List;

/**
 * Interface to deal with database transactions with abstractions.
 */
public interface TransactionRepository {

    boolean addTransaction(final Transaction t);

    List<Transaction> findAllTransactions();

    void deleteAll();

}
