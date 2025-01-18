package study.refactoring.chapter1.statement;

import java.util.List;
import study.refactoring.chapter1.play.Play;
import study.refactoring.chapter1.play.PlayType;
import study.refactoring.chapter1.play.Plays;

public record EnrichedPerformance(String playID, int audience, Play play, double amount) {

    public static List<EnrichedPerformance> createFrom(final List<Performance> performances, final Plays plays) {
        return performances.stream()
                .map(performance -> createFrom(performance, plays))
                .toList();
    }

    private static EnrichedPerformance createFrom(final Performance performance, final Plays plays) {
        Play play = playFor(performance, plays);
        double amount = calculateAmount(performance, play);

        return new EnrichedPerformance(performance.playID(), performance.audience(), play,
                amount);
    }

    private static Play playFor(final Performance performance, final Plays plays) {
        return plays.get(performance.playID());
    }

    private static double calculateAmount(final Performance performance, final Play play) {
        double result;
        switch (play.type()) {
            case PlayType.tragedy -> {
                result = 40_000;
                if (performance.audience() > 30) {
                    result += 1_000 * (performance.audience() - 30);
                }
            }
            case PlayType.comedy -> {
                result = 30_000;
                if (performance.audience() > 20) {
                    result += 10_000 + 500 * (performance.audience() - 20);
                }
                result += 300 * performance.audience();
            }
            default -> throw new IllegalStateException("알 수 없는 장르: " + play.type());
        }
        return result;
    }
}
