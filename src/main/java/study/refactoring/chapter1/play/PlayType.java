package study.refactoring.chapter1.play;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PlayType {

    TRAGEDY, COMEDY;

    @JsonValue
    public String toLowerCase() {
        return name().toLowerCase();
    }
}
