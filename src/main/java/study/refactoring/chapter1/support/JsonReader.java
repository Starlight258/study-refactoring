package study.refactoring.chapter1.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import study.refactoring.chapter1.play.Plays;
import study.refactoring.chapter1.statement.Invoice;

@Component
public class JsonReader {

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public JsonReader(final ObjectMapper objectMapper, final ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    public List<Invoice> readInvoices() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:data/invoices.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
        });
    }

    public Plays readPlays() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:data/plays.json");
        return new study.refactoring.chapter1.play.Plays(
                objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
                }));
    }
}
