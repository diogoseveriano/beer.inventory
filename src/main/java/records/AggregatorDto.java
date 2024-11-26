package records;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AggregatorDto(String title, String unit, BigDecimal unitAmount) {
}
