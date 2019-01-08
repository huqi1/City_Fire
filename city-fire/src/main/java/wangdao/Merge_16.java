package wangdao;

public class Merge_16 {
    public ListNode Merge(ListNode list1,ListNode list2) {
        ListNode list1next = null;
        ListNode list2pre = null;
        ListNode list2next = null;
        int flag = 2;//第一次需要特殊考虑pre
        if (list2 == null)
            return list1;
        //这样做的目的是不能直接操作list1和list2，需要确定list2pre和list2next的初始位置为第二个点
        if (list1.val <= list2.val){
            list1next = list1.next;
            list1.next = list2;
            list2 = list1;
            list1 = list1next;
        }
        list2pre = list2.next;
        list2next =list2.next;
        while (list1 != null && list2next != null){
            if (list1.val < list2next.val){
                list1next = list1.next;
                //如果是第一个点的话，此时还没有list2pre，所以直接用list2.next
                if (flag == 2){
                    list1.next = list2next;
                    list2.next =list1;
                }else {
                    list2pre.next = list1;
                    list1.next = list2next;
                }
                list1 = list1next;
            }else if(list1.val >= list2next.val){
                list2pre = list2next;
                list2next = list2pre.next;
                if (list2next == null){
                    list2pre.next = list1;
                    break;
                }
            }
            flag++;
        }
        return  list2;
    }
}
