import java.io.*;
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
    public String _currentWord = "";

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
        System.out.println("There are: " +_numWordsInFile+ " words in the file");

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
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int numWords = 0;
        try {
            while(in.readLine()!=null) {
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
        Scanner in = new Scanner(new FileReader(_wordlist));
        String word = "";
        int line =  new Random().nextInt((_numWordsInFile)) + 1;
        System.out.println("Random Line number: " + line);
        int count = 0;
        while(count < line && in.hasNextLine()) {
            count++;
            word = in.nextLine();
            if(word.equals("") && in.hasNextLine()) {
                word = in.nextLine();
            }
        }
        System.out.println("Getting word: " +word);
        return word;
    }

    /*
     * Returns the next word from the words array
     */
    public String nextWord() {
        if(_currentWordNumber < _numWordsInQuiz) {
            _currentWordNumber++;
            System.out.println(_currentWordNumber + " " + _numWordsInQuiz);
            if(_currentWordNumber == _numWordsInQuiz) {
                isLastAttempt = true;
            }
           _currentWord = _wordsInQuiz.get(_currentWordNumber - 1);
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
        FileLogic.addUniqueWord(FileLogic.attemptedlist, _currentWord);
        System.out.println("Checking answer for: " + _wordsInQuiz.get(_currentWordNumber - 1));
        if (attempt.equals(_wordsInQuiz.get(_currentWordNumber - 1))) {
            // If correct then can't be on second attempt
            if (isSecondAttempt) {
                FileLogic.addWord(FileLogic.faulted_stats, _currentWord);
            } else {
                FileLogic.addWord(FileLogic.mastered_stats, _currentWord);
                System.out.println("Removing word: "+ _currentWord);
                FileLogic.removeWord(FileLogic.reviewlist, _currentWord);
            }
            isSecondAttempt = false;
            return true;
        } else {
            FileLogic.addUniqueWord(FileLogic.reviewlist, _currentWord);
            if (isSecondAttempt) {
                FileLogic.addWord(FileLogic.failed_stats, _currentWord);
            }
            return false;
        }
    }


    public void sayWord(String word) {
        System.out.println(word);
        String cmd = "echo " + word + " | festival --tts";
//        try {
//            new ProcessBuilder("/bin/bash", "-c", cmd).start();
//        } catch (java.io.IOException e) {
//            e.printStackTrace();
//        }
    }

    public void spellOutWord() {
        String[] wordSplit = _currentWord.split("");
        String word = "";
        for(String s : wordSplit){
            word+= s + " ";
        }
        System.out.println(word);
        sayWord(word);
    }
}
