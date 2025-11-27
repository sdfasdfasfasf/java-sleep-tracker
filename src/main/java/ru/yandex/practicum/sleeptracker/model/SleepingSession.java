package ru.yandex.practicum.sleeptracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SleepingSession {
    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final SleepQuality quality;

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepingSession(String sleepStartStr, String sleepEndStr, String qualityStr) {
        this.sleepStart = LocalDateTime.parse(sleepStartStr, DATE_TIME_FORMATTER);
        this.sleepEnd = LocalDateTime.parse(sleepEndStr, DATE_TIME_FORMATTER);
        this.quality = SleepQuality.valueOf(qualityStr);
    }

    public long getDurationInMinutes() {
        return ChronoUnit.MINUTES.between(sleepStart, sleepEnd);
    }

    public boolean isNightSleep() {
        int startHour = sleepStart.getHour();
        int endHour = sleepEnd.getHour();

        return (sleepStart.toLocalDate().equals(sleepEnd.toLocalDate().minusDays(1)) &&
                startHour >= 18 ||
                (startHour < 6 && endHour >= 6) ||
                (startHour >= 18 && endHour < 6));
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public LocalDateTime getSleepEnd() {
        return sleepEnd;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return String.format("Sleep: %s - %s (%s, %d min)",
                sleepStart.format(DATE_TIME_FORMATTER),
                sleepEnd.format(DATE_TIME_FORMATTER),
                quality,
                getDurationInMinutes());
    }
}
