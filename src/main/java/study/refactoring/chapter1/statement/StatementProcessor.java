package study.refactoring.chapter1.statement;

import study.refactoring.chapter1.play.Play;
import study.refactoring.chapter1.play.PlayType;
import study.refactoring.chapter1.play.Plays;

// 명세서
public class StatementProcessor {

    private final study.refactoring.chapter1.statement.Invoice invoice;
    private final Plays plays;

    public StatementProcessor(final study.refactoring.chapter1.statement.Invoice invoice, final Plays plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    public String createStatement() {
        StringBuilder result = new StringBuilder();
        result.append("청구 내역 (고객명: ").append(invoice.customer()).append(")").append(System.lineSeparator());

        double totalAmount = 0;
        int volumeCredits = 0;
        for (study.refactoring.chapter1.statement.Performance performance : invoice.performances()) {
            Play play = plays.get(performance.playID());
            double thisAmount = calculateAmount(performance, play);

            // 포인트 적립
            volumeCredits += Math.max(performance.audience() - 30, 0);
            // 희극 관객 5명마다 추가 포인트 제공
            if (play.type() == PlayType.comedy) {
                volumeCredits += performance.audience() / 5;
            }
            // 청구 내역 출력
            result.append(String.format(" %s: $%,.2f", play.name(), thisAmount / 100)).append(" (")
                    .append(performance.audience()).append("석)").append(System.lineSeparator());
            totalAmount += thisAmount;
        }

        result.append(String.format("총액: $%,.2f", totalAmount / 100)).append(System.lineSeparator());
        result.append("적립 포인트: ").append(volumeCredits).append("점").append(System.lineSeparator());
        return result.toString();
    }

    private double calculateAmount(final Performance performance, final Play play) {
        double thisAmount;
        switch (play.type()) {
            case PlayType.tragedy -> {
                thisAmount = 40_000;
                if (performance.audience() > 30) {
                    thisAmount += 1_000 * (performance.audience() - 30);
                }
            }
            case PlayType.comedy -> {
                thisAmount = 30_000;
                if (performance.audience() > 20) {
                    thisAmount += 10_000 + 500 * (performance.audience() - 20);
                }
                thisAmount += 300 * performance.audience();
            }
            default -> throw new IllegalStateException("알 수 없는 장르: " + play.type());
        }
        return thisAmount;
    }
}
