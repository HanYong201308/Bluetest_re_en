package com.example.gongchen.bluetest_re.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Author: Administrator
 * Time: 2019/6/5
 * Description:
 */
public class Util {




    /**
     *  普通字符转换成16进制字符串
     * @param str
     * @return
     */
    public static String str2HexStr(String str)
    {
        byte[] bytes = str.getBytes();
        // 如果不是宽类型的可以用Integer
        BigInteger bigInteger = new BigInteger(1, bytes);
        return bigInteger.toString(16);
    }

    /** 16进制的字符串转换成16进制字符串数组
     * @param src
     * @return
     */
    public static byte[] HexString2Bytes(String src) {
        int len = src.length() / 2;
        byte[] ret = new byte[len];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < len; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }
}
