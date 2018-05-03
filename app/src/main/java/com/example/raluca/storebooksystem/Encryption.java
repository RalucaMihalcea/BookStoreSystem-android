package com.example.raluca.storebooksystem;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Raluca on 03.05.2018.
 */

public class Encryption {

    public static String MD5(String input) {

        try {

            MessageDigest digest = MessageDigest.getInstance("MD5");

            byte[] messageDigest = digest.digest(input.getBytes());

            BigInteger number = new BigInteger(1, messageDigest);
            String hashText = number.toString(16);
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

//    public static boolean matching(String orig, String compare) {
//        String md5 = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(compare.getBytes());
//            byte[] digest = md.digest();
//            md5 = new BigInteger(1, digest()).toString(16);
//
//            return md5.equals(orig);
//
//        } catch (NoSuchAlgorithmException e) {
//            return false;
//        }
//
//        return false;
//    }

//    private String  bytesToHex(byte[] hash) {
//
//        return DatatypeConverter.printHexBinary(hash);
//
//    }


}
