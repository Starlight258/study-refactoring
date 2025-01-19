package study.refactoring.chapter1.calculator;

import study.refactoring.chapter1.play.Play;
import study.refactoring.chapter1.statement.Performance;

public class TragedyCalculator extends PerformanceCalculator {

    public TragedyCalculator(final Performance performance, final Play play) {
        super(performance, play);
    }

    @Override
    public double calculateAmount() {
        double result;
        result = 40_000;
        if (performance.audience() > 30) {
            result += 1_000 * (performance.audience() - 30);
        }
        return result;
    }
}
