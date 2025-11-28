package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxDurationFunctionTest {
    @Test
    public void testMaxDurationWithVariousSessions() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD"), // 480 мин
                new SleepingSession("02.10.25 14:00", "02.10.25 15:00", "NORMAL"), // 60 мин
                new SleepingSession("03.10.25 21:00", "04.10.25 08:00", "BAD") // 660 мин
        );

        MaxDurationFunction function = new MaxDurationFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals("Максимальная продолжительность сессии (минут)", result.getDescription());
        assertEquals(660L, result.getValue());
    }

    @Test
    public void testMaxDurationWithEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();

        MaxDurationFunction function = new MaxDurationFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(0L, result.getValue());
    }
}