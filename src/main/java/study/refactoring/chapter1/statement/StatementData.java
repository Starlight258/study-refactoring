package study.refactoring.chapter1.statement;

import java.util.List;
import study.refactoring.chapter1.play.Plays;

public record StatementData(String customer, List<EnrichedPerformance> performances) {

    public static StatementData of(final Invoice invoice, final Plays plays) {
        return new StatementData(invoice.customer(), EnrichedPerformance.createFrom(invoice.performances(), plays));
    }
}
