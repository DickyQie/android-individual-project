package com.zhangqie.miyucalculator.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangqie on 2017/6/7.
 */

public class Util {

    public static float opt(String s) throws Exception{
        if(s == null || "".equals(s.trim())) {
            return 0f;
        }
        int a1=s.indexOf("+");
        int a2=s.indexOf("-");
        int a3=s.indexOf("*");
        int a4=s.indexOf("/");
        int a5=s.indexOf("(");
        if(a1==-1&&a2==-1&&a3==-1&&a4==-1){
            if(s.trim()==null||"".equals(s.trim())){
                throw new Exception("operate error");
            }
            return Float.parseFloat(s.trim());
        }

        if(a5!=-1){
            int a6=s.indexOf(")");
            if(a6==-1){
                throw new Exception("括号不匹配");
            }else{
                float f=opt(s.substring(a5+1,a6).trim());
                s=s.replace(s.substring(a5,a6+1), String.valueOf(f));
                return opt(s);
            }
        }

        if(a1!=-1){
            return opt(s.substring(0,a1))+opt(s.substring(a1+1,s.length()));
        }
        if(a2!=-1){
            return opt(s.substring(0,a2))-opt(s.substring(a2+1,s.length()));
        }
        if(a3!=-1){
            return opt(s.substring(0,a3))*opt(s.substring(a3+1,s.length()));
        }
        if(a4!=-1){
            return opt(s.substring(0,a4))/opt(s.substring(a4+1,s.length()));
        }
        return Float.parseFloat(s.trim());
    }

    /**
     * 最小计数单位
     *
     */
    private static String minExp="^((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\]))[\\+\\-\\*\\/]((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\]))$";
    /**
     * 不带括号的运算
     */
    private static String noParentheses="^[^\\(\\)]+$";
    /**
     * 匹配乘法或者除法
     */
    private static String priorOperatorExp="(((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\]))[\\*\\/]((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\])))";
    /**
     * 匹配加法和减法
     */
    private static String operatorExp="(((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\]))[\\+\\-]((\\d+(\\.\\d+)?)|(\\[\\-\\d+(\\.\\d+)?\\])))";
    /**
     * 匹配只带一个括号的
     */
    private static String minParentheses="\\([^\\(\\)]+\\)";

    public static String parseExp(String expression){
        //方法进入 先替换空格，在去除运算两边的()号
        expression=expression.replaceAll("\\s+", "").replaceAll("^\\(([^\\(\\)]+)\\)$", "$1");

        //最小表达式计算
        if(expression.matches(minExp)){
            String result=calculate(expression);
            return Double.parseDouble(result)>=0?result:"["+result+"]";
        }
        //计算不带括号的四则运算
        if(expression.matches(noParentheses)){
            Pattern patt=Pattern.compile(priorOperatorExp);
            Matcher mat=patt.matcher(expression);
            if(mat.find()){
                String tempMinExp=mat.group();
                expression=expression.replaceFirst(priorOperatorExp, parseExp(tempMinExp));
            }else{
                patt=Pattern.compile(operatorExp);
                mat=patt.matcher(expression);

                if(mat.find()){
                    String tempMinExp=mat.group();
                    expression=expression.replaceFirst(operatorExp, parseExp(tempMinExp));
                }
            }
            return parseExp(expression);
        }

        //计算带括号的四则运算
        Pattern patt=Pattern.compile(minParentheses);
        Matcher mat=patt.matcher(expression);
        if(mat.find()){
            String tempMinExp=mat.group();
            expression=expression.replaceFirst(minParentheses, parseExp(tempMinExp));
        }
        return parseExp(expression);
    }

    /**
     * 计算最小单位四则运算表达式（两个数字）
     * @param exp
     * @return
     */
    private static String calculate(String exp){
        exp=exp.replaceAll("[\\[\\]]", "");
        String number[]=exp.replaceFirst("(\\d)[\\+\\-\\*\\/]", "$1,").split(",");
        BigDecimal number1=new BigDecimal(number[0]);
        BigDecimal number2=new BigDecimal(number[1]);
        BigDecimal result=null;

        String operator=exp.replaceFirst("^.*\\d([\\+\\-\\*\\/]).+$", "$1");
        if("+".equals(operator)){
            result=number1.add(number2);
        }else if("-".equals(operator)){
            result=number1.subtract(number2);
        }else if("*".equals(operator)){
            result=number1.multiply(number2);
        }else if("/".equals(operator)){
            //第二个参数为精度，第三个为四色五入的模式
            result=number1.divide(number2,5,BigDecimal.ROUND_CEILING);
        }

        return result!=null?result.toString():null;
    }

}
