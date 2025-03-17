package com.mikewarren.ezsoftwaresolutionsinterview.utils

import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.security.SecureRandom

/**
 * Code adapted from: https://groovy.apache.org/blog/encryption-and-decryption-with-groovy
 */
public class EncryptDecryptHelper {
    private static EncryptDecryptHelper instance;

    public static final String KeyFileName = 'src/main/resources/key.dat';
    private static final String encryptionAlgorithm = 'AES';

    private SecretKey key;
    private ivParameterSpec;

    public static EncryptDecryptHelper GetInstance() {
        if (!instance) {
            instance = new EncryptDecryptHelper()
        }

        return instance;
    }

    private EncryptDecryptHelper() {
        // attempt to load the key in, and create it if we can't find it
        File keyFile = new File(KeyFileName);
        if (keyFile.exists()) {
            String[] keyAndIV = loadKeyAndInitVector(keyFile)
            byte[] decodedKey = Base64.getDecoder().decode(keyAndIV[0])
            byte[] decodedIV = Base64.getDecoder().decode(keyAndIV[1])

            key = new SecretKeySpec(decodedKey, 0, decodedKey.length, encryptionAlgorithm);
            this.ivParameterSpec = new IvParameterSpec(decodedIV);

            return;
        }

        key = generateKey(256);
        this.ivParameterSpec = randomParameterSpec();
        keyFile.withOutputStream({ outputStream ->
            outputStream.write(Base64.getEncoder().encode(key.encoded));
            outputStream.write(':'.getBytes());
            outputStream.write(Base64.getEncoder().encode(ivParameterSpec.getIV()));
        })
    }

    private String[] loadKeyAndInitVector(File keyFile) {
        String content = ''
        keyFile.withReader { reader ->
            content = reader.text
        }
        return content.split(':')
    }

    private SecretKey generateKey(Integer size) {
        KeyGenerator generator = KeyGenerator.getInstance(encryptionAlgorithm)
        generator.init(size)
        return generator.generateKey()
    }


    private IvParameterSpec randomParameterSpec() {
        byte[] block = new byte[getCipher().blockSize]
        SecureRandom.instanceStrong.nextBytes(block)
        return new IvParameterSpec(block)
    }

    private Cipher getCipher() {
        return Cipher.getInstance('AES/CBC/PKCS5Padding');
    }

    public String encrypt(String plaintext) {
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.bytes));
    }

    public String decrypt(String ciphertext) {
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)));
    }
}
