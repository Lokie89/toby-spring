package learningtest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest {

    Calculator calculator;
    String numFilepath;

    @Before
    public void setUp(){
        calculator = new Calculator();
        numFilepath = getClass().getResource("numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        int sum = calculator.calcSum(numFilepath);
        assertThat(sum, is(10));
    }

    @Test
    public void multiplyOfNumbers() throws IOException{
        int multiply = calculator.calcMultiply(numFilepath);
        assertThat(multiply, is(24));
    }

    @Test
    public void concatenateOfNumbers() throws IOException{
        String concatenate = calculator.concatenate(numFilepath);
        assertThat(concatenate,is("1234"));
    }
}
