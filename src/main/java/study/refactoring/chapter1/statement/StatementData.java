package study.refactoring.chapter1.statement;

import java.util.List;

public record StatementData(String customer, List<Performance> performances) {

    public static StatementData of(final Invoice invoice) {
        return new StatementData(invoice.customer(), copy(invoice.performances()));
    }

    private static List<Performance> copy(final List<Performance> performances) {
        return performances.stream()
                .map(performance -> new Performance(performance.playID(), performance.audience()))
                .toList();
    }
}
