import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created by Fraser McIntosh on 18/08/2016.
 */
public class FileLogic {

    private static String[] files = {"attemptedlist.txt", "reviewlist.txt", "mastered_stats.txt", "faulted_stats.txt", "failed_stats.txt"};

    public static void createFiles() {
        for (String filename : files) {
            File f = new File(filename);
            if (f.isFile()) {
                //do nothing
            } else {
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
}
