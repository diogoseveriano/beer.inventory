package records;

import lombok.*;

import java.io.Serializable;

@Builder
public record StatisticCard (String title, String stats, String avatarIcon, String color, boolean isMoney)
        implements Serializable {
}
