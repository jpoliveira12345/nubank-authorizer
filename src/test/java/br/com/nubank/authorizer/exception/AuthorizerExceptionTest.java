package br.com.nubank.authorizer.exception;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AuthorizerExceptionTest {

    @Test(expected = AuthorizerException.class)
    public void constructorTest(){
        throw new AuthorizerException("message", new Exception());
    }

    @Test
    public void getMessageTest(){
        var msg = "message";
        var ex = new AuthorizerException(msg, new Exception());
        assertEquals(ex.getMessage(), msg);
    }

    @Test
    public void getCauseTest(){
        var msg = "message";
        var cause = new Exception();
        var ex = new AuthorizerException(msg, cause);
        assertEquals(cause, ex.getCause());
    }

//    public synchronized Throwable getCause() {
//        return super.getCause();
//    }

}
