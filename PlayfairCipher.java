import java.util.*;

public class PlayfairCipher {

    private char[][] matrix = new char[5][5];

    // Generate Playfair Matrix
    public void generateMatrix(String key) {
        key = key.toUpperCase().replace("J", "I");

        LinkedHashSet<Character> set = new LinkedHashSet<>();

        for (char ch : key.toCharArray()) {
            if (Character.isLetter(ch))
                set.add(ch);
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch == 'J')
                continue;
            set.add(ch);
        }

        Iterator<Character> it = set.iterator();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = it.next();
            }
        }
    }

    // Display Matrix
    public void displayMatrix() {
        System.out.println("Playfair Matrix:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Find position of a character
    private int[] findPosition(char ch) {
        if (ch == 'J')
            ch = 'I';

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == ch)
                    return new int[] { i, j };
            }
        }
        return null;
    }

    // Prepare Plaintext
    private String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        text = text.replace("J", "I");

        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < text.length()) {
            char a = text.charAt(i);

            if (i == text.length() - 1) {
                sb.append(a).append('X');
                break;
            }

            char b = text.charAt(i + 1);

            if (a == b) {
                sb.append(a).append('X');
                i++;
            } else {
                sb.append(a).append(b);
                i += 2;
            }
        }

        return sb.toString();
    }

    // Encryption
    public String encrypt(String plaintext) {
        plaintext = prepareText(plaintext);
        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = plaintext.charAt(i + 1);

            int[] p1 = findPosition(a);
            int[] p2 = findPosition(b);

            if (p1[0] == p2[0]) { // Same Row
                cipher.append(matrix[p1[0]][(p1[1] + 1) % 5]);
                cipher.append(matrix[p2[0]][(p2[1] + 1) % 5]);
            } else if (p1[1] == p2[1]) { // Same Column
                cipher.append(matrix[(p1[0] + 1) % 5][p1[1]]);
                cipher.append(matrix[(p2[0] + 1) % 5][p2[1]]);
            } else { // Rectangle
                cipher.append(matrix[p1[0]][p2[1]]);
                cipher.append(matrix[p2[0]][p1[1]]);
            }
        }

        return cipher.toString();
    }

    // Decryption
    public String decrypt(String cipher) {
        StringBuilder plain = new StringBuilder();

        for (int i = 0; i < cipher.length(); i += 2) {
            char a = cipher.charAt(i);
            char b = cipher.charAt(i + 1);

            int[] p1 = findPosition(a);
            int[] p2 = findPosition(b);

            if (p1[0] == p2[0]) { // Same Row
                plain.append(matrix[p1[0]][(p1[1] + 4) % 5]);
                plain.append(matrix[p2[0]][(p2[1] + 4) % 5]);
            } else if (p1[1] == p2[1]) { // Same Column
                plain.append(matrix[(p1[0] + 4) % 5][p1[1]]);
                plain.append(matrix[(p2[0] + 4) % 5][p2[1]]);
            } else { // Rectangle
                plain.append(matrix[p1[0]][p2[1]]);
                plain.append(matrix[p2[0]][p1[1]]);
            }
        }

        return plain.toString();
    }

    public static void main(String[] args) {

        PlayfairCipher pf = new PlayfairCipher();

        String key = "information";
        String plaintext = "the key is hidden under the door pad";

        pf.generateMatrix(key);
        pf.displayMatrix();

        String cipher = pf.encrypt(plaintext);
        System.out.println("\nPlaintext : " + plaintext);
        System.out.println("Ciphertext: " + cipher);

        String decrypted = pf.decrypt(cipher);
        System.out.println("Decrypted : " + decrypted);
    }
}