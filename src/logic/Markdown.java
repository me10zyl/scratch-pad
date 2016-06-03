package logic;

/**
 * Created by ZyL on 2016/6/3.
 */
public class Markdown {
    public static String convert(String plain){
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        plain = plain.replaceAll("#(.+)", "<h1>$1</h1>");
        sb.append(plain);
        sb.append("</body></html>");
        return sb.toString();
    }
}
