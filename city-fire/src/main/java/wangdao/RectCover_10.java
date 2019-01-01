package wangdao;

public class RectCover_10 {
    public int RectCover(int target) {
        int result = 0;
        int before1 = 2,before2=1;
        if (target == 0){
            return 0;
        }
        if (target == 1){
            return before2;
        }
        if (target ==2){
            return before1;
        }
        try {

        }finally {

        }
        for (int i =3;i <= target;i++){
            result = before2+before1;
            before2 = before1;
            before1 = result;
        }
        return result;
    }
}
