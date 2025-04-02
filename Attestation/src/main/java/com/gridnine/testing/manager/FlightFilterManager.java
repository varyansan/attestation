package com.gridnine.testing.manager;

import com.gridnine.testing.filter.FlightFilter;
import com.gridnine.testing.model.Flight;

import java.util.ArrayList;
import java.util.List;

public class FlightFilterManager {

    private final List<FlightFilter> filters;

    public FlightFilterManager() {
        this.filters = new ArrayList<>();
    }

    public void addFilter(FlightFilter filter) {
        filters.add(filter);
    }

    public List<Flight> filter(List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<>(flights);
        for (FlightFilter filter : filters) {
            filteredFlights = filter.filter(filteredFlights);
        }
        return filteredFlights;
    }
}