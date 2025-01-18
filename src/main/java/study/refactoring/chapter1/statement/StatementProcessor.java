package study.refactoring.chapter1.statement;

import study.refactoring.chapter1.play.Plays;

public class StatementProcessor {

    private final StatementDataFactory statementDataFactory;

    public StatementProcessor(final Plays plays) {
        this.statementDataFactory = new StatementDataFactory(plays);
    }

    public String createStatement(final Invoice invoice) {
        return renderPlainText(statementDataFactory.createFrom(invoice));
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
                .append(formatUSD(statementData.totalAmount()))
                .append(System.lineSeparator());
        result.append("적립 포인트: ").append(statementData.totalVolumeCredits())
                .append("점")
                .append(System.lineSeparator());
        return result.toString();
    }

    private String formatUSD(double amount) {
        return String.format("$%,.2f", amount / 100);
    }

    private String renderHtml(StatementData statementData) throws Exception {
        StringBuilder result = new StringBuilder(
                String.format("<h1> 청구내역 (고객명: %s)\n </h1>", statementData.customer()));
        result.append("<table> \n");
        result.append("<tr><th> 연극 </th> <th>좌석 수</th> <th>금액</th>");
        for (EnrichedPerformance performance : statementData.performances()) {
            result.append(String.format("   <tr><td> %s</td> <td> %d석 </td> <td> $%s석 </td></tr>\n",
                    performance.play().name(), performance.audience(),
                    formatUSD(performance.amount())));
        }
        result.append("</table>\n");

        result.append(String.format("총액: $%s\n", formatUSD(statementData.totalAmount())));
        result.append(String.format("적립 포인트: %d점", statementData.totalVolumeCredits()));
        return result.toString();
    }
}
