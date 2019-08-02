package be.hackinthewoods.childfocus.backend.ws.model;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import org.springframework.util.Assert;

public class VolunteerAction {

    private Type type;
    private Poster poster;

    private VolunteerAction() {}

    private VolunteerAction(Type type, Poster poster) {
        this.type = type;
        this.poster = poster;
    }

    public static VolunteerAction put(Poster poster) {
        Assert.notNull(poster, "The poster mustn't be null");
        return new VolunteerAction(Type.PUT, poster);
    }

    public static VolunteerAction remove(Poster poster) {
        Assert.notNull(poster, "The poster mustn't be null");
        return new VolunteerAction(Type.REMOVE, poster);
    }

    public Type getType() {
        return type;
    }

    public Poster getPoster() {
        return poster;
    }

    public enum Type {
        PUT, REMOVE
    }
}
