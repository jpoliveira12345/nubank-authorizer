package br.com.nubank.authorizer.service;

import br.com.nubank.authorizer.model.dto.Account;
import br.com.nubank.authorizer.model.response.ResponseDto;

/**
 * Interface to deal with accounts.
 */
public interface AccountService {
    ResponseDto processAccount(Account c);

}
