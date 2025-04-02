package model;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.filter.GroundTimeExceedsTwoHoursFilter;
import com.gridnine.testing.manager.FlightFilterManager;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SegmentTest {

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        FlightFilterManager filterManager = new FlightFilterManager();
    }

    @Test
    @DisplayName("Должен выбросить исключение при null датах")
    void testSegmentCreationWithNullDates() {
        assertThrows(NullPointerException.class, () -> {
            new Segment(null, null);
        });
    }
    @Test
    @DisplayName("Должен вернуть 0 для перелета с одним сегментом")
    void testCalculateGroundTimeWithSingleSegment() {
        LocalDateTime now = LocalDateTime.now();
        Flight flight = FlightBuilder.createFlight(now.plusHours(1), now.plusHours(2));

        GroundTimeExceedsTwoHoursFilter filter = new GroundTimeExceedsTwoHoursFilter();
        List<Flight> filtered = filter.filter(List.of(flight));

        assertEquals(1, filtered.size());
        assertEquals(Duration.ZERO, filter.calculateGroundTime(flight));
    }

    @Test
    @DisplayName("Должен возвращать корректную продолжительность сегмента")
    void segment_ShouldReturnCorrectDuration() {
        Segment segment = new Segment(now, now.plusHours(2));
        assertEquals(Duration.ofHours(2), Duration.between(
                segment.getDepartureDate(), segment.getArrivalDate()));
    }

}