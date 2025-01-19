package study.refactoring.chapter1.calculator;

import study.refactoring.chapter1.play.Play;
import study.refactoring.chapter1.play.PlayType;
import study.refactoring.chapter1.statement.Performance;

public class PerformanceCalculator {

    private final Performance performance;
    private final Play play;

    public PerformanceCalculator(final Performance performance, final Play play) {
        this.performance = performance;
        this.play = play;
    }

    public double calculateAmount() {
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

    public int calculateVolumeCredits(final Performance performance, final Play play) {
        int result = Math.max(performance.audience() - 30, 0);
        // 희극 관객 5명마다 추가 포인트 제공
        if (play.type() == PlayType.comedy) {
            result += performance.audience() / 5;
        }
        return result;
    }
}
