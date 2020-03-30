package com.ddk.karthi.apps;

import com.google.gson.Gson;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class AddTwoLinkedList {
    ListNode listNode1;
    ListNode listNode2;
    ListNode sumListNode;

    public AddTwoLinkedList() {
        this.listNode1 = null;
        this.listNode2 = null;
        this.sumListNode = null;
    }

    public AddTwoLinkedList(int[] list1, int[] list2) {
        this.listNode1 = generateList(list1);
        this.listNode2 = generateList(list2);
        this.sumListNode = null;
    }

    public ListNode generateList(int[] array) {
        ListNode temp = null;
        ListNode reference = null;
        for ( int a : array ) {
            if (temp == null) {
                temp = new ListNode(a);
                reference = temp;
            } else {
                temp.next = new ListNode(a);
                temp = temp.next;
            }
        }
        return reference;
    }

    public void printNodes() {
        printListNode(listNode1);
        printListNode(listNode2);
        printListNode(sumListNode);
    }

    public void addTwoNumbers() {
        ListNode l1 = this.listNode1;
        ListNode l2 = this.listNode2;
        ListNode temp = null;
        int carryOver = 0;
        while ( l1 != null || l2 != null ) {
            int sum = carryOver;
            if ( l1 != null ) {
                sum += l1.val;
                l1 = l1.next;
            }
            if ( l2 != null ) {
                sum += l2.val;
                l2 = l2.next;
            }
            if ( temp == null ) {
                temp = new ListNode(sum % 10);
                temp.next = null;
                this.sumListNode = temp;
            } else {
                temp.next = new ListNode(sum % 10);
                temp = temp.next;
            }
            carryOver = sum / 10;
        }
        if ( carryOver != 0 ) {
            if ( temp == null )
                temp = new ListNode(carryOver);
            else
                temp.next = new ListNode(carryOver);
        }
    }

    public ListNode getResultListNode() {
        return sumListNode;
    }

    public String getResult() {
        return convertNodeToString(this.sumListNode);
    }

    private String convertNodeToString(ListNode listNode) {
        StringBuilder sb = new StringBuilder();
        while ( listNode != null ) {
            sb.append(listNode.val);
            listNode = listNode.next;
        }
        return sb.reverse().toString();
    }

    private void printListNode(ListNode listNode) {
        while ( listNode != null ) {
            System.out.print(listNode.val + " ");
            listNode = listNode.next;
        }
        System.out.println();
    }
}
