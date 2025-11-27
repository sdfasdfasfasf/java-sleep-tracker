package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;;

class TotalSessionsFunctionTest {
    @Test
    public void testTotalSessionsWithMultipleSessions() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD"),
                new SleepingSession("02.10.25 23:00", "03.10.25 07:00", "NORMAL"),
                new SleepingSession("03.10.25 14:00", "03.10.25 15:00", "BAD")
        );

        TotalSessionsFunction function = new TotalSessionsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals("Общее количество сессий сна", result.getDescription());
        assertEquals(3L, result.getValue());
    }

    @Test
    public void testTotalSessionsWithEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();

        TotalSessionsFunction function = new TotalSessionsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(0L, result.getValue());
    }
}