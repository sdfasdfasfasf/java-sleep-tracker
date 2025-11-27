package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BadQualitySessionsFunctionTest {
    @Test
    public void testBadQualitySessionsCount() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD"),
                new SleepingSession("02.10.25 23:00", "03.10.25 07:00", "BAD"),
                new SleepingSession("03.10.25 14:00", "03.10.25 15:00", "BAD"),
                new SleepingSession("04.10.25 22:00", "05.10.25 06:00", "NORMAL")
        );

        BadQualitySessionsFunction function = new BadQualitySessionsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
        assertEquals(2L, result.getValue());
    }

    @Test
    public void testBadQualitySessionsWithNoBadQuality() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD"),
                new SleepingSession("02.10.25 23:00", "03.10.25 07:00", "NORMAL")
        );

        BadQualitySessionsFunction function = new BadQualitySessionsFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(0L, result.getValue());
    }
}