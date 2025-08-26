/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iassure.util;

import com.iassure.exception.AppException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Naveen Kumar Chintala
 */
@Component
public class EncryptDecrypt {

    private static final String ALGORITHUM = "AES";
    private static final byte[] keyValue
            = new byte[]{'O', 'P', 'T', 'U', 'M', 'I', 'R',
                'M', '@', 'M', 'O', 'D', 'U', 'L', 'E', '$'};

    public String encrypt(String data) throws AppException {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHUM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            String encryptedValue = new Base64(true).encodeToString(encVal);
            return (encryptedValue).replaceAll("(\\r|\\n|\\t)", "");
        } catch (Exception e) {
            throw new AppException("Exception Occurred while Encrypting:" + e.getMessage());
        }
    }

    public String decrypt(String encryptedData) throws AppException {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHUM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.decodeBase64(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            return new String(decValue);
        } catch (Exception e) {
            throw new AppException("Exception Occurred while Decrypting:" + e.getMessage());
        }
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHUM);
        return key;
    }

    public static void main(String[] args) {
        try {
            EncryptDecrypt ed = new EncryptDecrypt();
            System.out.println(">>>>>" + ed.encrypt("30"));
            System.out.println(">>>>>" + ed.encrypt("57"));
            System.out.println(">>>>>" + ed.encrypt("36"));
            System.out.println(">>>>>" + ed.decrypt("wertaus0QkwSjsE4Xt9fWQ"));
            System.out.println(">>>>>" + ed.decrypt("WBjHMzE5YKf2w35z9AFxsw"));
            System.out.println(">>>>>" + ed.decrypt("wertaus0QkwSjsE4Xt9fWQ"));
        } catch (AppException ex) {
            Logger.getLogger(EncryptDecrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
