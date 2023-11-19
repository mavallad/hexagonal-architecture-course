package com.codescouts.hexagonal.codescoutshexagonaltraining;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "package com.codescouts.hexagonal.codescoutshexagonaltraining.infrastructure.*"
                ),
        }
)
public class CodeScoutsHexagonalTrainingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeScoutsHexagonalTrainingApplication.class, args);
    }
}
