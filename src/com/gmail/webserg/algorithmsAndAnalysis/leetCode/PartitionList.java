package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

/**
 * Partitioning a linked list around a given value and keeping the original order
 * Difficulty Level : Easy
 * Last Updated : 05 Jun, 2021
 * Given a linked list and a value x, partition it such that all nodes less than x come first,
 * then all nodes with a value equal to x, and finally nodes with a value greater than or equal to x.
 * The original relative order of the nodes in each of the three partitions should be preserved. The partition must work in place.
 * Examples:
 * Input : 1->4->3->2->5->2->3,
 * x = 3
 * Output: 1->2->2->3->3->4->5
 * <p>
 * Input : 1->4->2->10
 * x = 3
 * Output: 1->2->4->10
 * <p>
 * Input : 10->4->20->10->3
 * x = 3
 * Output: 3->10->4->20->10
 */
public class PartitionList {

    public static ListNode partition(ListNode cur, int p) {
        if (cur == null) return null;
        ListNode smallerHead = null, smallerLast = null;
        ListNode greaterLast = null, greaterHead = null;
        ListNode equalHead = null, equalLast = null;
        while (cur != null) {
             if (cur.val == p) {
                if (equalHead == null) {
                    equalHead = equalLast = cur;
                } else {
                    equalLast.next = cur;
                    equalLast = cur;
                }
            }
            else if (cur.val < p) {

                if (smallerHead == null) {
                    smallerHead = smallerLast = cur;
                } else {
                    smallerLast.next = cur;
                    smallerLast = cur;
                }

            } else  {
                if (greaterHead == null) {
                    greaterHead = greaterLast = cur;
                } else {
                    greaterLast.next = cur;
                    greaterLast = cur;
                }
            }
            cur = cur.next;
        }

        if (greaterLast != null)
            greaterLast.next = null;

        if (smallerHead == null) {
            if (equalHead == null)
                return greaterHead;
            equalLast.next = greaterHead;
            return equalHead;
        }

        if (equalHead == null) {
            smallerLast.next = greaterHead;
            return smallerHead;
        }
        smallerLast.next = equalHead;
        equalLast.next = greaterHead;
        return smallerHead;
    }

    public static void main(String[] args) {
        ListNode head = ListNode.buildLinkedList(new int[]{1, 4, 3});
        head.print();
        System.out.println();
        ListNode headPart = partition(head, 4);
        headPart.print();

        head = ListNode.buildLinkedList(new int[]{1, 4, 3});
        head.print();
        System.out.println();
        headPart = partition(head, 0);
        headPart.print();

        System.out.println();
        head = ListNode.buildLinkedList(new int[]{1, 4, 3, 2, 5, 2});
        head.print();
        System.out.println();
        headPart = partition(head, 3);
        headPart.print();
    }




}
