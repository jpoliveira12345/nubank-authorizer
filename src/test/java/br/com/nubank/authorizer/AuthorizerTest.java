package br.com.nubank.authorizer;

import br.com.nubank.authorizer.constants.ViolationConstant;
import br.com.nubank.authorizer.model.dto.Account;
import br.com.nubank.authorizer.model.dto.Transaction;
import br.com.nubank.authorizer.model.request.AccountRequest;
import br.com.nubank.authorizer.model.request.TransactionRequest;
import br.com.nubank.authorizer.model.response.ResponseDto;
import br.com.nubank.authorizer.repository.impl.AccountRepositoryImpl;
import br.com.nubank.authorizer.utils.JsonUtils;
import org.junit.Test;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthorizerTest {

	@Test
	public void testTwoAccounts() {
		final var originalStream = System.in;
		InputStream fips = AuthorizerTest.class.getResourceAsStream("/test-ok");
		System.setIn(fips);
		Authorizer.main(null);
		System.setIn(originalStream);
	}

	@Test
	public void testException() {
		final var originalStream = System.in;
		System.setIn(null);
		Authorizer.main(null);
		System.setIn(originalStream);
	}

	@Test
	public void processAccountSuccess() {
		final var authorizer = new Authorizer();
		var expectedTransaction  = Transaction.builder()
				.merchant("Burger King")
				.amount(50)
				.time(LocalDateTime.now())
				.build();

		var expectedResponse = ResponseDto.builder()
				.account(null)
				.violations(List.of(ViolationConstant.ACCOUNT_NOT_INITIALIZED_MESSAGE))
				.build();
		var accountRepo = new AccountRepositoryImpl();
		accountRepo.deleteAll();
		var request = TransactionRequest.builder().transaction(expectedTransaction).build();
		var response = authorizer.processTransaction(JsonUtils.toJson(request));
		assertEquals(response, JsonUtils.toJson(expectedResponse));
	}

	@Test
	public void processTransactionFail() {
		final var authorizer = new Authorizer();
		final var wrongJson = "{test}";
		var response = authorizer.processTransaction(wrongJson);
		assertEquals(response, "");
	}

	@Test
	public void processTransactionSuccess() {
		final var authorizer = new Authorizer();
		var expectedAccount  = Account.builder()
				.activeCard(true)
				.availableLimit(100)
				.build();

		var expectedResponse = ResponseDto.builder()
				.account(expectedAccount)
				.violations(Collections.emptyList())
				.build();

		var request = AccountRequest.builder().account(expectedAccount).build();
		var response = authorizer.processAccount(JsonUtils.toJson(request));
		assertEquals(response, JsonUtils.toJson(expectedResponse));
	}

	@Test
	public void processAccountFail() {
		final var authorizer = new Authorizer();
		final var wrongJson = "{test}";
		var response = authorizer.processAccount(wrongJson);
		assertEquals(response, "");
	}

}
