package com.gmail.webserg.algorithmsAndAnalysis.leetCode;

class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
        val = x;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    void print() {
        ListNode cur = this;
        while (cur != null) {
            System.out.print(cur.val + "->");
            cur = cur.next;
        }

    }

    public static ListNode buildLinkedList(int[] keys) {
        if (keys.length == 0) return null;
        ListNode head = new ListNode(keys[0]);
        ListNode cur = head;
        for (int i = 1; i < keys.length; i++) {
            cur.next = new ListNode(keys[i]);
            cur = cur.next;
        }
        return head;
    }

    @Override
    public String toString() {
        return val + "";
    }
}
