package com.ddk.karthi.junit;

import com.ddk.karthi.apps.AddTwoFractions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

@RunWith(Parameterized.class)
public class AddTwoFractionsTest {
    private String f1Str;
    private String f2Str;
    private String result;
    private Class exceptionClass;

    @Parameterized.Parameters(name = "{index}: Fraction {0} + Fraction {1} should result in {2}")
    public static Object[][] dataTwoFractions() {
        Object[][] data = new Object[][] {
                { "1/4", "3/4", "1", null },
                { "3/2", "6/4", "3", null },
                { "6/4", "3/2", "3", null },
                { "2/7", "3/14", "1/2", null },
                { "1/13", "1/3", "16/39", null },
                { "2/7", "4/14", "4/7", null },
                { "7/2", "4/7", "57/14", null },
                { "4/7", "8/14", "8/7", null },
                { "2", "1/4", "2 is not a valid fraction", Exception.class },
                { "1/4", "3", "3 is not a valid fraction", Exception.class },
                { "5/0", "5/0", "/ by zero", java.lang.ArithmeticException.class }
        };
        return data;
    }

    public AddTwoFractionsTest(String f1Str, String f2Str, String result, Class exceptionClass) {
        this.f1Str = f1Str;
        this.f2Str = f2Str;
        this.result = result;
        this.exceptionClass = exceptionClass;
    }

    @Test
    public void testAddTwoFractions() throws Exception {
        AddTwoFractions addTwoFractions = new AddTwoFractions();

        Field f1Field = AddTwoFractions.class.getDeclaredField("f1");
        f1Field.setAccessible(true);
        f1Field.set(addTwoFractions, this.f1Str);

        Field f2Field = AddTwoFractions.class.getDeclaredField("f2");
        f2Field.setAccessible(true);
        f2Field.set(addTwoFractions, this.f2Str);

        try {
            addTwoFractions.add();
            assertEquals(this.result, addTwoFractions.getSum());
        } catch ( Exception e ) {
            if ( this.exceptionClass != null ) {
                assertEquals(this.exceptionClass, e.getClass());
                assertEquals(result, e.getLocalizedMessage());
            }
        }

    }
}
