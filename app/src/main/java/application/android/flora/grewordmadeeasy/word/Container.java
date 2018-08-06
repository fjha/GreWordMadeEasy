package application.android.flora.grewordmadeeasy.word;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class Container {
    @SerializedName("all") Inner all;

    public List<WordPair> getAll() {
        return all.pairs;
    }

    public static class Inner {
        @SerializedName("pair")
        List<WordPair> pairs;
    }
}
