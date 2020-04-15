package com.ddk.karthi.apps;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

@XmlRootElement(name = "ListNode")
class ListNode {
    @XmlElement(name = "val")
    Integer val;
    @XmlElement(name = "next")
    ListNode next;
}

@XmlRootElement(name = "AddTwoLinkedList")
public class AddTwoLinkedList {

    @XmlElement(name = "addTwoNumbers")
    public void addTwoNumbers() {
        convertStringToNode();
        ListNode l1 = this.listNode1;
        ListNode l2 = this.listNode2;

        ListNode temp = null;
        int carryOver = 0;
        // while ( l1 != null || l2 != null ) {
        while ( true ) {
            /*
             ** convertStringToNode method makes both linked list l1 and l2 as same length
             ** by padding leading zeros. Padding leading zeros is required when input is
             ** 0002 and 1 is passed.
             **
             ** So just check for l1 == null and break this loop.
             ** This is improve code coverage
             **
             */
            if ( l1 == null ) {
                LOGGER.log(Level.INFO, "l2 is null");
                break;
            }

            int sum = carryOver;
            sum += l1.val;
            l1 = l1.next;

            sum += l2.val;
            l2 = l2.next;

            if ( temp == null ) {
                temp = new ListNode();
                temp.val = sum % 10;
                temp.next = null;
                this.sumListNode = temp;
            } else {
                temp.next = new ListNode();
                temp.next.val = sum % 10;
                temp = temp.next;
            }
            carryOver = sum / 10;
        }
        if ( carryOver != 0 ) {
            temp.next = new ListNode();
            temp.next.val = carryOver;
        }
    }

    @XmlElement(name = "getNum1")
    public String getNum1() {
        return this.num1;
    }

    @XmlElement(name = "getNum2")
    public String getNum2() {
        return this.num2;
    }

    @XmlElement(name = "getResult")
    public String getResult() {
        return convertNodeToString(this.sumListNode);
    }

    private String convertNodeToString(ListNode listNode) {
        StringBuilder sb = new StringBuilder();
        while ( listNode != null ) {
            sb.append(listNode.val);
            listNode = listNode.next;
        }
        Integer sum = Integer.parseInt(sb.reverse().toString());
        return Integer.toString(sum);
    }

    private void convertStringToNode() {
        int maxLength = (getNum1().length() > getNum2().length()) ? getNum1().length() : getNum2().length();
        LOGGER.log(Level.INFO, "MaxLen: " + maxLength + ", num1: " + getNum1() + ", num2: " + getNum2());
        String format = "%0" + maxLength + "d";
        LOGGER.log(Level.INFO, "Format: " + format);
        num1 = String.format(format, Integer.parseInt(getNum1()));
        LOGGER.log(Level.INFO, "MaxLen: " + maxLength + ", num1: " + getNum1() + ", num2: " + getNum2());
        num2 = String.format(format, Integer.parseInt(getNum2()));

        Integer[] l1 = new Integer[getNum1().length()];
        Integer[] l2 = new Integer[getNum2().length()];

        for (int i = getNum1().length() - 1, j = 0; i >= 0; i--, j++)
            l1[j] = getNum1().charAt(i) - '0';

        for (int i = getNum2().length() - 1, j = 0; i >= 0; i--, j++)
            l2[j] = getNum2().charAt(i) - '0';

        this.listNode1 = generateList(l1);
        this.listNode2 = generateList(l2);
    }

    private ListNode generateList(Integer[] array) {
        ListNode temp = null;
        ListNode reference = null;
        for ( int a : array ) {
            if (temp == null) {
                temp = new ListNode();
                temp.val = a;
                reference = temp;
            } else {
                temp.next = new ListNode();
                temp.next.val = a;
                temp = temp.next;
            }
        }
        return reference;
    }

/*
    public void printNodes() {
        printListNode(listNode1);
        printListNode(listNode2);
        printListNode(sumListNode);
    }

    private void printListNode(ListNode listNode) {
        List<Integer> values = new ArrayList<>();
        while ( listNode != null ) {
            values.add(listNode.val);
            listNode = listNode.next;
        }
        LOGGER.log(Level.INFO,
                values.stream().
                        map(String::valueOf).
                        collect(Collectors.joining(" "))
        );
    }
*/
    private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());


    ListNode listNode1;
    ListNode listNode2;

    @XmlElement(name = "num1")
    protected String num1;
    @XmlElement(name = "num2")
    protected String num2;
    @XmlElement(name = "sumListNode")
    ListNode sumListNode;
}
