package wangdao;

import com.baomidou.mybatisplus.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ReverseList_15 {

    static final Map<String, Object> paramNameValuePairs = new HashMap();
    static  AtomicInteger paramNameSeq = new AtomicInteger(0);
    static String paramAlias = null;
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
    static String formatSqlIfNeed(boolean need, String sqlStr, Object... params) {
        if (need && !StringUtils.isEmpty(sqlStr)) {
            if (ArrayUtils.isNotEmpty(params)) {
                for(int i = 0; i < params.length; ++i) {
                    String genParamName = "MPGENVAL" + paramNameSeq.incrementAndGet();
                    sqlStr = sqlStr.replace(String.format("{%s}", i), String.format("#{%s.paramNameValuePairs.%s}", getParamAlias(), genParamName));
                    paramNameValuePairs.put(genParamName, params[i]);
                }
            }

            return sqlStr;
        } else {
            return null;
        }
    }
    static String getParamAlias() {
        return StringUtils.isEmpty(paramAlias) ? "ew" : paramAlias;
    }

    public static void main(String[] args) {
        String name = "name";
       System.out.println(formatSqlIfNeed(true,"district = {%s}",name));
    }
}
