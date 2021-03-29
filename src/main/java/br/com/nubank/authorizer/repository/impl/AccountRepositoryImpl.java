package br.com.nubank.authorizer.repository.impl;

import br.com.nubank.authorizer.model.dto.Account;
import br.com.nubank.authorizer.repository.AccountRepository;
import br.com.nubank.authorizer.repository.MemoryDatabase;

public class AccountRepositoryImpl implements AccountRepository {


    @Override
    public int count() {
        var db = MemoryDatabase.getInstance();
        return db.getAccounts().size();
    }

    @Override
    public boolean addAccount(final Account a) {
        var db = MemoryDatabase.getInstance();
        return db.getAccounts().add(a);
    }

    @Override
    public Account findFirst() {
        var db = MemoryDatabase.getInstance();
        var accounts = db.getAccounts();
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    @Override
    public void deleteAll() {
        var db = MemoryDatabase.getInstance();
        var accounts = db.getAccounts();
        accounts.clear();
    }
}
