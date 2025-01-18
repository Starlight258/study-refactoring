package study.refactoring.chapter1.statement;

import java.util.List;
import study.refactoring.chapter1.play.Play;
import study.refactoring.chapter1.play.Plays;

public record EnrichedPerformance(String playID, int audience, Play play) {

    public static List<EnrichedPerformance> createFrom(final List<Performance> performances, final Plays plays) {
        return performances.stream()
                .map(performance -> new EnrichedPerformance(
                        performance.playID(), performance.audience(), playFor(performance, plays)))
                .toList();
    }

    private static Play playFor(final Performance performance, final Plays plays) {
        return plays.get(performance.playID());
    }
}
