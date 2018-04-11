/*
 * <p>Copyright : LinHai Technologies Co., Ltd. Copyright 2018, All right reserved.</p>
 * <p>Description : <pre>TODO(用一句话描述该文件做什么)</pre></p> <p>FileName : HexUtils.java</p> <p>Package :
 * pers.linhai.nature.utils</p>
 * @Creator lilinhai 2018年4月11日 下午8:56:00
 * @Version V1.0
 */

package pers.linhai.nature.utils;


import pers.linhai.nature.exception.DecoderException;


/**
 * 16进制转换工具
 * <p>ClassName      : HexUtils</p>
 * @author lilinhai 2018年4月11日 下午8:56:00
 * @version 1.0
 */
public abstract class HexUtils
{

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Converts an array of bytes into a String representing the hexadecimal values of each byte in order. The returned
     * String will be double the length of the passed array, as it takes two characters to represent any given byte.
     *
     * @param data
     *            a byte[] to convert to Hex characters
     * @return A String containing lower-case hexadecimal characters
     * @since 1.4
     */
    public static String encode(byte[] data)
    {
        return encode(data, true);
    }

    /**
     * Converts an array of bytes into a String representing the hexadecimal values of each byte in order. The returned
     * String will be double the length of the passed array, as it takes two characters to represent any given byte.
     *
     * @param data
     *            a byte[] to convert to Hex characters
     * @return A String containing lower-case hexadecimal characters
     * @since 1.4
     */
    public static String encode(byte[] data, boolean toLowerCase)
    {
        return new String(encode(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER));
    }

    /**
     * Converts a String representing hexadecimal values into an array of bytes of those same values. The
     * returned array will be half the length of the passed String, as it takes two characters to represent any given
     * byte. An exception is thrown if the passed String has an odd number of elements.
     *
     * @param data
     *            A String containing hexadecimal digits
     * @return A byte array containing binary data decoded from the supplied char array.
     * @throws DecoderException
     *             Thrown if an odd number or illegal of characters is supplied
     * @since 1.11
     */
    public static byte[] decode(final String data)
    {
        return decode(data.toCharArray());
    }

    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     * The returned array will be double the length of the passed array, as it takes two characters to represent any
     * given byte.
     *
     * @param data
     *            a byte[] to convert to Hex characters
     * @param toDigits
     *            the output alphabet (must contain at least 16 chars)
     * @return A char[] containing the appropriate characters from the alphabet
     *         For best results, this should be either upper- or lower-case hex.
     * @since 1.4
     */
    private static char[] encode(byte[] data, char[] toDigits)
    {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++ )
        {
            out[j++ ] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++ ] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * Converts a hexadecimal character to an integer.
     *
     * @param ch
     *            A character to convert to an integer digit
     * @param index
     *            The index of the character in the source
     * @return An integer
     * @throws DecoderException
     *             Thrown if ch is an illegal hex character
     */
    private static int toDigit(char ch, int index)
    {
        final int digit = Character.digit(ch, 16);
        if (digit == -1)
        {
            throw new DecoderException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

    /**
     * Converts an array of characters representing hexadecimal values into an array of bytes of those same values. The
     * returned array will be half the length of the passed array, as it takes two characters to represent any given
     * byte. An exception is thrown if the passed char array has an odd number of elements.
     *
     * @param data
     *            An array of characters containing hexadecimal digits
     * @return A byte array containing binary data decoded from the supplied char array.
     * @throws DecoderException
     *             Thrown if an odd number or illegal of characters is supplied
     */
    private static byte[] decode(char[] data)
    {
        int len = data.length;
        if ((len & 0x01) != 0)
        {
            throw new DecoderException("Odd number of characters.");
        }
        final byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++ )
        {
            int f = toDigit(data[j], j) << 4;
            j++ ;
            f = f | toDigit(data[j], j);
            j++ ;
            out[i] = (byte)(f & 0xFF);
        }

        return out;
    }
}
