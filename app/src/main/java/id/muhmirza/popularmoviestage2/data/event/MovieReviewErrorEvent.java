package id.muhmirza.popularmoviestage2.data.event;

public class MovieReviewErrorEvent extends BaseEvent {
    public MovieReviewErrorEvent(String message) {
        super(message);
    }
}