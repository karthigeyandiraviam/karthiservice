package com.ddk.karthi.junit;

import com.ddk.karthi.apps.GradeSort;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class GradeSortTest {
    private List<String> unsortedGrades;
    private List<String> result;
    private Class exceptionClass;

    @Parameterized.Parameters(name = "{index}: Unsorted Grades {0} should result in Sorted Grades {1}")
    public static Object[][] dataGradeSortTest() {
        Object[][] data = new Object[][] {
                {Arrays.asList("F", "D-", "D", "D+"), Arrays.asList("D+", "D", "D-", "F"), null },
                {Arrays.asList("A", "A-", "A+", "B+"), Arrays.asList("A+", "A", "A-", "B+"), null },
                {Arrays.asList("E", "E-", "E+", "G+"), new ArrayList<>(), null },
                {null, new ArrayList<>(), null }
        };
        return data;
    }

    public GradeSortTest(List<String> unsortedGrades, List<String> result, Class exceptionClass) {
        this.unsortedGrades = unsortedGrades;
        this.result = result;
        this.exceptionClass = exceptionClass;
    }

    @Test
    public void testGradeSort() throws Exception {
        GradeSort gradeSort = new GradeSort();

        Field num1Field = GradeSort.class.getDeclaredField("unsortedGrades");
        num1Field.setAccessible(true);
        num1Field.set(gradeSort, this.unsortedGrades);

        try {
            gradeSort.sortGrades();
            assertEquals(this.result, gradeSort.getSortedGrades());
        } catch ( Exception e ) {
            if ( this.exceptionClass != null ) {
                assertEquals(this.exceptionClass, e.getClass());
                assertEquals(result, e.getLocalizedMessage());
            }
        }

    }
}
