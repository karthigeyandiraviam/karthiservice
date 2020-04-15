package com.ddk.karthi.apps;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@XmlRootElement(name = "GradeSort")
public class GradeSort {
    public void sortGrades() {
        init();
        if ( this.unsortedGrades == null )
            this.unsortedGrades = new ArrayList<>();
        printArray(getUnsortedGrades().stream().toArray(String[]::new));
        List<String> validGrades = new ArrayList<>();
        for ( String s : getUnsortedGrades() ) {
            if ( getGradeOrder().containsKey(s) )
                validGrades.add(s);
        }
        if ( validGrades.size() > 0 ) {
            String[] validGradeArray = validGrades.stream().toArray(String[]::new);
            LOGGER.log(Level.INFO, "Merge sort");
            mergeSort(validGradeArray, validGradeArray.length);
            printArray(validGradeArray);
            this.sortedGrades = Arrays.asList(validGradeArray);
        }
        if ( this.sortedGrades == null )
            this.sortedGrades = new ArrayList<>();
        printArray(getSortedGrades().stream().toArray(String[]::new));
    }

    @XmlElement(name = "getSortedGrades")
    public List<String> getSortedGrades() {
        return this.sortedGrades;
    }

    @XmlElement(name = "getUnsortedGrades")
    public List<String> getUnsortedGrades() {
        return this.unsortedGrades;
    }

    public Map<String, Integer> getGradeOrder() {
        return this.gradeOrder;
    }

    private void init() {
        this.gradeOrder = new HashMap<String, Integer>() {{
            put("A+", 1);
            put("A", 2);
            put("A-", 3);
            put("B+", 4);
            put("B", 5);
            put("B-", 6);
            put("C+", 7);
            put("C", 8);
            put("C-", 9);
            put("D+", 10);
            put("D", 11);
            put("D-", 12);
            put("F", 13);
        }};
    }

    private void printArray(String[] array) {
        LOGGER.log(Level.INFO, String.join(" ", array));
    }

    private void mergeSort(String[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        String[] l = new String[mid];
        String[] r = new String[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }

    private void merge(String[] a, String[] l, String[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (getGradeOrder().get(l[i]) <= getGradeOrder().get(r[j])) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    private Map<String, Integer> gradeOrder;

    @XmlElement(name = "unsortedGrades")
    protected List<String> unsortedGrades;

    @XmlElement(name = "sortedGrades")
    protected List<String> sortedGrades;
}
