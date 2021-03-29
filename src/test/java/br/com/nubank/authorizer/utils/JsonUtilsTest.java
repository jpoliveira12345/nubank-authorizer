package br.com.nubank.authorizer.utils;

import br.com.nubank.authorizer.model.dto.Account;
import br.com.nubank.authorizer.model.request.AccountRequest;
import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JsonUtilsTest {

    @Test
    public void toObjectSuccess() {
        final var json = "{\"account\": {\"active-card\": true, \"available-limit\": 100}}";
        final var account = Account.builder()
                .activeCard(true)
                .availableLimit(100)
                .build();
        final var accountReq  = AccountRequest.builder().account(account).build();
        assertEquals(accountReq, JsonUtils.toObject(json, AccountRequest.class));
    }

    @Test
    public void  toObjectFail() {
        final var wrongJson = "potato";
        assertNull(JsonUtils.toObject(wrongJson, AccountRequest.class));
    }

    @Test
    public void toJsonSuccess() {
        final var json = "{\"active-card\":true,\"available-limit\":100}";
        var account = Account.builder()
                .activeCard(true)
                .availableLimit(100)
                .build();
        assertEquals(json, JsonUtils.toJson(account));
    }

    @Test
    public void  toJsonFail() {
        var result = JsonUtils.toJson(ZonedDateTime.now());
        assertNull(result);
    }

}
