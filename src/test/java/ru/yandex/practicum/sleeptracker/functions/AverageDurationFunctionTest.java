package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageDurationFunctionTest {
    @Test
    public void testAverageDurationWithVariousSessions() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD"), // 480 мин
                new SleepingSession("02.10.25 14:00", "02.10.25 15:00", "NORMAL") // 60 мин
        );

        AverageDurationFunction function = new AverageDurationFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals("Средняя продолжительность сессии (минут)", result.getDescription());
        assertEquals(270.0, result.getValue());
    }

    @Test
    public void testAverageDurationWithEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();

        AverageDurationFunction function = new AverageDurationFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(0.0, result.getValue());
    }
}