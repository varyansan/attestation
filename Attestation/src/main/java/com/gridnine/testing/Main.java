package com.gridnine.testing;

import com.gridnine.testing.builder.FlightBuilder;
import com.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.filter.GroundTimeExceedsTwoHoursFilter;
import com.gridnine.testing.manager.FlightFilterManager;
import com.gridnine.testing.model.Flight;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("Исходный список перелетов:");
        for (Flight flight : flights) {
            System.out.println("Filtered Flights: [" + flight + "];");
        }

        FlightFilterManager filterManager = new FlightFilterManager();
        filterManager.addFilter(new DepartureBeforeNowFilter());
        filterManager.addFilter(new ArrivalBeforeDepartureFilter());
        filterManager.addFilter(new GroundTimeExceedsTwoHoursFilter());

        List<Flight> filteredFlights = filterManager.filter(flights);

        System.out.println("\nОтфильтрованный список перелетов:");
        for (Flight flight : filteredFlights) {
            System.out.println("Filtered Flights: [" + flight + "]");
        }
    }
}