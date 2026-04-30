package io.github.eggy03.application.services;

import io.github.eggy03.application.exception.KeyDigestException;
import io.github.eggy03.application.exception.KeyGenerationException;
import io.github.eggy03.application.exception.KeyReadException;
import io.github.eggy03.application.exception.KeySaveException;
import javax0.license3j.crypto.LicenseKeyPair;
import javax0.license3j.io.IOFormat;
import javax0.license3j.io.KeyPairReader;
import javax0.license3j.io.KeyPairWriter;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

public class LicenseKeyPairService {

    @NonNull
    public LicenseKeyPair generateKeyPair(@NonNull String cipher, int size) {
        try {
            return LicenseKeyPair.Create.from(cipher, size);
        } catch (NoSuchAlgorithmException e) {
            throw new KeyGenerationException("Incompatible Cipher", e);
        }
    }

    @NonNull
    public LicenseKeyPair loadKeyPair(@NonNull File privateKeyFile, @NonNull File publicKeyFile, @NonNull IOFormat keyFormat) {

        Objects.requireNonNull(privateKeyFile, "privateKeyFile cannot be null");
        Objects.requireNonNull(publicKeyFile, "publicKeyFile cannot be null");
        Objects.requireNonNull(keyFormat, "keyFormat cannot be null");

        if(!privateKeyFile.isFile())
            throw new KeyReadException("Provided privateKeyFile is not a file");

        if(!publicKeyFile.isFile())
            throw new KeyReadException("Provided publicKeyFile is not a file");

        try(KeyPairReader privateKeyReader = new KeyPairReader(privateKeyFile); KeyPairReader publicKeyReader = new KeyPairReader(publicKeyFile)) {

            LicenseKeyPair privateKeyPair = privateKeyReader.readPrivate(keyFormat);
            LicenseKeyPair publicKeyPair = publicKeyReader.readPublic(keyFormat);

            // check for cipher match
            if(!privateKeyPair.cipher().equals(publicKeyPair.cipher()))
                throw new KeyReadException("Cypher Mismatch! Private Key has: "+ privateKeyPair.cipher()+" while Public Key has: "+publicKeyPair.cipher());

            final String cipher = privateKeyPair.cipher();
            PrivateKey privateKey = Objects.requireNonNull(privateKeyPair.getPair().getPrivate(), "privateKey cannot be null");
            PublicKey publicKey = Objects.requireNonNull(publicKeyPair.getPair().getPublic(), "publicKey cannot be null");

            return LicenseKeyPair.Create.from(publicKey, privateKey, cipher);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new KeyReadException("Key read failure", e);
        }

    }

    public void saveKeys(@NonNull LicenseKeyPair keyPair, @NonNull IOFormat keyFormat, @NonNull String privateKeyName, @NonNull String publicKeyName, @NonNull File keyFolder) {

        Objects.requireNonNull(keyPair, "keyPair cannot be null");
        Objects.requireNonNull(keyFormat, "keyFormat cannot be null");

        Objects.requireNonNull(privateKeyName, "privateKeyName cannot be null");
        Objects.requireNonNull(publicKeyName, "publicKeyName cannot be null");

        Objects.requireNonNull(keyFolder, "keyFolder cannot be null");

        if(!keyFolder.isDirectory())
            throw new KeySaveException("Provided key folder:" + keyFolder.getPath() + "is not a directory");

        if(!privateKeyName.matches("^[a-zA-Z0-9._-]+$"))
            throw new KeySaveException("Private Key name:" + privateKeyName + "contains invalid characters");

        if(!publicKeyName.matches("^[a-zA-Z0-9._-]+$"))
            throw new KeySaveException("Public Key name:" + publicKeyName + "contains invalid characters");

        try(KeyPairWriter keyPairWriter = new KeyPairWriter(new File(keyFolder, privateKeyName), new File(keyFolder, publicKeyName))) {
            keyPairWriter.write(keyPair, keyFormat);
        } catch (IOException e) {
            throw new KeySaveException("Key save failure", e);
        }
    }

    @NonNull
    public String digestPublicKey(@NonNull LicenseKeyPair keyPair) {
        Objects.requireNonNull(keyPair, "keyPair cannot be null");

        try {
            byte[] publicKey = keyPair.getPublic();
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            byte[] calculatedDigest = digest.digest(publicKey);

            StringBuilder javaCode = new StringBuilder("--KEY DIGEST START\nbyte [] digest = new byte[] {\n");
            for (int i = 0; i < calculatedDigest.length; i++) {
                int intVal = (calculatedDigest[i]) & 0xff;
                javaCode.append(String.format("(byte)0x%02X, ", intVal));
                if (i % 8 == 0) {
                    javaCode.append("\n");
                }
            }
            javaCode.append("\n};\n---KEY DIGEST END\n");

            javaCode.append("--KEY START\nbyte [] key = new byte[] {\n");
            for (int i = 0; i < publicKey.length; i++) {
                int intVal = (publicKey[i]) & 0xff;
                javaCode.append(String.format("(byte)0x%02X, ", intVal));
                if (i % 8 == 0) {
                    javaCode.append("\n");
                }
            }
            javaCode.append("\n};\n---KEY END\n");

            return javaCode.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new KeyDigestException("Public key digestion failure", e);
        }
    }
}
