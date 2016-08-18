import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Fraser McIntosh on 18/08/2016.
 */
public class QuizLogic {
    private String _wordlist;
    public int _numWords;

    QuizLogic(String wordlist){
        _wordlist = wordlist;
        _numWords = numWords(wordlist);
    }


    public ArrayList<String> getWords(){

        ArrayList<String> words = new ArrayList<>();
        System.out.println(_numWords);

        for(int i = 0; i <_numWords; i++){
            if(i>2) {
                break;
            }
            String word = getWord();
            while(words.contains(word)){
                word = getWord();
            }
            words.add(word);
        }
        return words;
    }

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
        return numWords;
    }

    public String getWord(){
        BufferedReader in = null;
        String word = "";
        try {
            in = new BufferedReader(new FileReader(_wordlist));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int line =  new Random().nextInt((_numWords) + 1);

        int count = 0;
        while(count < line) {
            count++;
            try {
                word = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return word;
    }

}
