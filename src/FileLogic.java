import java.io.*;
import java.util.ArrayList;

/**
 * Created by Fraser McIntosh on 18/08/2016.
 */
public class FileLogic {
    static String attemptedlist = ".attemptedlist.txt";
    static String reviewlist = ".reviewlist.txt";
    static String mastered_stats = ".mastered_stats.txt";
    static String faulted_stats = ".faulted_stats.txt";
    static String failed_stats = ".failed_stats.txt";
    private static String[] files = {attemptedlist, reviewlist, mastered_stats, faulted_stats, failed_stats};

    static String wordlist = "wordlist.txt";

    public static void createFiles() {
        for (String filename : files) {
            File f = new File(filename);
            if (!f.isFile()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void clearFiles() {
        // Loop through each file and if it exists clear it
        for (String filename : files) {
            File f = new File(filename);
            if (f.isFile()) {
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(f);
                    // writer.print("");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    writer.close();
                }
            }
        }
    }

    public static void addUniqueWord(String file, String word){
        if(!containsWord(file, word)){
            addWord(file, word);
        }
    }

    public static void addWord(String file, String word){
        PrintWriter output;
        try {
            output = new PrintWriter(new FileWriter(file, true));
            output.println(word);
            output.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }




    public static boolean containsWord(String file, String word){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String currentWord=in.readLine();
            while(currentWord!=null) {
                if(word.equals(currentWord)){
                    return true;
                }
                currentWord= in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeWord(String file, String word){
        // I got this method from http://stackoverflow.com/questions/1377279/find-a-line-in-a-file-and-remove-it
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            ArrayList<String> lines = new ArrayList<>();

            String lineToRemove = word;
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(lineToRemove)) continue;
                lines.add(currentLine);
            }
            PrintWriter output = new PrintWriter(new FileWriter(file));
            for(String s : lines) {
                output.println(s);
            }
            reader.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static int countOccurences(String file, String word) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int count = 0;
        try {
            String currentWord=in.readLine();
            while(currentWord!=null) {
                if(word.equals(currentWord)){
                    count++;
                }
                currentWord= in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}