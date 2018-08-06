/**
 @author flora jha on 27/05/17
 */
package application.android.flora.grewordmadeeasy.word;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.StringUtils;

public class WordPair {
    //private variables
    @SerializedName("word")
    String word = "";
    @SerializedName("meaning")
    String meaning = "";
    private int index;
    private int score;

    public WordPair() {
    }

    public WordPair(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        this.index = -1;
        this.score = 0;
    }

    // getter & setter Word
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaningForDisplay() {
        return StringUtils.capitalize(meaning);
    }

    // getter & setter Meaning
    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    // getter & setter Index
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    // getter & setter Score
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
// vim: set ts=4 sw=4 tw=0 et :
