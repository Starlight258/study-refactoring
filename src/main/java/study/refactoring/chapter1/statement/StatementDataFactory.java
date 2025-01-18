package study.refactoring.chapter1.statement;

import java.util.List;
import study.refactoring.chapter1.play.Play;
import study.refactoring.chapter1.play.PlayType;
import study.refactoring.chapter1.play.Plays;

public class StatementDataFactory {

    private final Plays plays;

    public StatementDataFactory(final Plays plays) {
        this.plays = plays;
    }

    public StatementData createFrom(final Invoice invoice) {
        List<EnrichedPerformance> enrichedPerformances = createFrom(invoice.performances());
        return new StatementData(invoice.customer(), enrichedPerformances,
                calculateTotalAmount(enrichedPerformances), calculateTotalVolumeCredits(enrichedPerformances));
    }

    private List<EnrichedPerformance> createFrom(final List<Performance> performances) {
        return performances.stream()
                .map(this::createFrom)
                .toList();
    }

    private EnrichedPerformance createFrom(final Performance performance) {
        Play play = playFor(performance);
        double amount = calculateAmount(performance, play);
        int volumeCredits = calculateVolumeCredits(performance, play);

        return new EnrichedPerformance(performance.playID(), performance.audience(), play,
                amount, volumeCredits);
    }

    private Play playFor(final Performance performance) {
        return plays.get(performance.playID());
    }

    private double calculateAmount(final Performance performance, final Play play) {
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

    private int calculateVolumeCredits(final Performance performance, final Play play) {
        int result = Math.max(performance.audience() - 30, 0);
        // 희극 관객 5명마다 추가 포인트 제공
        if (play.type() == PlayType.comedy) {
            result += performance.audience() / 5;
        }
        return result;
    }

    private double calculateTotalAmount(final List<EnrichedPerformance> performances) {
        double result = 0;
        for (EnrichedPerformance performance : performances) {
            result += performance.amount();
        }
        return result;
    }

    private int calculateTotalVolumeCredits(final List<EnrichedPerformance> performances) {
        int result = 0;
        for (EnrichedPerformance performance : performances) {
            result += performance.volumeCredits();
        }
        return result;
    }
}
