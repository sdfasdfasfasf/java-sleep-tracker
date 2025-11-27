package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {
    @Test
    public void testAppInitialization() {
        SleepTrackerApp app = new SleepTrackerApp();
        assertNotNull(app);
    }

    @Test
    public void testLoadSleepSessions() throws Exception {
        SleepTrackerApp app = new SleepTrackerApp();

        // Создаем временный файл для тестирования
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("test_sleep", ".txt");
        java.nio.file.Files.write(tempFile, Arrays.asList(
                "01.10.25 22:00;02.10.25 06:00;GOOD",
                "02.10.25 23:00;03.10.25 07:00;NORMAL"
        ));

        List<SleepingSession> sessions = app.loadSleepSessions(tempFile.toString());

        assertEquals(2, sessions.size());
        assertEquals(480L, sessions.get(0).getDurationInMinutes());

        // Удаляем временный файл
        java.nio.file.Files.deleteIfExists(tempFile);
    }

    @Test
    public void testAllFunctionsIntegration() {
        List<SleepingSession> sessions = Arrays.asList(
                new SleepingSession("01.10.25 22:00", "02.10.25 06:00", "GOOD"),
                new SleepingSession("02.10.25 14:00", "02.10.25 15:00", "BAD"),
                new SleepingSession("03.10.25 23:30", "04.10.25 09:30", "NORMAL")
        );

        SleepTrackerApp app = new SleepTrackerApp();

        // Проверяем, что все функции работают без исключений
        assertDoesNotThrow(() -> app.analyzeAndPrintResults(sessions));
    }
}