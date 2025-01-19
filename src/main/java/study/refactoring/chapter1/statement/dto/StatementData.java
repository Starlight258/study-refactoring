package study.refactoring.chapter1.statement.dto;

import java.util.List;

public record StatementData(String customer, List<EnrichedPerformance> performances, double totalAmount, int totalVolumeCredits) {
}
