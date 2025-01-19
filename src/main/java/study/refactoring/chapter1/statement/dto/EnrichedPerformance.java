package study.refactoring.chapter1.statement.dto;

import study.refactoring.chapter1.play.dto.Play;

public record EnrichedPerformance(String playID, int audience, Play play, double amount, int volumeCredits) {
}
