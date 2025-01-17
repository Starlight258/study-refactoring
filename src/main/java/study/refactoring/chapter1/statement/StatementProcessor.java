package study.refactoring.chapter1.statement;

import study.refactoring.chapter1.play.Play;
import study.refactoring.chapter1.play.PlayType;
import study.refactoring.chapter1.play.Plays;

// 명세서
public class StatementProcessor {

    private final Invoice invoice;
    private final Plays plays;

    public StatementProcessor(final Invoice invoice, final Plays plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String createStatement() {
        StringBuilder result = new StringBuilder();
        result.append("청구 내역 (고객명: ").append(invoice.customer()).append(")").append(System.lineSeparator());

        double totalAmount = 0;
        int volumeCredits = 0;
        for (Performance performance : invoice.performances()) {
            // 포인트 적립
            volumeCredits += Math.max(performance.audience() - 30, 0);
            // 희극 관객 5명마다 추가 포인트 제공
            if (playFor(performance).type() == PlayType.comedy) {
                volumeCredits += performance.audience() / 5;
            }
            // 청구 내역 출력
            result.append(String.format(" %s: $%,.2f", playFor(performance).name(), calculateAmount(performance) / 100))
                    .append(" (")
                    .append(performance.audience()).append("석)").append(System.lineSeparator());
            totalAmount += calculateAmount(performance);
        }

        result.append(String.format("총액: $%,.2f", totalAmount / 100)).append(System.lineSeparator());
        result.append("적립 포인트: ").append(volumeCredits).append("점").append(System.lineSeparator());
        return result.toString();
    }

    private Play playFor(final Performance performance) {
        return plays.get(performance.playID());
    }

    private double calculateAmount(final Performance performance) {
        double result;
        switch (playFor(performance).type()) {
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
            default -> throw new IllegalStateException("알 수 없는 장르: " + playFor(performance).type());
        }
        return result;
    }
}
