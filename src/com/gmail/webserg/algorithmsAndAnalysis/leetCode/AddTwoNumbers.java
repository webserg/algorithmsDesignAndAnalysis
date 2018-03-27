package com.gmail.webserg.algorithmsAndAnalysis.leetCode;


/**
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 */

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode sum = null;
        ListNode cur = null;

        boolean tens = false;
        while (l1 != null || l2 != null) {
            int val1 = 0, val2 = 0;
            if (l1 != null) {
                val1 = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                val2 = l2.val;
                l2 = l2.next;
            }
            int s = val1 + val2;
            if (tens) {
                s += 1;
                tens = false;
            }
            if (s >= 10) {
                s = s - 10;
                tens = true;
            }
            if (sum == null) {
                sum = new ListNode(s);
                cur = sum;
            } else {
                cur.next = new ListNode(s);
                cur = cur.next;
            }

        }
        if (tens) {
            cur.next = new ListNode(1);
        }
        return sum;
    }

    public static void main(String[] args) {
        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        ListNode l1 = new ListNode(2);
        (l1.next = new ListNode(4)).next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        (l2.next = new ListNode(6)).next = new ListNode(4);
        addTwoNumbers.addTwoNumbers(l1, l2).print();
        System.out.println();

        l1 = new ListNode(5);

        l2 = new ListNode(5);
        addTwoNumbers.addTwoNumbers(l1, l2).print();
        System.out.println();

        l1 = new ListNode(1);
        l1.next = new ListNode(8);

        l2 = new ListNode(0);
        addTwoNumbers.addTwoNumbers(l1, l2).print();

    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    void print() {
        ListNode cur = this;
        while (cur != null) {
            System.out.print(cur.val + "->");
            cur = cur.next;
        }

    }

    @Override
    public String toString() {
        return val + "";
    }
}
