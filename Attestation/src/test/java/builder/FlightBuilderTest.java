package builder;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.manager.FlightFilterManager;
import com.gridnine.testing.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlightBuilderTest {

    private LocalDateTime now;
    private FlightFilterManager filterManager;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        filterManager = new FlightFilterManager();
    }

    @Test
    @DisplayName("Должен выбросить исключение при нечетном количестве дат")
    void testCreateFlightWithOddDates() {
        LocalDateTime now = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () -> {
            FlightBuilder.createFlight(now, now.plusHours(1), now.plusHours(2));
        });
    }
    @Test
    @DisplayName("Должен создавать корректное количество сегментов")
    void flightBuilder_ShouldCreateCorrectNumberOfSegments() {
        Flight flight = FlightBuilder.createFlight(
                now, now.plusHours(1),
                now.plusHours(2), now.plusHours(3),
                now.plusHours(4), now.plusHours(5)
        );

        assertEquals(3, flight.getSegments().size());
    }
}