package esay;

/**
 * <pre>
 *      @ClassName
 *      @Author  : YMD
 *      @E-mail  : 1679423201@qq.com
 *      @Date    : 2022/2/24 20:06
 *      @Desc    :
 *      @Version : 1.0
 * </pre>
 */
public class easy {
    public static void main(String[] args) {
        String a = new String("abc");
        String b = new String("abc");
        String c = "abc";
        String d = "abc";
        System.out.println(a==b);
        System.out.println(a.equals(b));
        System.out.println(c==d);
        System.out.println(c.equals(a));
    }
}
