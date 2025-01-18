package study.refactoring.chapter1.statement;

import study.refactoring.chapter1.play.Plays;

public class StatementProcessor {

    private final StatementDataFactory statementDataFactory;

    public StatementProcessor(final Plays plays) {
        this.statementDataFactory = new StatementDataFactory(plays);
    }

    public String createStatement(final Invoice invoice) {
        StatementData statementData = statementDataFactory.createFrom(invoice);
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
                    .append(formatUSD(performance.amount()))
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
            result += performance.amount();
        }
        return result;
    }

    private int calculateTotalVolumeCredits(final StatementData statementData) {
        int result = 0;
        for (EnrichedPerformance performance : statementData.performances()) {
            result += performance.volumeCredits();
        }
        return result;
    }

    private String formatUSD(double amount) {
        return String.format("$%,.2f", amount / 100);
    }
}
