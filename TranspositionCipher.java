import java.util.*;

public class TranspositionCipher {

    // Remove spaces and convert to lowercase
    static String prepareText(String text) {
        return text.replaceAll("\\s+", "").toLowerCase();
    }

    // Find inverse (decryption) key
    static int[] inverseKey(int[] key) {
        int[] inverse = new int[key.length];
        for (int i = 0; i < key.length; i++) {
            inverse[key[i] - 1] = i + 1;
        }
        return inverse;
    }

    // ---------------- COLUMN TRANSPOSITION ----------------
    static String columnEncrypt(String text, int[] key) {
        text = prepareText(text);

        int cols = key.length;
        int rows = (int) Math.ceil((double) text.length() / cols);

        char[][] matrix = new char[rows][cols];

        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (k < text.length())
                    matrix[i][j] = text.charAt(k++);
                else
                    matrix[i][j] = 'X';
            }
        }

        StringBuilder cipher = new StringBuilder();

        for (int num = 1; num <= cols; num++) {
            int col = 0;
            for (int j = 0; j < cols; j++) {
                if (key[j] == num) {
                    col = j;
                    break;
                }
            }

            for (int i = 0; i < rows; i++)
                cipher.append(matrix[i][col]);
        }

        return cipher.toString();
    }

    static String columnDecrypt(String cipher, int[] key) {

        int cols = key.length;
        int rows = cipher.length() / cols;

        char[][] matrix = new char[rows][cols];

        int k = 0;
        for (int num = 1; num <= cols; num++) {

            int col = 0;
            for (int j = 0; j < cols; j++) {
                if (key[j] == num) {
                    col = j;
                    break;
                }
            }

            for (int i = 0; i < rows; i++)
                matrix[i][col] = cipher.charAt(k++);
        }

        StringBuilder plain = new StringBuilder();

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                plain.append(matrix[i][j]);

        return plain.toString();
    }

    // ---------------- ROW TRANSPOSITION ----------------
    static String rowEncrypt(String text, int[] key) {
        text = prepareText(text);

        int rows = key.length;
        int cols = (int) Math.ceil((double) text.length() / rows);

        char[][] matrix = new char[rows][cols];

        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (k < text.length())
                    matrix[i][j] = text.charAt(k++);
                else
                    matrix[i][j] = 'X';
            }
        }

        StringBuilder cipher = new StringBuilder();

        for (int num = 1; num <= rows; num++) {

            int row = 0;
            for (int i = 0; i < rows; i++) {
                if (key[i] == num) {
                    row = i;
                    break;
                }
            }

            for (int j = 0; j < cols; j++)
                cipher.append(matrix[row][j]);
        }

        return cipher.toString();
    }

    static String rowDecrypt(String cipher, int[] key) {

        int rows = key.length;
        int cols = cipher.length() / rows;

        char[][] matrix = new char[rows][cols];

        int k = 0;

        for (int num = 1; num <= rows; num++) {

            int row = 0;
            for (int i = 0; i < rows; i++) {
                if (key[i] == num) {
                    row = i;
                    break;
                }
            }

            for (int j = 0; j < cols; j++)
                matrix[row][j] = cipher.charAt(k++);
        }

        StringBuilder plain = new StringBuilder();

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                plain.append(matrix[i][j]);

        return plain.toString();
    }

    public static void main(String[] args) {

        String plaintext = "attack postponed until two am";

        int[] columnKey = {3,4,2,1,5,6,7};
        int[] rowKey = {4,2,3,1};

        System.out.println("Original Plaintext : " + plaintext);

        // Column Transposition
        String columnCipher = columnEncrypt(plaintext, columnKey);
        System.out.println("\nColumn Cipher      : " + columnCipher);

        int[] columnDecryptKey = inverseKey(columnKey);
        System.out.println("Column Decrypt Key : " + Arrays.toString(columnDecryptKey));

        String columnPlain = columnDecrypt(columnCipher, columnKey);
        System.out.println("Column Decryption  : " + columnPlain);

        // Row Transposition
        String rowCipher = rowEncrypt(plaintext, rowKey);
        System.out.println("\nRow Cipher         : " + rowCipher);

        int[] rowDecryptKey = inverseKey(rowKey);
        System.out.println("Row Decrypt Key    : " + Arrays.toString(rowDecryptKey));

        String rowPlain = rowDecrypt(rowCipher, rowKey);
        System.out.println("Row Decryption     : " + rowPlain);
    }
}