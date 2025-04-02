package filter;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.filter.GroundTimeExceedsTwoHoursFilter;
import com.gridnine.testing.manager.FlightFilterManager;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightFiltersTest {

    @Test
    @DisplayName("Должен оставить перелеты с вылетом в будущем")
    void testDepartureBeforeNowFilter() {
        LocalDateTime now = LocalDateTime.now();
        Flight futureFlight = FlightBuilder.createFlight(now.plusHours(1), now.plusHours(2));
        Flight pastFlight = FlightBuilder.createFlight(now.minusHours(1), now.plusHours(1));

        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter();
        List<Flight> filtered = filter.filter(List.of(futureFlight, pastFlight));

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(futureFlight));
    }

    @Test
    @DisplayName("Должен отклонить перелеты с датой прилета раньше вылета")
    void testArrivalBeforeDepartureFilter() {
        LocalDateTime now = LocalDateTime.now();
        Flight validFlight = FlightBuilder.createFlight(now.plusHours(1), now.plusHours(2));
        Flight invalidFlight = FlightBuilder.createFlight(now.plusHours(2), now.plusHours(1));

        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();
        List<Flight> filtered = filter.filter(List.of(validFlight, invalidFlight));

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(validFlight));
    }

    @Test
    @DisplayName("Должен отклонить перелеты с временем на земле > 2 часов")
    void testGroundTimeExceedsTwoHoursFilter() {
        LocalDateTime now = LocalDateTime.now();
        Flight shortGroundTime = FlightBuilder.createFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(3), now.plusHours(4)
        );
        Flight longGroundTime = FlightBuilder.createFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(5), now.plusHours(6)
        );

        GroundTimeExceedsTwoHoursFilter filter = new GroundTimeExceedsTwoHoursFilter();
        List<Flight> filtered = filter.filter(List.of(shortGroundTime, longGroundTime));

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(shortGroundTime));
    }
    @Test
    @DisplayName("Должен корректно применять все фильтры последовательно")
    void testFlightFilterManager() {
        LocalDateTime now = LocalDateTime.now();
        Flight validFlight = FlightBuilder.createFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(3), now.plusHours(4)
        );
        Flight invalidDeparture = FlightBuilder.createFlight(
                now.minusHours(1), now.plusHours(1)
        );
        Flight invalidArrival = FlightBuilder.createFlight(
                now.plusHours(1), now.minusHours(1)
        );
        Flight invalidGroundTime = FlightBuilder.createFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(5), now.plusHours(6)
        );

        FlightFilterManager manager = new FlightFilterManager();
        manager.addFilter(new DepartureBeforeNowFilter());
        manager.addFilter(new ArrivalBeforeDepartureFilter());
        manager.addFilter(new GroundTimeExceedsTwoHoursFilter());

        List<Flight> filtered = manager.filter(List.of(validFlight, invalidDeparture, invalidArrival, invalidGroundTime));

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(validFlight));
    }
}