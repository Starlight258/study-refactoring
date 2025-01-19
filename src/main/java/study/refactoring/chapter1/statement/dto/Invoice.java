package study.refactoring.chapter1.statement.dto;

import java.util.List;

public record Invoice(String customer, List<Performance> performances) {
}
