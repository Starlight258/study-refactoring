package study.refactoring.chapter1.calculator;

import study.refactoring.chapter1.play.dto.Play;
import study.refactoring.chapter1.statement.dto.Performance;

public class PerformanceCalculator {

    protected final Performance performance;
    protected final Play play;

    protected PerformanceCalculator(final Performance performance, final Play play) {
        this.performance = performance;
        this.play = play;
    }

    public static PerformanceCalculator of(final Performance performance, final Play play) {
        switch (play.type()) {
            case TRAGEDY -> {
                return new TragedyCalculator(performance, play);
            }
            case COMEDY -> {
                return new ComedyCalculator(performance, play);
            }
            default -> throw new IllegalStateException("알 수 없는 장르: " + play.type());
        }
    }

    public double calculateAmount() {
        throw new UnsupportedOperationException("서브클래스에서 처리하도록 설계되었습니다.");
    }

    public int calculateVolumeCredits() {
        return Math.max(performance.audience() - 30, 0);
    }
}
