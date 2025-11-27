package ru.yandex.practicum.sleeptracker.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SleepAnalysisResultTest {
    @Test
    public void testSleepAnalysisResultCreation() {
        SleepAnalysisResult result = new SleepAnalysisResult("Test Description", 42);

        assertEquals("Test Description", result.getDescription());
        assertEquals(42, result.getValue());
    }

    @Test
    public void testSleepAnalysisResultToString() {
        SleepAnalysisResult result = new SleepAnalysisResult("Test Metric", 123);
        String stringResult = result.toString();

        assertEquals("Test Metric: 123", stringResult);
    }

    @Test
    public void testSleepAnalysisResultWithStringValue() {
        SleepAnalysisResult result = new SleepAnalysisResult("Chronotype", "OWL");

        assertEquals("Chronotype", result.getDescription());
        assertEquals("OWL", result.getValue());
    }
}