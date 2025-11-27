package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SleeplessNightsFunctionTest {
    @Test
    public void testSleeplessNightsWithMixedSessions() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD"), // Ночь 01-02 окт
                new SleepingSession("03.10.25 14:00", "03.10.25 15:00", "NORMAL") // Дневной сон, ночь 02-03 окт бессонная
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals("Количество бессонных ночей", result.getDescription());
        assertTrue((Long) result.getValue() >= 1);
    }

    @Test
    public void testSleeplessNightsWithAllNightSessions() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD"),
                new SleepingSession("02.10.25 23:00", "03.10.25 07:00", "NORMAL"),
                new SleepingSession("03.10.25 22:30", "04.10.25 06:30", "BAD")
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(0L, result.getValue()); // Все ночи со сном
    }

    @Test
    public void testSleeplessNightsWithOnlyDaySessions() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 10:00", "01.10.25 11:00", "GOOD"),
                new SleepingSession("02.10.25 14:00", "02.10.25 15:00", "NORMAL"),
                new SleepingSession("03.10.25 16:00", "03.10.25 17:00", "BAD")
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertTrue((Long) result.getValue() > 0); // Все ночи бессонные
    }

    @Test
    public void testSleeplessNightsWithLateNightSession() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 02:00", "01.10.25 08:00", "GOOD") // Ночной сон
        );

        SleeplessNightsFunction function = new SleeplessNightsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(0L, result.getValue()); // Ночь не бессонная
    }
}