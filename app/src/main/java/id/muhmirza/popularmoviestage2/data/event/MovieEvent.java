package id.muhmirza.popularmoviestage2.data.event;

import java.util.List;

import id.muhmirza.popularmoviestage2.data.model.Movie;
import id.muhmirza.popularmoviestage2.data.util.Constant;


public class MovieEvent extends BaseEvent {
    private final List<Movie> results;
    private final Constant.Data.MOVIE_LIST_TITLE movieListTitle;

    public MovieEvent(String message, List<Movie> results, Constant.Data.MOVIE_LIST_TITLE movieListTitle) {
        super(message);
        this.results = results;
        this.movieListTitle = movieListTitle;
    }

    public List<Movie> getResults() {
        return results;
    }

    public Constant.Data.MOVIE_LIST_TITLE getMovieListTitle() {
        return movieListTitle;
    }
}