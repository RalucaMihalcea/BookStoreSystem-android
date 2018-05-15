package com.example.raluca.storebooksystem.Activities.Activities;

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

}
