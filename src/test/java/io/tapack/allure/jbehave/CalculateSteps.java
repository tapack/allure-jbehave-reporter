package io.tapack.allure.jbehave;

import org.jbehave.core.annotations.*;

import static org.junit.Assert.assertEquals;

public class CalculateSteps {

    private int firstDigit;
    private int secondDigit;
    private int sum;

    @Given("the first number $digit")
    @Alias("the first number <first>")
    public void theFirstNumber(@Named("first") int digit) {
        firstDigit = digit;
    }

    @Given("the second number $digit")
    @Alias("the second number <second>")
    public void theSecondNumber(@Named("second") int digit) {
        secondDigit = digit;
    }

    @When("I add them together")
    public void iAddThemTogether() {
        sum = firstDigit + secondDigit;
    }

    @Then("the sum is equal to $result")
    @Alias("the sum is equal to <result>")
    public void theSumIsEqualTo(@Named("result") int result) {
        assertEquals(result, sum);
    }

    @Given("the wrong number $digit")
    public void theWrongNumber(int digit) throws Throwable {
        Object o = digit;
        String fail = (String) o;
    }
}
