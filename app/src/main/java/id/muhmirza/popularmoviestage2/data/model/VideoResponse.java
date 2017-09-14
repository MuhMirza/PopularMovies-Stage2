package id.muhmirza.popularmoviestage2.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    @SerializedName("id")
    private int movieId;

    @SerializedName("results")
    private List<Video> results;

    public int getMovieId() {
        return movieId;
    }

    public List<Video> getResults() {
        return results;
    }
}
