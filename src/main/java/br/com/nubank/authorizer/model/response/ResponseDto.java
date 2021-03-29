package br.com.nubank.authorizer.model.response;

import br.com.nubank.authorizer.model.dto.Account;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Response object of application
 */
@Value
@Getter
@Builder
@Jacksonized
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class ResponseDto {

    Account account;

    List<String> violations;

}
