package wangdao.Stack_5;

import java.util.Stack;

public class Solution {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        int result = 0;
        if (!stack2.empty()){
            result = stack2.pop();
        }else {
            if (stack1.empty()){
                return 0;
            }else {
                while (!stack1.empty()){
                    stack2.push(stack1.pop());
                }
                result = stack2.pop();
            }
        }
        return result;
    }
}
