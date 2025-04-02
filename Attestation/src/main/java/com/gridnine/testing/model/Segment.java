package com.gridnine.testing.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class Segment {

    private final LocalDateTime departureDate;
    private final LocalDateTime arrivalDate;

    public Segment(final LocalDateTime dep, final LocalDateTime arr) {
        departureDate = Objects.requireNonNull(dep);
        arrivalDate = Objects.requireNonNull(arr);
    }

    @Override
    public String toString() {
        return "[" + departureDate + '|' + arrivalDate + "]";
    }
}