package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class MaxDurationFunction implements SleepAnalysisFunction {
    private static final String RESULT_DESCRIPTION = "Максимальная продолжительность сессии (минут)";
    private static final long DEFAULT_MAX_DURATION = 0L;

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        var t = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes);

        long maxDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .max()
                .orElse(DEFAULT_MAX_DURATION);

        return new SleepAnalysisResult(RESULT_DESCRIPTION, maxDuration);
    }
}
