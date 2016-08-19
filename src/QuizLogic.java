import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.jar.Pack200;

/**
 * Created by Fraser McIntosh on 18/08/2016.
 */
public class QuizLogic {
    private String _wordlist;
    public int _numWordsInFile;
    public int _currentWordNumber = 0;
    public ArrayList<String> _wordsInQuiz;
    public int _numWordsInQuiz;
    public boolean _hasWords;
    public boolean isSecondAttempt = false;
    public boolean isLastAttempt = false;

    QuizLogic(String wordlist){
        _wordlist = wordlist;
        _numWordsInFile = numWords(wordlist);
        _wordsInQuiz = pickWords();

    }

    /*
     * Generates a list of words (up to 3)
     */
    public ArrayList<String> pickWords(){
        ArrayList<String> words = new ArrayList<>();
        System.out.println(_numWordsInFile);

        for(int i = 0; i <_numWordsInFile; i++){
            if(i>2) {
                break;
            }
            try {
                String word = getWord();
                while(words.contains(word) || word.equals("")){
                    word = getWord();
                }
                words.add(word);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        _numWordsInQuiz = words.size();
        return words;
    }

    /*
     * Loops through a file and finds how many lines there are
     */
    public int numWords(String file){
        // Get number of lines in list
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(_wordlist));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int numWords = 0;
        try {
            while(in.readLine()!=null) {
                in.readLine();
                numWords++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Check that there are words
        if(numWords != 0) {
            _hasWords= true;
        }
        return numWords;
    }

    /*
     * Helper function to pick a random line from a file
     */
    private String getWord() throws Exception{
        Scanner in = new Scanner(new FileReader("wordlist.txt"));
        String word = "";
        int line =  new Random().nextInt((_numWordsInFile) + 1) + 1;
        int count = 1;
        while(count < line) {
            count++;
            word = in.nextLine();
            in.nextLine();
        }
        System.out.println(word);
        return word;
    }

    /*
     * Returns the next word from the words array
     */
    public String nextWord() {
        if(_currentWordNumber <= _numWordsInQuiz) {
            _currentWordNumber++;
            if(_currentWordNumber == _numWordsInQuiz) {
                isLastAttempt = true;
            }
            return _wordsInQuiz.get(_currentWordNumber - 1);
        } else {
            return "";
        }
    }

    /*
     * Takes a string and checks if the string is the current word
     * Updates state to check if it is the second attempt
     */
    public boolean checkAnswer(String attempt) {
        System.out.println(_wordsInQuiz.get(_currentWordNumber));
        if (attempt.equals(_wordsInQuiz.get(_currentWordNumber))) {
            // If correct then can't be on second attempt
            isSecondAttempt = false;
            return true;
        } else {
            return false;
        }
    }

}
