import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
        }
    }

    void addWord(String str) {

    }

    String randomWord(String str) {

    }

    public String toString() {

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

    }
}