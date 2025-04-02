package filter;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.manager.FlightFilterManager;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepartureBeforeNowFilterTest {

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        FlightFilterManager filterManager = new FlightFilterManager();
    }

    @Test
    @DisplayName("Должен отклонить все перелеты, когда все вылеты в прошлом")
    void departureFilter_ShouldRejectAllFlightsWhenAllDeparturesInPast() {
        Flight pastFlight1 = FlightBuilder.createFlight(now.minusHours(2), now.minusHours(1));
        Flight pastFlight2 = FlightBuilder.createFlight(now.minusDays(1), now.minusDays(1).plusHours(2));

        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter();
        List<Flight> result = filter.filter(List.of(pastFlight1, pastFlight2));

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Должен корректно обрабатывать пустой список перелетов")
    void departureFilter_ShouldHandleEmptyList() {
        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter();
        List<Flight> result = filter.filter(Collections.emptyList());

        assertTrue(result.isEmpty());
    }

}