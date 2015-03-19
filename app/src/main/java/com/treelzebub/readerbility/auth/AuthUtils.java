package com.treelzebub.readerbility.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tre Murillo on 3/18/15
 */
public class AuthUtils {

    public static String getNonce() {

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            digest.update(Double.toString(System.currentTimeMillis() + Math.random()).getBytes());
            StringBuffer hexString = new StringBuffer();
            byte messageDigest[] = digest.digest();
            for (byte aMessageDigest : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // not happening.
        }
        return null;
    }

    public static String getTimestamp() {
        return Long.toString(System.currentTimeMillis());
    }
}
