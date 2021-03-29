package br.com.nubank.authorizer.model.request;

import br.com.nubank.authorizer.model.dto.Account;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Request object to register accounts.
 */
@Value
@Getter
@Builder
@Jacksonized
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class AccountRequest {

    Account account;

}