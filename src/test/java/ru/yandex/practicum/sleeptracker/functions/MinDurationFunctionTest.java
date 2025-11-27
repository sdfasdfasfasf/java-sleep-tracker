package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MinDurationFunctionTest {
    @Test
    public void testMinDurationWithVariousSessions() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD"), // 480 мин
                new SleepingSession("02.10.25 14:00", "02.10.25 15:00", "NORMAL"), // 60 мин
                new SleepingSession("03.10.25 23:00", "04.10.25 07:00", "BAD") // 480 мин
        );

        MinDurationFunction function = new MinDurationFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals("Минимальная продолжительность сессии (минут)", result.getDescription());
        assertEquals(60L, result.getValue());
    }

    @Test
    public void testMinDurationWithEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();

        MinDurationFunction function = new MinDurationFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(0L, result.getValue());
    }
}