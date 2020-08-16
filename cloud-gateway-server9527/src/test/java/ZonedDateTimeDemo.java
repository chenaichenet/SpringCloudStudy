import java.time.ZonedDateTime;

/**
 * FileName: ZonedDateTimeDemo
 * Author:   嘉平十七
 * Date:     2020/8/16 8:39
 * Description: 得到当前时间
 * notes：
 */

public class ZonedDateTimeDemo {
    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("当前时间："+zonedDateTime);
    }
}