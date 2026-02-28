import java.util.*;
public class java{
    private char[][] keyMatrix = new char[5][5];
    private Map<Character, int[]> charPositions = new HashMap<>();
    public java(String key) {
        generateKeyMatrix(key);
    }
    private void generateKeyMatrix(String key) {
        boolean[] used = new boolean[26];
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder();
        for (char c : key.toCharArray()) {
            if (!used[c - 'A']) {
                used[c - 'A'] = true;
                sb.append(c);
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            if (!used[c - 'A']) {
                sb.append(c);
            }
        }
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keyMatrix[i][j] = sb.charAt(index);
                charPositions.put(sb.charAt(index), new int[]{i, j});
                index++;
            }
        }
    }
    private String prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (i + 1 < text.length() && text.charAt(i) == text.charAt(i + 1)) {
                sb.append('X');
            }
        }
        if (sb.length() % 2 != 0) {
            sb.append('X');
        }
        return sb.toString();
    }
    public String encrypt(String text) {
        text = prepareText(text);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] posA = charPositions.get(a);
            int[] posB = charPositions.get(b);
            if (posA[0] == posB[0]) { 
                result.append(keyMatrix[posA[0]][(posA[1] + 1) % 5]);
                result.append(keyMatrix[posB[0]][(posB[1] + 1) % 5]);
            } else if (posA[1] == posB[1]) { 
                result.append(keyMatrix[(posA[0] + 1) % 5][posA[1]]);
                result.append(keyMatrix[(posB[0] + 1) % 5][posB[1]]);
            } else { 
                result.append(keyMatrix[posA[0]][posB[1]]);
                result.append(keyMatrix[posB[0]][posA[1]]);
            }
        }
        return result.toString();
    }
    public String decrypt(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);
            int[] posA = charPositions.get(a);
            int[] posB = charPositions.get(b);
            if (posA[0] == posB[0]) {   
                result.append(keyMatrix[posA[0]][(posA[1] + 4) % 5]);
                result.append(keyMatrix[posB[0]][(posB[1] + 4) % 5]);
            } else if (posA[1] == posB[1]) { 
                result.append(keyMatrix[(posA[0] + 4) % 5][posA[1]]);
                result.append(keyMatrix[(posB[0] + 4) % 5][posB[1]]);
            } else { 
                result.append(keyMatrix[posA[0]][posB[1]]);
                result.append(keyMatrix[posB[0]][posA[1]]);
            }
        }
        return result.toString();
    }
    public void printMatrix() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(keyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter key: ");
        String key = sc.nextLine();
        java pf = new java(key);
        System.out.println("\nKey Matrix:");
        pf.printMatrix();
        System.out.print("\nEnter plaintext: ");
        String plaintext = sc.nextLine();
        String encrypted = pf.encrypt(plaintext);
        System.out.println("Encrypted Text: " + encrypted);
        String decrypted = pf.decrypt(encrypted);
        System.out.println("Decrypted Text: " + decrypted);
        sc.close();
    }
}