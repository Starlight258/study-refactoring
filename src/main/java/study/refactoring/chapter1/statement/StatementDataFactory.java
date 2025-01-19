package study.refactoring.chapter1.statement;

import java.util.List;
import study.refactoring.chapter1.calculator.PerformanceCalculator;
import study.refactoring.chapter1.play.Play;
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
        PerformanceCalculator calculator = PerformanceCalculator.of(performance, playFor(performance));
        Play play = playFor(performance);
        double amount = calculator.calculateAmount();
        int volumeCredits = calculator.calculateVolumeCredits();

        return new EnrichedPerformance(performance.playID(), performance.audience(), play,
                amount, volumeCredits);
    }

    private Play playFor(final Performance performance) {
        return plays.get(performance.playID());
    }

    private double calculateTotalAmount(final List<EnrichedPerformance> performances) {
        return performances.stream()
                .mapToDouble(EnrichedPerformance::amount)
                .sum();
    }

    private int calculateTotalVolumeCredits(final List<EnrichedPerformance> performances) {
        return performances.stream()
                .mapToInt(EnrichedPerformance::volumeCredits)
                .sum();
    }
}
