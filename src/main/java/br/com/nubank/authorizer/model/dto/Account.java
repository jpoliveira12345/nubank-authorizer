package br.com.nubank.authorizer.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Account model.
 */
@Value
@Getter
@Builder
@Jacksonized
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class Account {

    boolean activeCard;

    Integer availableLimit;

}
