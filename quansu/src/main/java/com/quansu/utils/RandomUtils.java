package com.quansu.utils;

import java.util.Random;

/**
 * Created by com.ysnows on 2017/7/15.
 */

public class RandomUtils {

    public static final char[] LETTER_NUMBER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static final char[] LETTER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static final char[] NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 按指定大小在<b>26个英文字母</b>中生成随机数。
     *
     * @param t 生成的长度，t不能小于1或大于99，否则返回"0"
     *
     * @return 你想要的随机数
     *
     * @created 2013-5-16 下午02:40:05
     * @author Belen
     */
    public static String getRandomOfLetter(int t) {
        return get(LETTER, t);
    }

    /**
     * 按指定大小在<b>0-9</b>数字中生成随机数。
     *
     * @param t 生成的长度，t不能小于1或大于99，否则返回"0"
     *
     * @return 你想要的随机数
     *
     * @created 2013-5-16 下午02:40:05
     * @author Belen
     */
    public static String getRandomOfNumber(int t) {
        return get(NUMBER, t);
    }

    /**
     * 按指定大小在<b>25个英文以及10个数字</b>中生成随机数。
     *
     * @param t 生成的长度，t不能小于1或大于99，否则返回"0"
     *
     * @return 你想要的随机数
     *
     * @created 2013-5-16 下午02:40:05
     * @author Belen
     */
    public static String getRandomOfLetterAndNumber(int t) {
        return get(LETTER_NUMBER, t);
    }

    /**
     * 按指定数组生成数据。
     */
    private static String get(char[] c, int t) {
        if (t < 1 || t > 99) {
            return "0";
        }

        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的长度

        StringBuffer sb = new StringBuffer("");
        Random r = new Random();
        while (count < t) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < c.length) {
                sb.append(c[i]);
                count++;
            }
        }
        return sb.toString();
    }
}
