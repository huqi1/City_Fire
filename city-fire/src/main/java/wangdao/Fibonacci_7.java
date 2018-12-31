package wangdao;

public class Fibonacci_7 {
    public int Fibonacci(int n) {
        int before1 = 1;
        int before2 = 0;
        int result = 0;
        if( n == 0 || n == 1 ){
            return n;
        }else if (n == 2){
            return 1;
        }
        for (int i =2;i<=n;i++){
            result = before2+before1;
            before2 = before1;
            before1 = result;
        }
        return result;
    }
}
