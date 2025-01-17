package study.refactoring.chapter1.statement;

import java.util.List;

public record Invoice(String customer, List<Performance> performances) {
}
