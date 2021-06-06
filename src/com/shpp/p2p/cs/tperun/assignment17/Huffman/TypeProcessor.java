package com.shpp.p2p.cs.tperun.assignment17.Huffman;


/* This class contains some methods for converting data types,
 * i.e. receiving an integer from an array of 4 bytes, obtaining a long
 * number from an array of 8 bytes, converting a byte to a binary string,
 * converting an integer to an array of bytes, converting a long number
 * to an array of bytes, etc.
 */
public class TypeProcessor implements Constants {

    /**
     * Receives an integer from an array of 4 bytes.
     *
     * @param bytes The input bytes array.
     * @return The integer.
     */
    public int getIntFromFourBytes(byte[] bytes) {

        int result = ((255 & bytes[0]) << 24) | ((255 & bytes[1]) << 16) |
                ((255 & bytes[2]) << 8) | (255 & bytes[3]);

        return result;
    }

    /**
     * Receives a short integer from an array of 2 bytes.
     *
     * @param bytes The input bytes array.
     * @return The short value.
     */
    public short getShortFromTwoBytes(byte[] bytes) {

        return (short) (((255 & bytes[0]) << 8) | (255 & bytes[1]));
    }


    /**
     * Receives a long number from an array of 8 bytes.
     *
     * @param bytes The input bytes array.
     * @return The long integer.
     */
    public long getLongFromEightBytes(byte[] bytes) {

        long result = ((long) (255 & bytes[0]) << 56) | ((long) (255 & bytes[1]) << 48) |
                ((long) (255 & bytes[2]) << 40) | ((long) (255 & bytes[3]) << 32) |
                ((long) (255 & bytes[4]) << 24) | ((long) (255 & bytes[5] << 16) |
                ((long) (255 & bytes[6]) << 8) | ((long) (255 & bytes[7])));

        return result;
    }

    /**
     * Returns a string representation of the byte as an unsigned integer in base 2.
     * If the string is less than 8 characters long, add zeros to the left to 8.
     *
     * @param byt The input byte value from -128 to 127.
     * @return The string representation of the byte value.
     */
    public String byteToBinaryString(byte byt, int limit) {

        String string = Integer.toBinaryString(byt & 255);

        int count = limit - string.length();
        for (int i = 0; i < count; i++) {
            string = "0".concat(string);
        }
        return string;
    }

    /**
     * Converts an int number into an array of four bytes.
     *
     * @param number The input number.
     * @return The byte array.
     */
    public byte[] intToByteArray(int number) {

        byte[] array = new byte[INT_SIZE_IN_BYTES];
        for (int i = 0; i < array.length; i++) {

            array[i] = (byte) (number >>> 8 * (array.length - 1 - i));
        }
        return array;
    }

    /**
     * Converts an short number into an array of two bytes.
     *
     * @param number The input number.
     * @return The byte array.
     */
    public byte[] shortToByteArray(short number) {

        byte[] array = new byte[SHORT_SIZE_IN_BYTES];
        for (int i = 0; i < array.length; i++) {

            array[i] = (byte) (number >>> BYTE * (array.length - 1 - i));
        }
        return array;
    }

    /**
     * Converts an long number into an array of eight bytes.
     *
     * @param number The number to converting.
     * @return The byte array.
     */
    public byte[] longToByteArray(long number) {

        byte[] array = new byte[LONG_SIZE_IN_BYTES];
        for (int i = 0; i < array.length; i++) {

            array[i] = (byte) (number >>> LONG_SIZE_IN_BYTES * (array.length - 1 - i));
        }
        return array;
    }

    /**
     * Converts the bit string to byte value.
     *
     * @param bitString The bit string.
     * @return The byte value.
     */
    public byte bitsToByte(String bitString) {

        byte byteValue = (byte) Integer.parseInt(bitString, 2);
        return byteValue;
    }
}
