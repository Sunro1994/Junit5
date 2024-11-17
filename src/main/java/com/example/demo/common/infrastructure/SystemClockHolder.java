package com.example.demo.common.infrastructure;

import com.example.demo.user.service.port.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.UUID;

@Component
public class SystemClockHolder implements ClockHolder {

    @Override
    public long mills() {
        System.out.println(Clock.systemUTC().millis());
        return Clock.systemUTC().millis();
    }
}
