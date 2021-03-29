package br.com.nubank.authorizer.model.dto;

import br.com.nubank.authorizer.config.LocalDateTimeDeserializer;
import br.com.nubank.authorizer.config.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

/**
 * Transaction model
 */
@Value
@Getter
@Builder
@Jacksonized
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
public class Transaction {

    String merchant;

    Integer amount;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime time;

}
