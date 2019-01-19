package wangdao;

import java.util.Iterator;
import java.util.TreeSet;

public class NumberOf1_11 {
    /** 求n的二进制补码 1的个数
     *  首先明白 java int 是 4字节
     *  存放8：00000000 00000000 00000000 00001000
     *  存放-8： 原码：10000000 00000000 00000000 00001000
     *          反码： 11111111 11111111 11111111 11110111
     *          补码   11111111 11111111 11111111 11111000 （存的是补码）
     *
     *  存放-9： 原码：10000000 00000000 00000000 00001001
     *          反码： 11111111 11111111 11111111 11110110
     *          补码   11111111 11111111 11111111 11110111 （存的是补码）
     *
     *   运算方法：
     *   一个数的补码 与 其-1的补码进行运算（如 -8）
     *   -8 & -9 == 11111111 11111111 11111111 11110000 （-8的1少了一个）
     *   计数+1，每次与运算都会少一个1
     *   所以当为0时 得出1的个数
     *
     * */
    public int NumberOf1(int n) {
        int count = 0;
        if (n == 0){
            return 0;
        }
        while ( n != 0){
            n = n & (n-1);
            ++count;
        }
     return  count;
    }
    public static void main(String[] args){
        /*int i = 5;
        int b = -5;
        System.out.println(i>>1);
        System.out.println(i>>>1);

        System.out.println(b>>1);
        System.out.println(b>>>1);*/
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("2019-01-10");
        treeSet.add("2019-01-06");
        treeSet.add("2019-01-18");
        treeSet.add("2019-01-10");
        treeSet.add("2019-01-11");
        treeSet.add("2019-01-13");
        treeSet.add("2019-01-07");
        treeSet.comparator();
        Iterator iterator = treeSet.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
