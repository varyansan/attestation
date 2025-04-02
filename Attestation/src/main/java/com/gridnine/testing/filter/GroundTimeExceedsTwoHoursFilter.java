package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class GroundTimeExceedsTwoHoursFilter implements FlightFilter {

    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> calculateGroundTime(flight).compareTo(Duration.ofHours(2)) <= 0)
                .collect(Collectors.toList());
    }

    public Duration calculateGroundTime(Flight flight) {
        Duration totalGroundTime = Duration.ZERO;

        for (int i = 0; i < flight.getSegments().size() - 1; i++) {
            Segment currentSegment = flight.getSegments().get(i);
            Segment nextSegment = flight.getSegments().get(i + 1);
            totalGroundTime = totalGroundTime.plus(Duration.between(currentSegment.getArrivalDate(), nextSegment.getDepartureDate()));
        }

        return totalGroundTime;
    }
}