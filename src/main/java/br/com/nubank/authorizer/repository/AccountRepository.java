package br.com.nubank.authorizer.repository;

import br.com.nubank.authorizer.model.dto.Account;

/**
 * Interface to deal with database accounts with abstractions.
 */
public interface AccountRepository {
    int count();

    boolean addAccount(final Account a);

    Account findFirst();

    void deleteAll();

}
