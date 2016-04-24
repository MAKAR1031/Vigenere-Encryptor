package encript;

import java.util.ArrayList;
import java.util.List;

public class VigenereEncryptor {
    public enum Actions {
        ENCRYPT, DECRYPT;
    }

    private final String FULL_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private List<String> alphabets;
    private String keyWord;
    private int offset;

    public VigenereEncryptor() {
        this.offset = 0;
        this.keyWord = "";
    }
    
    private void init(String keyWord, int offset) {
        this.keyWord = keyWord;
        this.offset = offset;
        alphabets = new ArrayList<>();
        for (int i = 0; i < offset; i++) {
            String part1 = FULL_ALPHABET.split(Character.toString(keyWord.charAt(i)))[1];
            String part2 = FULL_ALPHABET.split(Character.toString(keyWord.charAt(i)))[0];
            alphabets.add(keyWord.charAt(i) + part1 + part2);
        }
    }
    
    public void setParameters(String keyWord, int offset) {
        init(keyWord, offset);
    }

    public String getKeyWord() {
        return keyWord;
    }

    public int getOffset() {
        return offset;
    }

    public String convertText(String source, Actions action) {
        StringBuilder result = new StringBuilder();
        int currentChar = 0;
        for (char symbol : source.toCharArray()) {
            symbol = Character.toLowerCase(symbol);
            if (!FULL_ALPHABET.contains(Character.toString(symbol))) {
                result.append(symbol);
                continue;
            }
            String currentAlphabet = alphabets.get(currentChar % offset);
            switch (action) {
                case ENCRYPT:
                    result.append(currentAlphabet.charAt(FULL_ALPHABET.indexOf(symbol)));
                    break;
                case DECRYPT:
                    result.append(FULL_ALPHABET.charAt(currentAlphabet.indexOf(symbol)));
                    break;
            }
            currentChar++;
        }
        return result.toString();
    }
}