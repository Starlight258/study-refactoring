package study.refactoring.chapter1.statement;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.refactoring.chapter1.play.Plays;
import study.refactoring.chapter1.support.JsonReader;

@SpringBootTest
class StatementProcessorTest {

    @Autowired
    private JsonReader jsonReader;

    @Test
    void createStatement() throws IOException {
        // given
        List<Invoice> invoices = jsonReader.readInvoices();
        Plays plays = jsonReader.readPlays();
        StatementProcessor statementProcessor = new StatementProcessor(invoices.getFirst(), plays);

        // when
        String statement = statementProcessor.createStatement();

        // then
        assertThat(statement).contains("""
                청구 내역 (고객명: BigCo)
                 Hamlet: $650.00 (55석)
                 As You Like It: $580.00 (35석)
                 Othello: $500.00 (40석)
                총액: $1,730.00
                적립 포인트: 47점""");
    }
}
