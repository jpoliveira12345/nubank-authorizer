package br.com.nubank.authorizer;

import br.com.nubank.authorizer.exception.AuthorizerException;
import br.com.nubank.authorizer.model.request.AccountRequest;
import br.com.nubank.authorizer.model.request.TransactionRequest;
import br.com.nubank.authorizer.service.impl.AccountServiceImpl;
import br.com.nubank.authorizer.service.impl.TransactionServiceImpl;
import br.com.nubank.authorizer.utils.JsonUtils;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Entrypoint of the authorizer.
 */
public class Authorizer {

	/**
	 * Entrypoint method
	 */
	public static void main(String[] args) {
		try {
			var aut = new Authorizer();
			var input = System.in;
			aut.processInput(aut, input);
		} catch (Exception ex){
			var authEx = new AuthorizerException(ex.getMessage(), ex.getCause());
			System.err.println(authEx.getMessage());
		}
	}

	/**
	 * Process each line of a input stream.
	 */
	@SneakyThrows
	public void processInput(Authorizer aut, InputStream inputStream) {
		if(Objects.isNull(inputStream)){
			throw new AuthorizerException("Missing arguments!!!");
		}
		BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
		var line = input.readLine();

		while (line != null){
			aut.processAccount(line);
			aut.processTransaction(line);
			line = input.readLine();
		}
		input.close();
	}

	/**
	 * Process a JSON of an account.
	 */
	public String processAccount(String json) {
		var accountService = new AccountServiceImpl();
		var accountRequest = JsonUtils.toObject(json, AccountRequest.class);
		if (accountRequest != null) {
			var response = accountService.processAccount(accountRequest.getAccount());
			var responseJson = JsonUtils.toJson(response);
			System.out.println(responseJson);
			return responseJson;
		}
		return "";
	}

	/**
	 * Process a JSON of a transaction.
	 */
	public String processTransaction(String json) {
		var transactionService = new TransactionServiceImpl();
		var transactionRequest = JsonUtils.toObject(json, TransactionRequest.class);
		if (transactionRequest != null) {
			var response = transactionService.processTransaction(transactionRequest.getTransaction());
			var responseJson = JsonUtils.toJson(response);
			System.out.println(responseJson);
			return responseJson;
		}
		return "";
	}

}
