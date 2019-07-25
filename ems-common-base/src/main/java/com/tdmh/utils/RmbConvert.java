package com.tdmh.utils;

/**
 * 2 * @Author: Auser_Qh
 * 3 * @Date: 2019/7/18 14:03
 * 4转换为人民币
 */
public class RmbConvert {
    public String simpleToBig(double money) {
        if (money < 0) {
            return "";
        }
        if (money == 0) {
            return "零元";
        }
        String result = "";
        // 段内的量度
        char[] hunit = { '拾', '佰', '仟' };
        // 段间的量度
        char[] vunit = { '万', '亿' };
        // 小写对应的大写
        char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };

        long longMoney = (long) (money * 100);
        // 将小数变成整数,再放入字符串中
        String strMoney = String.valueOf(longMoney);
        // 整数部分
        String head = strMoney.substring(0, strMoney.length() - 2);
        // 小数部分
        String tail = strMoney.substring(strMoney.length() - 2);

        String prefix = ""; // 存放转换的整数部分
        String suffix = ""; // 存放转换的小数部分

        if (tail.equals("00")) {
            suffix += "整";
        } else {
            char tailChar[] = tail.toCharArray();
            suffix += digit[tailChar[0] - '0'] + "角"
                    + digit[tailChar[1] - '0'] + "分";
        }
        char headChar[] = head.toCharArray();
        /*
         * 考虑零的情况，记录0的个数
         */
        char zero = '0';
        byte zeroSize = 0;
        for (int i = 0; i < headChar.length; i++) {
            int idx = (headChar.length - i - 1) % 4;
            // idx=0,表示每4位分段了
            int vidx = (headChar.length - i - 1) / 4; // 判断“段间”，“万，亿”
            if (headChar[i] == '0') {
                zeroSize++;
                if (zero == '0') {
                    zero = digit[0];
                } else if (idx == 0 && zeroSize < 4 && vidx > 0) {
                    // 当遇到1,1000,1234时， 1千万才能表示出来
                    prefix += vunit[vidx - 1];
                    // 当1千万表示出来时，“零”就不需要了，在后来判断zero != 0
                    zero = '0';
                }
                continue;
            }
            // 没有转成千万时，就将”零“加入
            if (zero != '0') {
                // 不至于出现很多个”零“
                prefix += zero;
            }
            // 还原成以前的
            zeroSize = 0;
            // ‘1’变成‘壹’
            prefix += digit[headChar[i] - '0'];

            if (idx > 0) {
                // 加上 "十，佰，千"
                prefix += hunit[idx - 1];
            }
            if (idx == 0 && vidx > 0) {
                // 加上 “万，亿”
                prefix += vunit[vidx - 1];
            }
        }
        if (prefix.length() > 0) {
            result = prefix + "圆" + suffix;
        }
        return result;
    }

    public static void main(String[] args) {
        RmbConvert rmb = new RmbConvert();
        String cn = rmb.simpleToBig(1234567890.09);
        System.out.println(cn);
    }
}
