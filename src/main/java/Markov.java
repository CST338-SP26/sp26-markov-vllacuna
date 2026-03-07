import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Title: Markov.java
 * Abstract: A program that reads in a text file, collects every word in
 *           said file and which word follows each word using a HashMap.
 *           Creates a new sentence based on it.
 * Author: Von Andre Llacuna
 * Date: 03/06/26
 */

public class Markov {
    //constants
    private static final String BEGINS_SENTENCE = "__$";
    private static final String PUNCTUATION_MARKS = ".!?$";

    // members
    private HashMap<String, ArrayList<String>> words;
    private String prevWord;

    //METHODS

    /**
     * Constructor for Markov.
     *
     * Initializes words HashMap, sets BEGINS_SENTENCE key to an ArrayList,
     * and sets prevWord to BEGINS_SENTENCE constant.
     */
    public Markov() {
        words = new HashMap<>();
        words.put(BEGINS_SENTENCE, new ArrayList<>());
        prevWord = BEGINS_SENTENCE;
    }

    /**
     * Method for generating a sentence
     *
     * Begins with randomly selecting a sentence starting word and repeatedly
     * choosing a random word that follows until it selects a word that
     * ends with a punctuation.
     *
     * @return a string that contains the generated sentence
     */
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

    /**
     * Method for reading a file and adding each line of text to program.
     *
     * @param filename the path for the text file
     */
    public void addFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner fs = new Scanner(file);
            while(fs.hasNextLine()) {
                addLine(fs.nextLine());
            }
            fs.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + filename);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a word to the Markov system.
     *
     * Adds the word as a possible following word for the prevWord.
     * If the previous word ends a sentence, this word is put as a possible starter.
     *
     * @param str the word that is being added
     */
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

    /**
     * Picks a random word that follows the current word.
     *
     * @param str the key for the list of words that may follow
     * @return a random word that follows the given word, or null if none exist
     */
    String randomWord(String str) {
        ArrayList<String> wordList = words.get(str);
        if (wordList == null || wordList.isEmpty()) {
            return null;
        }

        Random rand = new Random();
        int i = rand.nextInt(wordList.size());
        return wordList.get(i);
    }

    /**
     * Returns a string that represents the HashMap
     *
     * @return word HashMap as a string
     */
    public String toString() {
        return words.toString();
    }

    /**
     * Returns the map that has all the words as keys with the value being
     * an ArrayList of all the possible following words.
     *
     * @return the HashMap of all the words, and possible following words
     */
    HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    /**
     * Gets a line of text and separates each word. Each word is then put into
     * addWord().
     *
     * @param str the line of text that should be separated
     */
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

    /**
     * Method for figuring out if a word ends with ".!?$".
     *
     * @param str the word to check if it ends with punctuation
     * @return returns true if it ends with punctuation, false otherwise
     */
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