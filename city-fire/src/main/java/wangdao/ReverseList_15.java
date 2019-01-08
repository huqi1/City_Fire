package wangdao;

public class ReverseList_15 {
    public static ListNode ReverseList(ListNode head) {
        if (head == null) return null;
        ListNode newhead  = head;
        ListNode next = head.next;
        newhead.next = null;
        head = next;
        while (head != null){
            next = head.next;
            head.next = newhead;
            newhead = head;
            head = next;
        }
        return newhead;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode5.next = null;
        listNode4.next = listNode5;
        listNode3.next = listNode4;
        listNode2.next = listNode3;
        listNode1.next = listNode2;

        ReverseList(listNode1);
    }
}
