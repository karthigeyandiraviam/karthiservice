package com.ddk.karthi.junit;

import com.ddk.karthi.apps.CreditCard;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

@RunWith(Parameterized.class)
public class CreditCardTest {
    private String creditCardStr;
    private Boolean result;
    private Class exceptionClass;

    @Parameterized.Parameters(name = "{index}: Validation for CreditCard {0} should return {1}")
    public static Object[][] dataGradeSortTest() {
        Object[][] data = new Object[][] {
                {"374245455400126", true, null },
                {"6011000991300009", true, null },
                {"60115564485789458", false, null },
                {"5425233430109903", true, null },
                {"4263982640269299", true, null },
                {"3263982640269299", false, null },
                {"174245455400126", false, null },
                {"100", false, null },
                {"4111111111111112", false, null }
        };
        return data;
    }

    public CreditCardTest(String creditCardStr, Boolean result, Class exceptionClass) {
        this.creditCardStr = creditCardStr;
        this.result = result;
        this.exceptionClass = exceptionClass;
    }

    @Test
    public void testCreditCardValidation() throws Exception {
        CreditCard creditCard = new CreditCard();

        Field num1Field = CreditCard.class.getDeclaredField("creditCardStr");
        num1Field.setAccessible(true);
        num1Field.set(creditCard, this.creditCardStr);

        try {
            creditCard.validateCC();
            assertEquals(this.result, creditCard.isValidCreditCard());
        } catch ( Exception e ) {
            if ( this.exceptionClass != null ) {
                assertEquals(this.exceptionClass, e.getClass());
                assertEquals(result, e.getLocalizedMessage());
            }
        }

    }
}
