package study.refactoring.chapter1.statement;

import study.refactoring.chapter1.play.Play;

public record EnrichedPerformance(String playID, int audience, Play play, double amount) {
}
