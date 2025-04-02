package filter;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.filter.GroundTimeExceedsTwoHoursFilter;
import com.gridnine.testing.manager.FlightFilterManager;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroundTimeExceedsTwoHoursFilterTest {

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        FlightFilterManager filterManager = new FlightFilterManager();
    }

    @Test
    @DisplayName("Должен принять перелет с точно 2 часами на земле")
    void groundTimeFilter_ShouldAcceptFlightWithExactlyTwoHoursGroundTime() {
        Flight exactTwoHours = FlightBuilder.createFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(4), now.plusHours(5)
        );

        GroundTimeExceedsTwoHoursFilter filter = new GroundTimeExceedsTwoHoursFilter();
        List<Flight> result = filter.filter(List.of(exactTwoHours));

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Должен отклонить перелет с несколькими периодами ожидания >2 часов")
    void groundTimeFilter_ShouldRejectFlightWithMultipleWaitingPeriods() {
        Flight multipleWaits = FlightBuilder.createFlight(
                now.plusHours(1), now.plusHours(2),  // segment 1
                now.plusHours(5), now.plusHours(6),  // segment 2 (3h wait)
                now.plusHours(7), now.plusHours(8),   // segment 3 (1h wait)
                now.plusHours(10), now.plusHours(11)  // segment 4 (2h wait)
        );

        GroundTimeExceedsTwoHoursFilter filter = new GroundTimeExceedsTwoHoursFilter();
        List<Flight> result = filter.filter(List.of(multipleWaits));

        assertTrue(result.isEmpty());
    }
}