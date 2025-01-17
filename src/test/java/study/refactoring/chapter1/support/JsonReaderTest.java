package study.refactoring.chapter1.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static study.refactoring.chapter1.play.PlayType.tragedy;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.refactoring.chapter1.play.Play;
import study.refactoring.chapter1.play.Plays;
import study.refactoring.chapter1.statement.Invoice;

@SpringBootTest
class JsonReaderTest {

    @Autowired
    private JsonReader jsonReader;


    @Test
    void readInvoices() throws IOException {
        // when
        List<Invoice> invoices = jsonReader.readInvoices();

        // then
        Invoice invoice = invoices.getFirst();
        assertAll(
                () -> assertThat(invoice.customer()).isEqualTo("BigCo"),
                () -> assertThat(invoice.performances()).hasSize(3)
        );
    }

    @Test
    void readPlays() throws IOException {
        // when
        Plays plays = jsonReader.readPlays();

        // then
        Play play = plays.get("hamlet");
        assertAll(
                () -> assertThat(play.name()).isEqualTo("Hamlet"),
                () -> assertThat(play.type()).isEqualTo(tragedy)
        );
    }
}
