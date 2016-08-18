import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Fraser McIntosh on 18/08/2016.
 */
public class FileLogic {

    public static void createFiles(String[] files) {
        for (String filename : files){
            File f = new File(filename);
            if(f.isFile()){
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
}
