package be.hackinthewoods.childfocus.backend.controller.api.model;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import org.springframework.util.Assert;

public class PosterAction {

    private Type type;
    private Poster poster;

    private PosterAction() {}

    private PosterAction(Type type, Poster poster) {
        this.type = type;
        this.poster = poster;
    }

    public static PosterAction put(Poster poster) {
        Assert.notNull(poster, "The poster mustn't be null");
        return new PosterAction(Type.PUT, poster);
    }

    public static PosterAction remove(Poster poster) {
        Assert.notNull(poster, "The poster mustn't be null");
        return new PosterAction(Type.REMOVE, poster);
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
