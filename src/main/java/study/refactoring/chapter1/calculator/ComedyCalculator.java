package study.refactoring.chapter1.calculator;

import study.refactoring.chapter1.play.dto.Play;
import study.refactoring.chapter1.statement.dto.Performance;

public class ComedyCalculator extends PerformanceCalculator {

    public ComedyCalculator(final Performance performance, final Play play) {
        super(performance, play);
    }

    @Override
    public double calculateAmount() {
        double result;
        result = 30_000;
        if (performance.audience() > 20) {
            result += 10_000 + 500 * (performance.audience() - 20);
        }
        result += 300 * performance.audience();
        return result;
    }

    @Override
    public int calculateVolumeCredits() {
        return super.calculateVolumeCredits() + performance.audience() / 5;
    }
}
