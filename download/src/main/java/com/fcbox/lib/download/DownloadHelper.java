package com.fcbox.lib.download;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Author:xz
 * Date:2019/11/8 10:25
 * Desc:
 */
public class DownloadHelper {
    public static String getFileMD5String(File file) {
        long t1 = System.currentTimeMillis();
        String md5 = "";

        MessageDigest messagedigest;
        InputStream fis = null;
        try {
            /*<Modify>-end:关闭资源;2017-03-07;雷管*/
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024*8];
            int numRead = 0;
            messagedigest = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
            fis.close();
            md5 = new String(Hex.encodeHex(messagedigest.digest()));
            return md5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*<Modify>-start:关闭资源;2017-03-07;雷管*/ finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*<Modify>-end:关闭资源;2017-03-07;雷管*/
        long t2 = System.currentTimeMillis();
        Log.i("UpdateHelper","md5:" + md5+" time: "+(t2-t1));
        return md5;
    }


    public static class Hex {

        /**
         * Used to build output as Hex
         */
        private final static char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
                'd', 'e', 'f'};

        /**
         * Used to build output as Hex
         */
        private final static char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                'D', 'E', 'F'};

        /**
         * Converts an array of bytes into an array of characters representing
         * the hexadecimal values of each byte in order. The returned array will
         * be double the length of the passed array, as it takes two characters
         * to represent any given byte.
         *
         * @param data a byte[] to convert to Hex characters
         * @return A char[] containing hexadecimal characters
         */
        public static char[] encodeHex(final byte[] data) {
            return encodeHex(data, true);
        }

        /**
         * Converts an array of bytes into an array of characters representing
         * the hexadecimal values of each byte in order. The returned array will
         * be double the length of the passed array, as it takes two characters
         * to represent any given byte.
         *
         * @param data        a byte[] to convert to Hex characters
         * @param toLowerCase <code>true</code> converts to lowercase,
         *                    <code>false</code> to uppercase
         * @return A char[] containing hexadecimal characters
         * @since 1.4
         */
        public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
            return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
        }

        /**
         * Converts an array of bytes into an array of characters representing
         * the hexadecimal values of each byte in order. The returned array will
         * be double the length of the passed array, as it takes two characters
         * to represent any given byte.
         *
         * @param data     a byte[] to convert to Hex characters
         * @param toDigits the output alphabet
         * @return A char[] containing hexadecimal characters
         * @since 1.4
         */
        protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
            final int l = data.length;
            final char[] out = new char[l << 1];
            // two characters form the hex value.
            for (int i = 0, j = 0; i < l; i++) {
                out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
                out[j++] = toDigits[0x0F & data[i]];
            }
            return out;
        }
    }

}
