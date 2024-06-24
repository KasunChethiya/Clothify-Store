package edu.icet.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {

    public String encryption(String password){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            md5.update(password.getBytes());
            byte[] digest = md5.digest();

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                stringBuilder.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void decryption (String encryptedPassword){

    }
}
