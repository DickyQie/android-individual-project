package com.zhangqie.miyucalculator.util;

import android.util.Log;

/**
 * Created by zhangqie on 2017/6/7.
 */

public class UtilDB {

    /***
     * 得到最后一个字符
     * @param text
     * @return
     */
    public static final String TEXTONE(String text){
       return text.substring((text.length()-1),text.length());
    }

    /***
     * 得到最后两个字符
     * @param text
     * @return
     */
    public static final String TEXTTOW(String text){
        return text.substring((text.length()-2),text.length());
    }
    /***
     * 得到倒数第两个字符
     * @param text
     * @return
     */
    public static final String TEXTTOWONE(String text){
        return text.substring((text.length()-2),(text.length()-1));
    }

    /***
     * 验证是否包含点  并且结尾
     * @param text
     * @return
     */
    public static final boolean ISSHOWDES(String text){
        String n=text.substring((text.length()-1),text.length());
        if (text.indexOf(".")!=-1 && n.equals(".")){
            return false;
        }
        return true;
    }


    /***
     * 验证是否除以0
     * @param text
     * @return
     */
    public static final boolean ISSHOWO(String text){
        String n=text.substring((text.length()-1),text.length());
        if (n.equals("÷")){
            return false;
        }
        return true;
    }

    /***
     * 删除
     * @param text
     * @return
     */
    public static final String SHOWDELETE(String text){
        return text.substring(0, (text.length()-1));
    }

    /**
     * 判断当前字符或字符串是否是符号
     * @param str
     * @return
     */
    public static boolean ISNUMBER(String str) {
        return str.startsWith("+")
                || str.startsWith("-")
                || str.startsWith("÷")
                || str.startsWith("×");
    }

    /**
     * 判断式子中是否包含算数式
     * @param str
     * @return
     */
    public static boolean ISFANGSHI(String str) {
        if (str.indexOf("+")!=-1 || str.indexOf("-")!=-1 || str.indexOf("÷")!=-1 ||str.indexOf("×")!=-1){
            return true;
        }
        return false;
    }

    /**
     * 判断当前字符或字符串是否是取余
     * @param str
     * @return
     */
    public static boolean ISNUMBERQYS(String str) {
        if (str.indexOf("%")!=-1){
            return true;
        }
        return false;
    }

    /***
     * 验证负数
     * @param str
     * @return
     */
    public static  boolean ISTOWNUMBER(String str){
        return str.startsWith("--");
    }

    /**
     * 判断结果余数最后面的是0
     */
    public static String getDateSelects(String str) {
        int s0 = str.indexOf(".");
        if (s0 <= 0) {
            return str;
        }
        String s1 = str.substring((s0 + 1), (str.length()));
        int i = s1.length();
        if (i>3){
            if (s1.substring(i-3,i).equals("000")){
                return str.substring(0,(str.length()-3));
            }else if (s1.substring(i-2,i).equals("00")){
                return str.substring(0,(str.length()-2));
            }else if (s1.substring(i-1,i).equals("0")){
                return str.substring(0,(str.length()-1));
            }else {
                return str;
            }
        }
        else if (i>2){
           if (s1.substring(i-2,i).equals("00")){
                return str.substring(0,(str.length()-2));
            }else if (s1.substring(i-1,i).equals("0")){
                return str.substring(0,(str.length()-1));
            }else {
            return str;
            }
        }else {
                return str;
        }

    }

    /**
     * 判断点
     * @param strText
     * @return
     */
    static String setTextDeiShow(String strText)
    {
        int s0 = strText.indexOf(".");
        if (s0 <= 0) {
            return strText;
        }
        if(s0==(strText.length()-1))//1.
        {
            return  (strText.substring(0, (strText.length()-1)));
        }
        return strText;
    }


    /***
     * 判断字体大小
     */
    public static float setTextSize(String text) {
        if (text.length() > 15) {
            return 25;
        } else if (text.length() > 9) {
            return 35;
        } else {
            return 45;
        }
    }

    /***
     * 替换算数符号
     */
    public static String SHOWTEXTREPLACE(String text) {
        String a=text.replace("÷","/").toString();
        return a.replace("×","*").toString();

    }


    /**
     * 判断是否是以符号结尾哦
     * @param str
     * @return
     */
    public static String setNum(String str) {
        String str1=TEXTONE(str);
        String str2=TEXTTOWONE(str);
        if (ISNUMBER(str1) && ISNUMBER(str2)){
            return str.substring(0,str.length()-2);
        }else if (ISNUMBER(str1)){
            return str.substring(0,str.length()-1);
        }
        return str;

    }


    public static String SHOWQYS(Integer num1,Integer num2){
        return String.valueOf((num1%num2));
    }

    /****
     * 判断参数中是否包含点存在
     * @param string
     * @return
     */
    public static boolean SHOWDLENGS(String string){
        if (string.indexOf(".")!=-1){
            return false;
        }
        return true;
    }



    /**
     * 判断结果余数是否都是0
     */
    public static String getDateSelectsNum(String str) {
        int s0 = str.indexOf(".");
        Log.i("1",s0+"");
        if (s0 <= 0) {
            return str;
        }
        String s01=str.substring(0,s0+1);
        Log.i("2",s01);
        String s1 = str.substring((s0 + 1), (str.length()));//52999999;   2.5625
        Log.i("3",s1);
        if (s1.length()>4){
            String s2=s1.substring(4,5);
            Log.i("4",s2);
            if (Integer.valueOf(s2)>4){
                int l1=Integer.valueOf(s1.substring(0,4))+1;
                Log.i("5",l1+"");
                if (l1>999){
                    return (s01+String.valueOf(l1));
                }else if (l1>99){
                    return (s01+"0"+String.valueOf(l1));
                }else if (l1>9){
                    return (s01+"00"+String.valueOf(l1));
                }else if (l1>0) {
                    return (s01 +"000"+ String.valueOf(l1));
                }
                else{
                    return (s01 + String.valueOf(l1));
                }
            }else {
                Log.i("6",s01+s1.substring(0,4));
                return (s01+s1.substring(0,4));
            }
        }else {
            return str;
        }
    }



}
