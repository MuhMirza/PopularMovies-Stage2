package id.muhmirza.popularmoviestage2.data.event;


import id.muhmirza.popularmoviestage2.data.model.VideoResponse;

public class MovieTrailerEvent extends BaseEvent {
    private VideoResponse body;

    public MovieTrailerEvent(String message, VideoResponse body) {
        super(message);
        this.body = body;
    }

    public VideoResponse getBody() {
        return body;
    }
}