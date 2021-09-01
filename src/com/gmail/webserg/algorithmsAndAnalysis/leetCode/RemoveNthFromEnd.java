package com.gmail.webserg.algorithmsAndAnalysis.leetCode;


/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
public class RemoveNthFromEnd {
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode curPointer = head;
        ListNode delPointer = head;
        int len = 1;
        int j = 0;
        while (curPointer.next != null) {
            if (len < n) {
                len++;
            } else {
                delPointer = delPointer.next;
            }
            curPointer = curPointer.next;
        }

        return delPointer;
    }

    public static void main(String[] args) {

        System.out.println(removeNthFromEnd(buildLinkedList(), 2));
        System.out.println(removeNthFromEnd(buildLinkedList(), 3));
        System.out.println(removeNthFromEnd(buildLinkedList(), 4));
        System.out.println(removeNthFromEnd(buildLinkedList(), 5));
    }

    private static ListNode buildLinkedList() {
        ListNode l1 = new ListNode(1);
        (((l1.next = new ListNode(2)).next = new ListNode(3)).next = new ListNode(4)).next = new ListNode(5);
        return l1;
    }
}

