package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {
    // Constants
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    // Method to generate salt
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    // Method to hash a password with a salt using PBKDF2
    public static String hashPassword(String password) throws Exception {
        byte[] salt = generateSalt();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        // Combine salt and hash for storage (salt:hash)
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);
        return saltBase64 + ":" + hashBase64; // Store in format: salt:hash
    }

    // Method to compare the entered password with the stored hashed password
    public static boolean verifyPassword(String inputPassword, String storedPassword) throws Exception {
        // Split the stored password into salt and hash components
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Stored password is in an invalid format.");
        }

        String saltBase64 = parts[0];
        String storedHashBase64 = parts[1];

        byte[] salt = Base64.getDecoder().decode(saltBase64);
        byte[] storedHash = Base64.getDecoder().decode(storedHashBase64);

        // Hash the input password with the same salt
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec spec = new PBEKeySpec(inputPassword.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        byte[] inputHash = factory.generateSecret(spec).getEncoded();

        // Compare the hashed input password with the stored hash
        return java.util.Arrays.equals(storedHash, inputHash);
    }
}
