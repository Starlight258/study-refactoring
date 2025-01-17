package study.refactoring.chapter1.play;

import java.util.Map;

public class Plays {

    private final Map<String, Play> plays;

    public Plays(final Map<String, Play> plays) {
        this.plays = plays;
    }

    public Play get(final String key) {
        return plays.get(key);
    }
}
