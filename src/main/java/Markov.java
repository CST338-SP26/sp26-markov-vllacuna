import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Markov {
    private static final String BEGINS_SENTENCE = "__$";
    private static final String PUNCTUATION_MARKS = ".!?$";
    private HashMap<String, ArrayList<String>> words;
    private String prevWord;

    public Markov() {
        words = new HashMap<>();
        words.put(BEGINS_SENTENCE, new ArrayList<>());
        prevWord = BEGINS_SENTENCE;
    }

    public String getSentence() {
        StringBuilder sb = new StringBuilder();

        String currentWord = randomWord(BEGINS_SENTENCE);

        if(currentWord == null) {
            return "";
        }

        while(!endsWithPunctuation(currentWord)) {
            sb.append(currentWord).append(" ");
            currentWord = randomWord(currentWord);
        }

        sb.append(currentWord);
        return sb.toString();
    }

    public void addFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner fs = new Scanner(file);
            while(fs.hasNextLine()) {
                addLine(fs.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + filename);
            System.out.println(e.getMessage());
        }
    }

    void addWord(String str) {
        if(str == null || str.isEmpty()) {
            return;
        }

        if(endsWithPunctuation(prevWord)) {
            words.get(BEGINS_SENTENCE).add(str);
        } else {
            if (!words.containsKey(prevWord)) {
                words.put(prevWord, new ArrayList<>());
            }
            words.get(prevWord).add(str);
        }

        prevWord = str;
    }

    String randomWord(String str) {
        ArrayList<String> wordList = words.get(str);
        if (wordList == null || wordList.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        int i = rand.nextInt(wordList.size());
        return wordList.get(i);
    }

    public String toString() {
        return words.toString();
    }

    HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    void addLine(String str) {
        if(str == null) {
            return;
        }

        if(str.isEmpty()) {
            return;
        }

        Scanner sc = new Scanner(str);
        while(sc.hasNext()) {
            addWord(sc.next());
        }
        sc.close();
    }

    public static boolean endsWithPunctuation(String str) {
        try {
            if(str == null || str.isEmpty()) {
                return false;
            }
            char lastCharOfWord = str.charAt(str.length()-1);
            return PUNCTUATION_MARKS.indexOf(lastCharOfWord) >= 0;
        } catch (Exception e) {
            System.out.println("Error checking at word: " + str);
            System.out.println(e.getMessage());
            return false;
        }
    }
}