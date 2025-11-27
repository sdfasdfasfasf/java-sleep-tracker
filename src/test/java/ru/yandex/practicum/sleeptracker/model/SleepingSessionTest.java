package ru.yandex.practicum.sleeptracker.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SleepingSessionTest {
    @Test
    public void testSleepingSessionDurationCalculation() {
        SleepingSession session = new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD");
        assertEquals(480L, session.getDurationInMinutes());
    }

    @Test
    public void testSleepingSessionShortDuration() {
        SleepingSession session = new SleepingSession("01.10.25 14:00", "01.10.25 14:30", "NORMAL");
        assertEquals(30L, session.getDurationInMinutes());
    }

    @Test
    public void testNightSleepDetection() {
        // Ночной сон (вечер-утро)
        SleepingSession nightSession1 = new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD");
        assertTrue(nightSession1.isNightSleep());

        // Ночной сон (ночь-утро)
        SleepingSession nightSession2 = new SleepingSession("01.10.25 02:00", "01.10.25 08:00", "NORMAL");
        assertTrue(nightSession2.isNightSleep());

        // Дневной сон
        SleepingSession daySession = new SleepingSession("01.10.25 14:00", "01.10.25 15:00", "BAD");
        assertFalse(daySession.isNightSleep());

        // Вечерний сон (но без пересечения ночи)
        SleepingSession eveningSession = new SleepingSession("01.10.25 19:00", "01.10.25 23:00", "GOOD");
        assertFalse(eveningSession.isNightSleep());
    }

    @Test
    public void testSleepQualityParsing() {
        SleepingSession goodSession = new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD");
        assertEquals(SleepQuality.GOOD, goodSession.getQuality());

        SleepingSession badSession = new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "BAD");
        assertEquals(SleepQuality.BAD, badSession.getQuality());

        SleepingSession normalSession = new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "NORMAL");
        assertEquals(SleepQuality.NORMAL, normalSession.getQuality());
    }

    @Test
    public void testSleepingSessionToString() {
        SleepingSession session = new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD");
        String result = session.toString();
        assertTrue(result.contains("01.10.25 22:00"));
        assertTrue(result.contains("02.10.25 06:00"));
        assertTrue(result.contains("GOOD"));
        assertTrue(result.contains("480"));
    }
}