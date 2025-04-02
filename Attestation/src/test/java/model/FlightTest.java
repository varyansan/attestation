package model;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.manager.FlightFilterManager;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightTest {

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        FlightFilterManager filterManager = new FlightFilterManager();
    }

    @Test
    @DisplayName("Должен содержать корректную информацию для одного сегмента рейса")
    public void testToStringSingleSegment() {
        Segment segment = new Segment(LocalDateTime.of(2025, 4, 5, 22, 9, 56),
                LocalDateTime.of(2025, 4, 6, 0, 9, 56));
        Flight flight = new Flight(List.of(segment));
        assertEquals("[2025-04-05T22:09:56|2025-04-06T00:09:56]", flight.toString());
    }

    @Test
    @DisplayName("Должен содержать корректную информацию для нескольких сегментов рейса")
    public void testToStringMultipleSegments() {
        Segment segment1 = new Segment(LocalDateTime.of(2025, 4, 5, 22, 9, 56),
                LocalDateTime.of(2025, 4, 6, 0, 9, 56));
        Segment segment2 = new Segment(LocalDateTime.of(2025, 4, 6, 1, 9, 56),
                LocalDateTime.of(2025, 4, 6, 3, 9, 56));

        Flight flight = new Flight(List.of(segment1, segment2));

        assertEquals("[2025-04-05T22:09:56|2025-04-06T00:09:56] [2025-04-06T01:09:56|2025-04-06T03:09:56]", flight.toString());
    }

    @Test
    @DisplayName("Должен содержать информацию о всех сегментах")
    void flight_ToString_ShouldContainAllSegments() {
        Flight flight = FlightBuilder.createFlight(
                now, now.plusHours(1),
                now.plusHours(2), now.plusHours(3)
        );

        String str = flight.toString();
        assertTrue(str.contains(now.toString()));
        assertTrue(str.contains(now.plusHours(1).toString()));
        assertTrue(str.contains(now.plusHours(2).toString()));
        assertTrue(str.contains(now.plusHours(3).toString()));
    }
}