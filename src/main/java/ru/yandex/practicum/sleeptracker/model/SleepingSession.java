package ru.yandex.practicum.sleeptracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class SleepingSession {
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private static final int EVENING_HOUR_THRESHOLD = 18;
    private static final int NIGHT_END_HOUR_THRESHOLD = 6;
    private static final int MORNING_HOUR_THRESHOLD = 6;
    private static final String TO_STRING_FORMAT = "Sleep: %s - %s (%s, %d min)";

    private final LocalDateTime sleepStart;
    private final LocalDateTime sleepEnd;
    private final SleepQuality quality;

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
                startHour >= EVENING_HOUR_THRESHOLD ||
                (startHour < NIGHT_END_HOUR_THRESHOLD && endHour >= MORNING_HOUR_THRESHOLD) ||
                (startHour >= EVENING_HOUR_THRESHOLD && endHour < NIGHT_END_HOUR_THRESHOLD));
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
        return String.format(TO_STRING_FORMAT,
                sleepStart.format(DATE_TIME_FORMATTER),
                sleepEnd.format(DATE_TIME_FORMATTER),
                quality,
                getDurationInMinutes());
    }
}
