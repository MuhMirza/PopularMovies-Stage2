package id.muhmirza.popularmoviestage2.data.event;

public class MovieTrailerErrorEvent extends BaseEvent {
    public MovieTrailerErrorEvent(String message) {
        super(message);
    }
}