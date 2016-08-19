import javafx.beans.property.StringProperty;

/**
 * Created by Fraser McIntosh on 20/08/2016.
 */
public class WordStatistic {
    private String _word;
    private int _faulted;
    private int _failed;
    private int _mastered;

    WordStatistic(String word) {
        _word = word;
        _faulted = FileLogic.countOccurences(FileLogic.faulted_stats, word);
        _failed = FileLogic.countOccurences(FileLogic.failed_stats, word);
        _mastered = FileLogic.countOccurences(FileLogic.mastered_stats, word);
    }

    public String getWord() {
        return _word;
    }

    public void setWord(String word) {
        _word = word;
    }

    public int getFaulted() {
        return _faulted;
    }

    public void setFaulted(int faulted) {
        _faulted = faulted;
    }

    public int getFailed() {
        return _failed;
    }

    public void setFailed(int failed) {
        _failed = failed;
    }

    public int getMastered() {
        return _mastered;
    }

    public void setMastered(int mastered) {
        _mastered = mastered;
    }
}
