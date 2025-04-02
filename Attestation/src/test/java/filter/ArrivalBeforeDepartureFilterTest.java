package filter;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.manager.FlightFilterManager;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArrivalBeforeDepartureFilterTest {

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        FlightFilterManager filterManager = new FlightFilterManager();
    }

    @Test
    @DisplayName("Должен отклонить перелет с одинаковым временем вылета и прилета")
    void arrivalBeforeDepartureFilter_ShouldRejectFlightWithEqualTimes() {
        Flight invalidFlight = FlightBuilder.createFlight(now.plusHours(1), now.plusHours(1));

        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();
        List<Flight> result = filter.filter(List.of(invalidFlight));

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Должен проверять все сегменты перелета")
    void arrivalBeforeDepartureFilter_ShouldCheckAllSegments() {
        Flight mixedFlight = FlightBuilder.createFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(3), now.plusHours(3),
                now.plusHours(4), now.plusHours(5)
        );

        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();
        List<Flight> result = filter.filter(List.of(mixedFlight));

        assertTrue(result.isEmpty());
    }
}