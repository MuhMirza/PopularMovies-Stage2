package id.muhmirza.popularmoviestage2.data.event;

public class MovieErrorEvent extends BaseEvent {
    public MovieErrorEvent(String message) {
        super(message);
    }
}