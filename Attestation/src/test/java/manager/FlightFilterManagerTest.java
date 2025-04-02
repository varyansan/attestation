package manager;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.filter.GroundTimeExceedsTwoHoursFilter;
import com.gridnine.testing.manager.FlightFilterManager;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightFilterManagerTest {

    private LocalDateTime now;
    private FlightFilterManager filterManager;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        filterManager = new FlightFilterManager();
    }

    @Test
    @DisplayName("Должен работать без добавленных фильтров")
    void filterManager_ShouldWorkWithNoFilters() {
        Flight flight = FlightBuilder.createFlight(now.plusHours(1), now.plusHours(2));

        List<Flight> result = new FlightFilterManager().filter(List.of(flight));

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Должен применять фильтры в правильном порядке")
    void filterManager_ShouldApplyFiltersInOrder() {
        Flight invalidFlight = FlightBuilder.createFlight(
                now.plusHours(1), now.minusHours(1) // arrival before departure
        );

        Flight longGroundTimeFlight = FlightBuilder.createFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(5), now.plusHours(6) // 3h ground time
        );

        Flight validFlight = FlightBuilder.createFlight(
                now.plusHours(1), now.plusHours(2),
                now.plusHours(3), now.plusHours(4) // 1h ground time
        );

        filterManager.addFilter(new DepartureBeforeNowFilter());
        filterManager.addFilter(new ArrivalBeforeDepartureFilter());
        filterManager.addFilter(new GroundTimeExceedsTwoHoursFilter());

        List<Flight> result = filterManager.filter(List.of(invalidFlight, longGroundTimeFlight, validFlight));

        assertEquals(1, result.size());
        assertEquals(validFlight, result.get(0));
    }
}