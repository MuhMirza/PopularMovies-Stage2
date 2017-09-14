package id.muhmirza.popularmoviestage2.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ReviewResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<Review> results;

    public int getId() {
        return id;
    }

    public List<Review> getResults() {
        return results;
    }
}
