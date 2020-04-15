package com.ddk.karthi.junit;

import com.ddk.karthi.apps.AddTwoLinkedList;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

@RunWith(Parameterized.class)
public class AddTwoLinkedListTest {
    private String num1Str;
    private String num2Str;
    private String result;
    private Class exceptionClass;

    @Parameterized.Parameters(name = "{index}: Number {0} + Number {1} should result in {2}")
    public static Object[][] dataTwoLinkedList() {
        Object[][] data = new Object[][] {
                { "0001", "002", "3", null },
                { "1", "999", "1000", null },
                { "0", "0", "0", null },
                { "890", "098", "988", null },
                { "111", "111", "222", null },
                { "999", "001", "1000", null },
                { "9999", "9999", "19998", null },
                { "-1", "-2", "-3", null },
                { "1", "1", "2", null },
                { "", "", "0", null },
                { "1", "", "", null },
                { "", "1", "1", null },
                { "-1", "", "-1", null },
                { "0", "10", "10", null },
                { "2147483648", "1", "2147483649", null }
        };
        return data;
    }

    public AddTwoLinkedListTest(String num1Str, String num2Str, String result, Class exceptionClass) {
        this.num1Str = num1Str;
        this.num2Str = num2Str;
        this.result = result;
        this.exceptionClass = exceptionClass;
    }

    @Test
    public void testAddTwoLinkedList() throws Exception {
        AddTwoLinkedList addTwoLinkedList = new AddTwoLinkedList();

        Field num1Field = AddTwoLinkedList.class.getDeclaredField("num1");
        num1Field.setAccessible(true);
        num1Field.set(addTwoLinkedList, this.num1Str);

        Field num2Field = AddTwoLinkedList.class.getDeclaredField("num2");
        num2Field.setAccessible(true);
        num2Field.set(addTwoLinkedList, this.num2Str);

        try {
            addTwoLinkedList.addTwoNumbers();
            assertEquals(this.result, addTwoLinkedList.getResult());
        } catch ( Exception e ) {
            if ( this.exceptionClass != null ) {
                assertEquals(this.exceptionClass, e.getClass());
                assertEquals(result, e.getLocalizedMessage());
            }
        }

    }
}
