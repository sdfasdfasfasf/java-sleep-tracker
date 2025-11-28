package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class AverageDurationFunction implements SleepAnalysisFunction {
    private static final double DEFAULT_AVERAGE_VALUE = 0.0;
    private static final int DECIMAL_PLACES_MULTIPLIER = 100;
    private static final String RESULT_DESCRIPTION = "Средняя продолжительность сессии (минут)";

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        double averageDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(DEFAULT_AVERAGE_VALUE);

        return new SleepAnalysisResult(RESULT_DESCRIPTION,
                Math.round(averageDuration * DECIMAL_PLACES_MULTIPLIER) / DECIMAL_PLACES_MULTIPLIER);
    }
}
