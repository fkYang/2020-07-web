import java.util.Date;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/3
 */
public class demo {
    public static void main(String[] args) {
        show();
    }
  static   void show(){
        Date date = new Date();
        Date date2 = new Date();

      System.out.println(date.getDay());

        System.out.println(date.toString());
    }
}
