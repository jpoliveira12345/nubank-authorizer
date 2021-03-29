package br.com.nubank.authorizer.service;

import br.com.nubank.authorizer.model.dto.Transaction;
import br.com.nubank.authorizer.model.response.ResponseDto;

/**
 * Interface to deal with transactions.
 */
public interface TransactionService {
    ResponseDto processTransaction(final Transaction t);

}
