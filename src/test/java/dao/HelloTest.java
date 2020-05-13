package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.lang.reflect.Proxy;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class HelloTest {
    @Test
    public void simpleProxy() {
//        Hello hello = new HelloUppercase(new HelloTarget());
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
        
        assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
        assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
        assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
    }
}
