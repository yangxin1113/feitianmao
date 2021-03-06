package cn.feitianmao.app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class RegulaUtils {

    private static RegulaUtils check;

    private RegulaUtils(){

    }

    public static RegulaUtils getInstance(){

        if(check == null)
            check = new RegulaUtils();
        return check;
    }

    /**
     * 判断手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

        }

    /**
     * 判断邮编
     * @param zipString
     * @return
     */
    public static boolean isZipNO(String zipString){
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }

    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static Integer getDigit(String s){
        if(s == null || s.equals("")){
            return 0;
        }
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        int result = Integer.valueOf(m.replaceAll("").trim());
        return result;
    }
}
