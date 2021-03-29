package br.com.nubank.authorizer.service.impl;

import br.com.nubank.authorizer.constants.ViolationConstant;
import br.com.nubank.authorizer.model.dto.Account;
import br.com.nubank.authorizer.model.response.ResponseDto;
import br.com.nubank.authorizer.repository.AccountRepository;
import br.com.nubank.authorizer.repository.impl.AccountRepositoryImpl;
import br.com.nubank.authorizer.service.AccountService;

import java.util.ArrayList;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl() {
        accountRepository = new AccountRepositoryImpl();
    }

    @Override
    public ResponseDto processAccount(final Account c) {
        final var violations = new ArrayList<String>();
        if( accountRepository.count() > 0 ){
            violations.add(ViolationConstant.ACCOUNT_ALREADY_INITIALIZED_MESSAGE);
        }

        if(violations.isEmpty()) {
            accountRepository.addAccount(c);
        }
        return ResponseDto.builder()
                .account(accountRepository.findFirst())
                .violations(violations)
                .build();
    }

}
