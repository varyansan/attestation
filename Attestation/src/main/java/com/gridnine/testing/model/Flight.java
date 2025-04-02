package com.gridnine.testing.model;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Flight {

    private final List<Segment> segments;

    public Flight(final List<Segment> segs) {
        segments = segs;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}