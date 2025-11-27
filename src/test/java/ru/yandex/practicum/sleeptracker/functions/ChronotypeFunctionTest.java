package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ChronotypeFunctionTest {
    @Test
    public void testChronotypeOwl() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 23:30", "02.10.25 09:30", "GOOD"), // Сова
                new SleepingSession("02.10.25 23:45", "03.10.25 09:15", "NORMAL"), // Сова
                new SleepingSession("03.10.25 23:20", "04.10.25 09:00", "BAD") // Сова
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals("Хронотип пользователя", result.getDescription());
        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    public void testChronotypeLark() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 21:00", "02.10.25 06:30", "GOOD"), // Жаворонок
                new SleepingSession("02.10.25 20:30", "03.10.25 06:00", "NORMAL"), // Жаворонок
                new SleepingSession("03.10.25 21:15", "04.10.25 06:45", "BAD") // Жаворонок
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    public void testChronotypeDove() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:30", "02.10.25 07:30", "GOOD"), // Голубь
                new SleepingSession("02.10.25 22:15", "03.10.25 07:45", "NORMAL"), // Голубь
                new SleepingSession("03.10.25 22:45", "04.10.25 08:00", "BAD") // Голубь
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    public void testChronotypeWithMixedSessions() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 23:30", "02.10.25 09:30", "GOOD"), // Сова
                new SleepingSession("02.10.25 21:00", "03.10.25 06:30", "NORMAL"), // Жаворонок
                new SleepingSession("03.10.25 22:30", "04.10.25 07:30", "BAD"), // Голубь
                new SleepingSession("04.10.25 23:45", "05.10.25 09:15", "GOOD") // Сова
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(Chronotype.DOVE, result.getValue()); // Сов больше
    }

    @Test
    public void testChronotypeWithDaySessionsIgnored() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 14:00", "01.10.25 15:00", "GOOD"), // Дневной сон (игнорируется)
                new SleepingSession("01.10.25 23:30", "02.10.25 09:30", "NORMAL"), // Сова
                new SleepingSession("02.10.25 23:45", "03.10.25 09:15", "BAD") // Сова
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    public void testChronotypeWithEmptyList() {
        List<SleepingSession> sessions = Collections.emptyList();

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(Chronotype.DOVE, result.getValue()); // По умолчанию голубь
    }

    @Test
    public void testChronotypeTieBreaker() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 23:30", "02.10.25 09:30", "GOOD"), // Сова
                new SleepingSession("02.10.25 21:00", "03.10.25 06:30", "NORMAL") // Жаворонок
        );

        ChronotypeFunction function = new ChronotypeFunction();
        SleepAnalysisResult result = function.analyze(sessions);

        assertEquals(Chronotype.DOVE, result.getValue()); // При равенстве - голубь
    }
}