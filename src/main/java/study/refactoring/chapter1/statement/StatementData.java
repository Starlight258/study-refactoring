package study.refactoring.chapter1.statement;

import java.util.List;

public record StatementData(String customer, List<EnrichedPerformance> performances) {
}
