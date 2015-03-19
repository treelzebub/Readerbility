package com.treelzebub.readerbility.auth;

import com.treelzebub.readerbility.Constants;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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

    public static String getSignature(String url, String params)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {
        /**
         * base has three parts, all connected by "&"
         * 1) protocol
         * 2) URL *
         * 3) Parameter List *
         *
         *       * needs to be URLEncoded
         */

        Base64 base64 = new Base64();

        StringBuilder base = new StringBuilder()
                .append("GET&")
                .append(url)
                .append("&")
                .append(params);

        // append a "&" to the end of secret key.
        byte[] keyBytes = (Constants.CONSUMER_SECRET + "&").getBytes(Constants.ENCODING);

        SecretKey key = new SecretKeySpec(keyBytes, Constants.SIGNATURE_METHOD);

        Mac mac = Mac.getInstance(Constants.SIGNATURE_METHOD);
        mac.init(key);

        // encode it; base64 it; trim+string it; return
        return new String(base64.encode(mac.doFinal(base.toString().getBytes(
                Constants.ENCODING))), Constants.ENCODING).trim();
    }
}
