package study.refactoring.chapter1.statement;

import study.refactoring.chapter1.play.PlayType;
import study.refactoring.chapter1.play.Plays;

public class StatementProcessor {

    private final Invoice invoice;
    private final Plays plays;

    public StatementProcessor(final Invoice invoice, final Plays plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String createStatement() {
        StatementData statementData = StatementData.of(invoice, plays);
        return renderPlainText(statementData);
    }

    private String renderPlainText(final StatementData statementData) {
        StringBuilder result = new StringBuilder();
        result.append("청구 내역 (고객명: ")
                .append(statementData.customer()).
                append(")")
                .append(System.lineSeparator());

        for (EnrichedPerformance performance : statementData.performances()) {
            // 청구 내역 출력
            result.append(String.format(" %s: ", performance.play().name()))
                    .append(formatUSD(calculateAmount(performance)))
                    .append(" (")
                    .append(performance.audience()).append("석)").append(System.lineSeparator());
        }
        result.append("총액: ")
                .append(formatUSD(calculateTotalAmount(statementData)))
                .append(System.lineSeparator());
        result.append("적립 포인트: ").append(calculateTotalVolumeCredits(statementData))
                .append("점")
                .append(System.lineSeparator());
        return result.toString();
    }

    private double calculateTotalAmount(final StatementData statementData) {
        double result = 0;
        for (EnrichedPerformance performance : statementData.performances()) {
            result += calculateAmount(performance);
        }
        return result;
    }

    private int calculateTotalVolumeCredits(final StatementData statementData) {
        int result = 0;
        for (EnrichedPerformance performance : statementData.performances()) {
            result += calculateVolumeCredits(performance);
        }
        return result;
    }

    private int calculateVolumeCredits(final EnrichedPerformance performance) {
        int result = Math.max(performance.audience() - 30, 0);
        // 희극 관객 5명마다 추가 포인트 제공
        if (performance.play().type() == PlayType.comedy) {
            result += performance.audience() / 5;
        }
        return result;
    }

    private double calculateAmount(final EnrichedPerformance performance) {
        double result;
        switch (performance.play().type()) {
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
            default -> throw new IllegalStateException("알 수 없는 장르: " + performance.play().type());
        }
        return result;
    }

    private String formatUSD(double amount) {
        return String.format("$%,.2f", amount / 100);
    }
}
