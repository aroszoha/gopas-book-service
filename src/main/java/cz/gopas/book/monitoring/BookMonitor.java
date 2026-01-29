package cz.gopas.book.monitoring;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Override app health indicator.
 * Health method can perform some checks to determine overall status.
 */
@Component
public class BookMonitor implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().build();
    }

}
