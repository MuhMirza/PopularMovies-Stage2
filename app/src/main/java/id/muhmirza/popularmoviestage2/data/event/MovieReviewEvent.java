package id.muhmirza.popularmoviestage2.data.event;


import id.muhmirza.popularmoviestage2.data.model.ReviewResponse;

public class MovieReviewEvent extends BaseEvent {
    private ReviewResponse body;

    public MovieReviewEvent(String message, ReviewResponse body) {
        super(message);
        this.body = body;
    }

    public ReviewResponse getBody() {
        return body;
    }
}