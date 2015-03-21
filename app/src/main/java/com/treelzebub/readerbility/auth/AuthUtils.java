package com.treelzebub.readerbility.auth;

import com.treelzebub.readerbility.Constants;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Tre Murillo on 3/18/15
 */
public class AuthUtils {

    public static String getNonce() {
        return md5(Long.toString(System.currentTimeMillis()));
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
         * 2) URL (url encoded)
         * 3) Parameter List (url encoded)
         */
        Base64 base64 = new Base64();

        String base = "GET&" + url + "&" + params;

        // append a "&" to the end of secret key. it's protocol, bitch.
        byte[] keyBytes = (Constants.CONSUMER_SECRET + "&").getBytes(Constants.ENCODING);

        SecretKey key = new SecretKeySpec(keyBytes, Constants.SIGNATURE_METHOD);

        Mac mac = Mac.getInstance(Constants.SIGNATURE_METHOD);
        mac.init(key);

        // encode it; base64 it; string it; return
        return new String(base64.encode(mac.doFinal(base.getBytes(
                Constants.ENCODING))), Constants.ENCODING).trim();
    }

    public static String sha1(String s, String keyString)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"),
                "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

        return new String(Base64.encodeBase64(bytes));
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // there is SoSuchAlgorithm.
        }
        return "";
    }

    public static String encodeForOAuth(String s) {
        // Sort the requests
        String[] params = s.split("&");
        Arrays.sort(params);

        // URL Encode Key and Values
        int j = 0;
        String postify = "";
        for (String aPar : params) {
            if (j == 1) postify += "&";

            String[] temp = aPar.split("=");
            postify += URLEncoder.encode(temp[0]) + "="
                    + URLEncoder.encode(temp[1]);
            j = 1;
        }

        return postify;
    }

}
