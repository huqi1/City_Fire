package wangdao;

public class FindKthToTail_14 {
    public ListNode FindKthToTail(ListNode head,int k) {
        int num = 0;
        int index = 0;
        ListNode head2 = head;
        while (head != null){
            num++;
            head = head.next;
        }
        while (head2 != null){
            index++;
            if (index == num-k+1){
                break;
            }
            head2 = head2.next;
        }
        return head2;
    }
}
