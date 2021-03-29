package br.com.nubank.authorizer.exception;


/**
 * Application exception.
 */
public class AuthorizerException extends RuntimeException{

    public AuthorizerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizerException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

}
