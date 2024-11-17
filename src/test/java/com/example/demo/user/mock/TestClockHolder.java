package com.example.demo.user.mock;

import com.example.demo.user.service.port.ClockHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestClockHolder implements ClockHolder {

    private final long mills;
    @Override
    public long mills() {
        return mills;
    }
}
